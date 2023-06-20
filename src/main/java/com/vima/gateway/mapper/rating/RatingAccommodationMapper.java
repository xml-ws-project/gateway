package com.vima.gateway.mapper.rating;

import com.vima.gateway.RatingAccoommodationService;
import com.vima.gateway.RatingServiceOuterClass;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.rating.*;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RatingAccommodationMapper {

    public static RatingAccoommodationService.RatingAccommodationRequest convertHttpToGrpc(@Valid RatingAccommodationHttpRequest request){
        var result = RatingAccoommodationService.RatingAccommodationRequest.newBuilder()
                .setValue(request.getValue())
                .setAccommodationId(String.valueOf(request.getAccommodationId()))
                .setGuestId(request.getGuestId())
                .build();
        return result;
    }

    public static RatingAccommodationHttpResponse convertGrpcToHttp(RatingAccoommodationService.RatingAccommodationResponse response){
        var result = RatingAccommodationHttpResponse.builder()
                .id(response.getId())
                .value(response.getValue())
                .accommodationId(UUID.fromString(response.getAccommodationId()))
                .guestId(response.getGuestId())
                .date(LocalDateConverter.convertGoogleTimeStampToLocalDate(response.getDate()))
                .build();
        return result;
    }

    public static RatingAccoommodationService.EditRatingAccommodationRequest convertEditHttpToGrpc(EditRatingAccommodationHttpRequest request){
        var result = RatingAccoommodationService.EditRatingAccommodationRequest.newBuilder()
                .setId(request.getId())
                .setNewValue(request.getNewValue())
                .build();
        return result;
    }
    public static AccommodationRatingHttpResponse convertGrpcToHttpForAccommodation(RatingAccoommodationService.RatingByAccommodationId grpcResponse){
        var result = AccommodationRatingHttpResponse.builder()
                .id(grpcResponse.getId())
                .value(grpcResponse.getValue())
                .username(grpcResponse.getUsername())
                .guestId(grpcResponse.getGuestId())
                .date(LocalDateConverter.convertGoogleTimeStampToLocalDate(grpcResponse.getDate()))
                .build();
        return result;
    }

    public static List<AccommodationRatingHttpResponse> convertGrpcToHttpList(RatingAccoommodationService.RatingAccommodationList grpcResponseList){
        List<AccommodationRatingHttpResponse> httpResponseList = new ArrayList<>();
        grpcResponseList.getResponseList().forEach(response ->{
            httpResponseList.add(convertGrpcToHttpForAccommodation(response));
        });
        return httpResponseList;
    }

    public static AvgAccommodationRateHttpResponse convertAvgAccommodationRateGrpcToHttp(RatingAccoommodationService.AvgAccommorationRate response){
        var result = AvgAccommodationRateHttpResponse.builder()
                .avgRate(response.getAvgRate())
                .name(response.getName())
                .build();
        return result;
    }
}
