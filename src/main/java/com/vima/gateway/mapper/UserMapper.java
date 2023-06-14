package com.vima.gateway.mapper;

import com.vima.gateway.AccommodationRequest;
import com.vima.gateway.AccommodationResponse;
import com.vima.gateway.DateRange;

import com.vima.gateway.auth.RegistrationHttpRequest;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.accommodation.AccommodationHttpRequest;
import com.vima.gateway.dto.accommodation.AccommodationHttpResponse;
import com.vima.gateway.dto.user.DeleteUserHttpRequest;
import com.vima.gateway.dto.user.EditUserHttpRequest;
import com.vima.gateway.enums.accommodation.PaymentType;
import communication.DeleteUserRequest;
import communication.EditUserRequest;
import communication.RegistrationRequest;
import communication.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public static RegistrationRequest convertHttpToGrpc(RegistrationHttpRequest httpRequest) {

        return communication.RegistrationRequest.newBuilder()
                .setEmail(httpRequest.getEmail())
                .setPassword(httpRequest.getPassword())
                .setFirstName(httpRequest.getFirstName())
                .setLastName(httpRequest.getLastName())
                .setPhoneNumber(httpRequest.getPhoneNumber())
                .setUsername(httpRequest.getUsername())
                .setRole(Role.valueOf(httpRequest.getRole()))
                .build();
    }

    public static EditUserRequest convertHttpToGrpc(EditUserHttpRequest httpRequest){

            if(httpRequest.getEmail() == null){
                httpRequest.setEmail("");
            }
            if(httpRequest.getPassword() == null){
            httpRequest.setPassword("");
            }
            if(httpRequest.getFirstName() == null){
                httpRequest.setFirstName("");
            }
            if(httpRequest.getLastName() == null){
                httpRequest.setLastName("");
            }
            if(httpRequest.getPhoneNumber() == null){
                httpRequest.setPhoneNumber("");
            }
            if(httpRequest.getUsername() == null){
                httpRequest.setUsername("");
            }
            if(httpRequest.getLocation() == null){
                httpRequest.setLocation("");
            }

        return EditUserRequest.newBuilder()
                .setNewEmail(httpRequest.getEmail())
                .setNewPassword(httpRequest.getPassword())
                .setNewFirstName(httpRequest.getFirstName())
                .setNewLastName(httpRequest.getLastName())
                .setNewPhoneNumber(httpRequest.getPhoneNumber())
                .setNewUsername(httpRequest.getUsername())
                .setCurrentUsername(httpRequest.getCurrentUsername())
                .setNewLocation(httpRequest.getLocation())
                .build();

    }

    public static DeleteUserRequest convertHttpToGrpc(DeleteUserHttpRequest httpRequest){

        return DeleteUserRequest.newBuilder()
                .setUsername(httpRequest.getUsername())
                .build();
    }
}
