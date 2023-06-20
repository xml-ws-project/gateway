package com.vima.gateway.mapper.rating;

import com.vima.gateway.RatingServiceOuterClass;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.rating.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public static RatingServiceOuterClass.EditRatingRequest convertEditHttpToGrpc(EditRatingHttpRequest request){
        var result = RatingServiceOuterClass.EditRatingRequest.newBuilder()
                .setId(request.getId())
                .setNewValue(request.getNewValue())
                .build();
        return result;
    }

    public static HostRatingHttpResponse convertGrpcToHttpForHost(RatingServiceOuterClass.RatingByHostId grpcResponse){
        var result = HostRatingHttpResponse.builder()
                .id(grpcResponse.getId())
                .value(grpcResponse.getValue())
                .username(grpcResponse.getUsername())
                .guestId(grpcResponse.getGuestId())
                .date(LocalDateConverter.convertGoogleTimeStampToLocalDate(grpcResponse.getDate()))
                .build();
        return result;
    }

    public static List<HostRatingHttpResponse> convertGrpcToHttpList(RatingServiceOuterClass.RatingList grpcResponseList){
        List<HostRatingHttpResponse> httpResponseList = new ArrayList<>();
        grpcResponseList.getResponseList().forEach(response ->{
            httpResponseList.add(convertGrpcToHttpForHost(response));
        });
        return httpResponseList;
    }

    public static AvgRateHttpResponse convertAvgRateGrpcToHttp(RatingServiceOuterClass.AvgRate response){
        var result = AvgRateHttpResponse.builder()
                .avgRate(response.getAvgRate())
                .firstName(response.getFirstName())
                .lastName(response.getLastName())
                .build();
        return result;
    }


}
