package com.vima.gateway.dto.grpcObjects;

import com.vima.gateway.ReservationServiceGrpc;
import io.grpc.ManagedChannel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Builder
public class gRPCObjectRes {
    ManagedChannel channel;
    ReservationServiceGrpc.ReservationServiceBlockingStub stub;
}
