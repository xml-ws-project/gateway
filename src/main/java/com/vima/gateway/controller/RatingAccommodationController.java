package com.vima.gateway.controller;

import com.vima.gateway.RatingAccommodationServiceGrpc;
import com.vima.gateway.RatingAccoommodationService;
import com.vima.gateway.RatingServiceGrpc;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRatingAccommodation;
import com.vima.gateway.dto.rating.EditRatingAccommodationHttpRequest;
import com.vima.gateway.dto.rating.EditRatingHttpRequest;
import com.vima.gateway.dto.rating.RatingAccommodationHttpRequest;
import com.vima.gateway.dto.rating.RatingAccommodationHttpResponse;
import com.vima.gateway.mapper.rating.RatingAccommodationMapper;
import com.vima.gateway.mapper.rating.RatingMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRating;

import javax.validation.Valid;

@RestController
@RequestMapping("/rating-accommodation")
@RequiredArgsConstructor
public class RatingAccommodationController {
    @PostMapping("/")
    public ResponseEntity<RatingAccommodationHttpResponse>  create (@RequestBody @Valid final RatingAccommodationHttpRequest request) {
        var response = getBlockingStub().getStub().create(RatingAccommodationMapper.convertHttpToGrpc(request));
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(RatingAccommodationMapper.convertGrpcToHttp(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final Long id){
        var response = getBlockingStub().getStub().delete(RatingAccoommodationService.ID.newBuilder().setId(id).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@RequestBody @Valid final EditRatingAccommodationHttpRequest request){
        var response = getBlockingStub().getStub().edit(RatingAccommodationMapper.convertEditHttpToGrpc(request));
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    private gRPCObjectRatingAccommodation getBlockingStub(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9093)
                .usePlaintext()
                .build();

        return gRPCObjectRatingAccommodation.builder()
                .channel(channel)
                .stub(RatingAccommodationServiceGrpc.newBlockingStub(channel))
                .build();
    }
}
