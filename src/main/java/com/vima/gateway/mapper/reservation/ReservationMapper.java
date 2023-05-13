package com.vima.gateway.mapper.reservation;

import com.vima.gateway.AccommodationInfo;
import com.vima.gateway.DateRange;
import com.vima.gateway.ReservationRequest;
import com.vima.gateway.ReservationResponse;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.reservation.AccommodationInfoHttpResponse;
import com.vima.gateway.dto.reservation.ReservationHttpRequest;
import com.vima.gateway.dto.reservation.ReservationHttpResponse;
import com.vima.gateway.enums.reservation.ReservationStatus;

public class ReservationMapper {

    public static ReservationRequest convertHttpToGrpc(ReservationHttpRequest httpRequest){
        com.vima.gateway.DateRange dateRange = DateRange.newBuilder()
                .setStart(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getStart()))
                .setEnd(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getEnd()))
                .build();

        com.vima.gateway.AccommodationInfo accomInfo = AccommodationInfo.newBuilder()
                .setAccomId(httpRequest.getAccomId())
                .setMinGuests(httpRequest.getMinGuests())
                .setMaxGuests(httpRequest.getMaxGuests())
                .setCity(httpRequest.getCity())
                .setCountry(httpRequest.getCountry())
                .build();

        return ReservationRequest.newBuilder()
                .setNumOfGuests(httpRequest.getNumOfGuests())
                .setDesiredDate(dateRange)
                .setAccommodationInfo(accomInfo)
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

}
