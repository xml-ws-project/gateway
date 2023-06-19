package com.vima.gateway.controller;

import com.vima.gateway.AccommodationServiceGrpc;
import com.vima.gateway.dto.grpcObjects.gRPCObjectAccom;
import com.vima.gateway.dto.grpcObjects.gRPCObjectUser;
import com.vima.gateway.dto.notification.EditNotificationHttpRequest;
import com.vima.gateway.mapper.notification.NotificationMapper;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

	@Value("${channel.address.auth-ms}")
	private String channelAddress;

	@PatchMapping("/")
	public ResponseEntity<?> editNotificationOptions(@RequestBody @Valid EditNotificationHttpRequest request) {
		var userBlockingStub = getBlockingStub();
		var response = userBlockingStub.getStub().editNotificationOptions(NotificationMapper.convertHttpToGrpcEditRequest(request));
		return ResponseEntity.ok(NotificationMapper.convertGrpcToHttpResponse(response));
	}

	private gRPCObjectUser getBlockingStub() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress(channelAddress, 9092)
			.usePlaintext()
			.build();
		return gRPCObjectUser.builder()
			.channel(channel)
			.stub(communication.userDetailsServiceGrpc.newBlockingStub(channel))
			.build();
	}
}
