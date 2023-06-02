package com.vima.gateway.mapper.rating;

import com.vima.gateway.RatingServiceOuterClass;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.rating.RatingHttpRequest;
import com.vima.gateway.dto.rating.RatingHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public static RatingServiceOuterClass.RatingRequest convertHttpToGrpc(RatingHttpRequest request){
        var result = RatingServiceOuterClass.RatingRequest.newBuilder()
                .setValue(request.getValue())
                .setHostId(request.getHostId())
                .setGuestId(request.getGuestId())
                .build();

        return result;
    }

    public static RatingHttpResponse convertGrpcToHttp(RatingServiceOuterClass.RatingResponse response){
        var result = RatingHttpResponse.builder()
                .id(response.getId())
                .value(response.getValue())
                .hostId(response.getHostId())
                .guestId(response.getGuestId())
                .date(LocalDateConverter.convertGoogleTimeStampToLocalDate(response.getDate()))
                .build();

        return  result;
    }

}
