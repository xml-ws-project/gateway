package com.vima.gateway.controller;

import com.vima.gateway.RatingServiceGrpc;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRating;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRes;
import com.vima.gateway.dto.rating.RatingHttpRequest;
import com.vima.gateway.dto.rating.RatingHttpResponse;
import com.vima.gateway.mapper.rating.RatingMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    @PostMapping("/")
    public ResponseEntity<RatingHttpResponse> create(@RequestBody @Valid final RatingHttpRequest request){
        var response = getBlockingStub().getStub().create(RatingMapper.convertHttpToGrpc(request));
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(RatingMapper.convertGrpcToHttp(response));
    }

    private gRPCObjectRating getBlockingStub(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();

        return gRPCObjectRating.builder()
                .channel(channel)
                .stub(RatingServiceGrpc.newBlockingStub(channel))
                .build();
    }
}
