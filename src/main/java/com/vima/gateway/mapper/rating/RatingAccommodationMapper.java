package com.vima.gateway.mapper.rating;

import com.vima.gateway.RatingAccoommodationService;
import com.vima.gateway.RatingServiceOuterClass;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.rating.EditRatingAccommodationHttpRequest;
import com.vima.gateway.dto.rating.RatingAccommodationHttpRequest;
import com.vima.gateway.dto.rating.RatingAccommodationHttpResponse;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
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
}
