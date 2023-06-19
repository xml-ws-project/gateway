package com.vima.gateway.dto.grpcObjects;

import communication.userDetailsServiceGrpc;
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
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Builder
public class gRPCObjectUser {
	ManagedChannel channel;
	userDetailsServiceGrpc.userDetailsServiceBlockingStub stub;
}
