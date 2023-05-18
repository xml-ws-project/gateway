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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final String url = "http://localhost:8082/api/user/user-details/";

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return getUserDetails(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private User getUserDetails(String username){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();

        userDetailsServiceGrpc.userDetailsServiceBlockingStub blockingStub = userDetailsServiceGrpc.newBlockingStub(channel);
        UserDetailsRequest req = UserDetailsRequest.newBuilder().setUsername(username).build();
        UserDetailsResponse response = blockingStub.getUserDetails(req);
        User user = User.builder()
                .id(response.getId())
                .username(response.getUsername())
                .password(response.getPassword())
                .role(response.getRole().equals(Role.GUEST) ? com.vima.gateway.model.Role.GUEST : com.vima.gateway.model.Role.HOST)
                .penalties(response.getPenalties()).build();
        return user;
    }

    public RegistrationResponse register(RegistrationHttpRequest httpRequest){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();
        userDetailsServiceGrpc.userDetailsServiceBlockingStub blockingStub = userDetailsServiceGrpc.newBlockingStub(channel);
        RegistrationRequest req = UserMapper.convertHttpToGrpc(httpRequest);
        RegistrationResponse response = blockingStub.register(req);
        return RegistrationResponse.newBuilder()
                .setMessage(response.getMessage())
                .build();
    }

    public EditUserHttpResponse edit(EditUserHttpRequest httpRequest){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
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
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
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
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9094)
                .usePlaintext()
                .build();
        ReservationServiceGrpc.ReservationServiceBlockingStub blockingStub = ReservationServiceGrpc.newBlockingStub(channel);

        CheckReservationsForUserRequest req = CheckReservationsForUserRequest.newBuilder().setId(id).build();
        CheckReservationsForUserResponse res = blockingStub.checkIfGuestHasActiveReservations(req);
        return res.getContains();
    }

    private Boolean ifHostHasActiveReservations(Long id){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9094)
                .usePlaintext()
                .build();
        ReservationServiceGrpc.ReservationServiceBlockingStub blockingStub = ReservationServiceGrpc.newBlockingStub(channel);

        CheckReservationsForUserRequest req = CheckReservationsForUserRequest.newBuilder().setId(id).build();
        CheckReservationsForUserResponse res = blockingStub.checkIfHostHasActiveReservations(req);

        return res.getContains();
    }

    private String deleteHostAccommodations(Long id){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9093)
                .usePlaintext()
                .build();
        AccommodationServiceGrpc.AccommodationServiceBlockingStub blockingStub = AccommodationServiceGrpc.newBlockingStub(channel);
        DeleteHostAccommodationsRequest req = DeleteHostAccommodationsRequest.newBuilder().setId(id).build();
        DeleteHostAccommodationResponse res = blockingStub.deleteHostAccommodations(req);
        return res.getMessage();
    }

}
