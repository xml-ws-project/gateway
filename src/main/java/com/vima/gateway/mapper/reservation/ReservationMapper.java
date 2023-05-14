package com.vima.gateway.mapper.reservation;

import com.vima.gateway.*;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.reservation.*;
import com.vima.gateway.enums.reservation.ReservationStatus;

import java.util.ArrayList;
import java.util.List;

public class ReservationMapper {

    public static ReservationRequest convertHttpToGrpc(ReservationHttpRequest httpRequest){
        com.vima.gateway.DateRange dateRange = DateRange.newBuilder()
                .setStart(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getStart()))
                .setEnd(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getEnd()))
                .build();

        return ReservationRequest.newBuilder()
                .setNumOfGuests(httpRequest.getNumOfGuests())
                .setDesiredDate(dateRange)
                .setAccomId(httpRequest.getAccomId())
                .setUserId(httpRequest.getUserId())
                .build();
    }

    public static ReservationHttpResponse convertGrpcToHttp(ReservationResponse grpcResponse){
        AccommodationInfoHttpResponse accomInfo =  AccommodationInfoHttpResponse.builder()
                .accomId(grpcResponse.getAccomInfo().getAccomId())
                .minGuests(grpcResponse.getAccomInfo().getMinGuests())
                .maxGuests(grpcResponse.getAccomInfo().getMaxGuests())
                .city(grpcResponse.getAccomInfo().getCity())
                .country(grpcResponse.getAccomInfo().getCountry())
                .build();

        return  ReservationHttpResponse.builder()
                .id(grpcResponse.getId())
                .numOfGuests(grpcResponse.getNumOfGuests())
                .status(ReservationStatus.valueOf(grpcResponse.getStatus().toString()))
                .accomInfo(accomInfo)
                .userId(grpcResponse.getUserId())
                .start(LocalDateConverter.convertGoogleTimeStampToLocalDate(grpcResponse.getDesiredDate().getStart()))
                .end(LocalDateConverter.convertGoogleTimeStampToLocalDate(grpcResponse.getDesiredDate().getEnd()))
                .build();
    }

    public static List<ReservationHttpResponse> convertGrpcToHttpList(ReservationList grpcList){
        List<ReservationHttpResponse> httpList = new ArrayList<>();
        grpcList.getReturnListList().forEach(list ->{
            httpList.add(convertGrpcToHttp(list));
        });

        return httpList;
    }

    public static HostResponse convertHostResponseToGrpc(HostHttpResponse response){
        return HostResponse.newBuilder()
                .setId(response.getId())
                .setAccept(response.isAccept())
                .build();
    }

    public static UserRequest convertUserRequestToGrpc(UserHttpRequest request){
        return UserRequest.newBuilder()
                .setId(request.getId())
                .setRole(request.getRole())
                .build();
    }
}
