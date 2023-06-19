package com.vima.gateway.controller;

import com.google.protobuf.BoolValue;
import com.vima.gateway.*;
import com.vima.gateway.dto.grpcObjects.gRPCObjectRes;
import com.vima.gateway.dto.reservation.HostHttpResponse;
import com.vima.gateway.dto.reservation.ReservationHttpRequest;
import com.vima.gateway.dto.reservation.ReservationHttpResponse;
import com.vima.gateway.dto.reservation.UserHttpRequest;
import com.vima.gateway.mapper.reservation.ReservationMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    @Value("${channel.address.reservation-ms}")
    private String channelAddress;

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

    @GetMapping("/")
    public ResponseEntity<List<ReservationHttpResponse>> findAll(){
        var response = getBlockingStub().getStub().findAll(Empty.newBuilder().build());
        getBlockingStub().getChannel().shutdown();
        return  ResponseEntity.ok(ReservationMapper.convertGrpcToHttpList(response));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable("id") final String id){
        var response = getBlockingStub().getStub().cancelReservation(Uuid.newBuilder().setValue(id).build());
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(response.getValue());
    }

    @PutMapping("/host-response")
    public ResponseEntity<String> reservationResponse(@RequestBody @Valid final HostHttpResponse response){
        var result = getBlockingStub().getStub().hostResponse(ReservationMapper.convertHostResponseToGrpc(response));
        getBlockingStub().getChannel().shutdown();
        return ResponseEntity.ok(result.getValue());
    }

    @PostMapping("/user/all")
    public ResponseEntity<List<ReservationHttpResponse>> findAllByUser(@RequestBody @Valid final UserHttpRequest request){
        var result = getBlockingStub().getStub().findAllByUser(ReservationMapper.convertUserRequestToGrpc(request));
        getBlockingStub().getChannel().shutdown();
        return  ResponseEntity.ok(ReservationMapper.convertGrpcToHttpList(result));
    }

    private gRPCObjectRes getBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(channelAddress, 9094)
                .usePlaintext()
                .build();
        return gRPCObjectRes.builder()
                .channel(channel)
                .stub(ReservationServiceGrpc.newBlockingStub(channel))
                .build();
    }
}