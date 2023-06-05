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
    public void createUserNode(@PathVariable("id") final String userId){
        getBlockingStub().getStub().createUserNode(Uuid.newBuilder().setValue(userId).build());
        getBlockingStub().getChannel().shutdown();
    }

    @PostMapping("/create/accom/{id}")
    public void createAccomNode(@PathVariable("id") final String accomId){
        getBlockingStub().getStub().createAccomNode(Uuid.newBuilder().setValue(accomId).build());
        getBlockingStub().getChannel().shutdown();
    }

    private gRPCObjectRec getBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9095)
                .usePlaintext()
                .build();
        return gRPCObjectRec.builder()
                .channel(channel)
                .stub(RecommendationServiceGrpc.newBlockingStub(channel))
                .build();
    }
}
