package com.vima.gateway.dto;

import com.vima.gateway.AccommodationServiceGrpc;

import io.grpc.ManagedChannel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class gRPCObject {

	ManagedChannel channel;
	AccommodationServiceGrpc.AccommodationServiceBlockingStub stub;
}
