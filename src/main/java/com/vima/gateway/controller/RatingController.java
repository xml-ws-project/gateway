package com.vima.gateway.controller;

import com.vima.gateway.RatingServiceGrpc;
import com.vima.gateway.RatingServiceOuterClass;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRating;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRes;
import com.vima.gateway.dto.rating.*;
import com.vima.gateway.mapper.rating.RatingMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") final Long id){
        var response = getBlockingStub().getStub().delete(RatingServiceOuterClass.LONG.newBuilder().setValue(id).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@RequestBody @Valid final EditRatingHttpRequest request){
        var response = getBlockingStub().getStub().edit(RatingMapper.convertEditHttpToGrpc(request));
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<HostRatingHttpResponse>> findAllByHostId(@PathVariable("id") final Long id){
       var response  =getBlockingStub().getStub().findAllByHostId(RatingServiceOuterClass.LONG.newBuilder().setValue(id).build());
       getBlockingStub().getChannel().shutdown();
       return  ResponseEntity.ok(RatingMapper.convertGrpcToHttpList(response));
    }

    @GetMapping("avg/{id}")
    public ResponseEntity<AvgRateHttpResponse> findAvgRate(@PathVariable("id") final Long id){
        var response = getBlockingStub().getStub().findAvgRate(RatingServiceOuterClass.LONG.newBuilder().setValue(id).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(RatingMapper.convertAvgRateGrpcToHttp(response));
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
