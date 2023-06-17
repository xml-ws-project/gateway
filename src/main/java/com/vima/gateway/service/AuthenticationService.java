package com.vima.gateway.service;

import com.vima.gateway.*;
import com.vima.gateway.auth.RegistrationHttpRequest;
import com.vima.gateway.dto.user.DeleteUserHttpRequest;
import com.vima.gateway.dto.user.DeleteUserHttpResponse;
import com.vima.gateway.dto.user.EditUserHttpRequest;
import com.vima.gateway.dto.user.EditUserHttpResponse;
import com.vima.gateway.mapper.UserMapper;
import com.vima.gateway.model.User;
import communication.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final String channelAuthAddress;
    private final String channelAccommodationAddress;
    private final String channelReservationAddress;

    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder,
        @Value("${channel.address.auth-ms}") String channelAuthAddress,
        @Value("${channel.address.accommodation-ms}") String channelAccommodationAddress,
        @Value("${channel.address.reservation-ms}") String channelReservationAddress) {
        this.passwordEncoder = passwordEncoder;
        this.channelAuthAddress = channelAuthAddress;
        this.channelAccommodationAddress = channelAccommodationAddress;
        this.channelReservationAddress = channelReservationAddress;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(channelAuthAddress, 9092)
                .usePlaintext()
                .build();

            userDetailsServiceGrpc.userDetailsServiceBlockingStub blockingStub = userDetailsServiceGrpc.newBlockingStub(channel);
            UserDetailsRequest req = UserDetailsRequest.newBuilder().setUsername(username).build();
            UserDetailsResponse response = blockingStub.getUserDetails(req);
            channel.shutdown();
            return User.builder()
                .id(response.getId())
                .username(response.getUsername())
                .password(response.getPassword())
                .role(response.getRole().equals(Role.GUEST) ? com.vima.gateway.model.Role.GUEST : com.vima.gateway.model.Role.HOST)
                .penalties(response.getPenalties()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public RegistrationResponse register(RegistrationHttpRequest httpRequest){

        ManagedChannel channel = ManagedChannelBuilder.forAddress(channelAuthAddress, 9092)
                .usePlaintext()
                .build();
        userDetailsServiceGrpc.userDetailsServiceBlockingStub blockingStub = userDetailsServiceGrpc.newBlockingStub(channel);
        httpRequest.setPassword(passwordEncoder.encode(httpRequest.getPassword()));
        RegistrationRequest req = UserMapper.convertHttpToGrpc(httpRequest);
        RegistrationResponse response = blockingStub.register(req);
        return RegistrationResponse.newBuilder()
                .setMessage(response.getMessage())
                .build();
    }

    public EditUserHttpResponse edit(EditUserHttpRequest httpRequest){

        ManagedChannel channel = ManagedChannelBuilder.forAddress(channelAuthAddress, 9092)
                .usePlaintext()
                .build();
        userDetailsServiceGrpc.userDetailsServiceBlockingStub blockingStub = userDetailsServiceGrpc.newBlockingStub(channel);
        EditUserRequest req = UserMapper.convertHttpToGrpc(httpRequest);
        EditUserResponse res = blockingStub.edit(req);
        return EditUserHttpResponse.builder()
                .message(res.getMessage())
                .build();
    }

    public DeleteUserHttpResponse delete(DeleteUserHttpRequest httpRequest){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(channelAuthAddress, 9092)
                .usePlaintext()
                .build();
        userDetailsServiceGrpc.userDetailsServiceBlockingStub blockingStub = userDetailsServiceGrpc.newBlockingStub(channel);

        User  user = loadUserByUsername(httpRequest.getUsername());
        com.vima.gateway.model.Role role = user.getRole();
        Long userId = user.getId();

        if(role == com.vima.gateway.model.Role.GUEST ){
            if(ifGuestHasActiveReservations(userId)) {
                return DeleteUserHttpResponse.builder()
                        .message("User has reservations in upcoming periods!")
                        .build();
            }
        }

        if(role == com.vima.gateway.model.Role.HOST ){
            if(ifHostHasActiveReservations(userId)) {
                return DeleteUserHttpResponse.builder()
                        .message("User has reservations in upcoming periods!")
                        .build();
            }
            else {
                deleteHostAccommodations(userId);
            }

        }

        DeleteUserRequest req = UserMapper.convertHttpToGrpc(httpRequest);
        DeleteUserResponse res = blockingStub.delete(req);
        return DeleteUserHttpResponse.builder()
                .message(res.getMessage())
                .build();
    }

    private Boolean ifGuestHasActiveReservations(Long id){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(channelReservationAddress, 9094)
                .usePlaintext()
                .build();
        ReservationServiceGrpc.ReservationServiceBlockingStub blockingStub = ReservationServiceGrpc.newBlockingStub(channel);

        CheckReservationsForUserRequest req = CheckReservationsForUserRequest.newBuilder().setId(id).build();
        CheckReservationsForUserResponse res = blockingStub.checkIfGuestHasActiveReservations(req);
        return res.getContains();
    }

    private Boolean ifHostHasActiveReservations(Long id){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(channelReservationAddress, 9094)
                .usePlaintext()
                .build();
        ReservationServiceGrpc.ReservationServiceBlockingStub blockingStub = ReservationServiceGrpc.newBlockingStub(channel);

        CheckReservationsForUserRequest req = CheckReservationsForUserRequest.newBuilder().setId(id).build();
        CheckReservationsForUserResponse res = blockingStub.checkIfHostHasActiveReservations(req);

        return res.getContains();
    }

    private String deleteHostAccommodations(Long id){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(channelAccommodationAddress, 9093)
                .usePlaintext()
                .build();
        AccommodationServiceGrpc.AccommodationServiceBlockingStub blockingStub = AccommodationServiceGrpc.newBlockingStub(channel);
        DeleteHostAccommodationsRequest req = DeleteHostAccommodationsRequest.newBuilder().setId(id).build();
        DeleteHostAccommodationResponse res = blockingStub.deleteHostAccommodations(req);
        return res.getMessage();
    }

}
