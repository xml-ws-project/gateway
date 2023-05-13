package com.vima.gateway.controller;

import com.google.protobuf.BoolValue;
import com.vima.gateway.ReservationServiceGrpc;
import com.vima.gateway.TextMessage;
import com.vima.gateway.Uuid;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRes;
import com.vima.gateway.dto.reservation.ReservationHttpRequest;
import com.vima.gateway.dto.reservation.ReservationHttpResponse;
import com.vima.gateway.mapper.reservation.ReservationMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    @PostMapping("/")
    public ResponseEntity<ReservationHttpResponse> create(@RequestBody @Valid final ReservationHttpRequest request){
        var response = getBlockingStub().getStub().create(ReservationMapper.convertHttpToGrpc(request));
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(ReservationMapper.convertGrpcToHttp(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationHttpResponse> findById(@PathVariable("id") final String id){
        var response = getBlockingStub().getStub().findById(Uuid.newBuilder().setValue(id).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(ReservationMapper.convertGrpcToHttp(response));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable("id") final String id){
        var response = getBlockingStub().getStub().cancelReservation(Uuid.newBuilder().setValue(id).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    @PutMapping("/respond/{id}")
    public ResponseEntity<TextMessage> reservationResponse(@PathVariable("id") final String id){
        var response = getBlockingStub().getStub().reservationResponse(Uuid.newBuilder().setValue(id).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response);
    }

    private gRPCObjectRes getBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9094)
                .usePlaintext()
                .build();
        return gRPCObjectRes.builder()
                .channel(channel)
                .stub(ReservationServiceGrpc.newBlockingStub(channel))
                .build();
    }
}