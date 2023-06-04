package com.vima.gateway.controller;

import com.vima.gateway.RecommendationServiceGrpc;
import com.vima.gateway.Uuid;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRec;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    @PostMapping("/create/user/{id}")
    public ResponseEntity<String> createUserNod(@PathVariable("id") final String userId){
        var response = getBlockingStub().getStub().createUserNod(Uuid.newBuilder().setValue(userId).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    @PostMapping("/create/accom/{id}")
    public ResponseEntity<String> createAccomNod(@PathVariable("id") final String accomId){
        var response = getBlockingStub().getStub().createAccomNod(Uuid.newBuilder().setValue(accomId).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    private gRPCObjectRec getBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9094)
                .usePlaintext()
                .build();
        return gRPCObjectRec.builder()
                .channel(channel)
                .stub(RecommendationServiceGrpc.newBlockingStub(channel))
                .build();
    }
}
