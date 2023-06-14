package com.vima.gateway.dto.grpcObjects;

import com.vima.gateway.RatingServiceGrpc;
import io.grpc.ManagedChannel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class gRPCObjectRating {

    ManagedChannel channel;
    RatingServiceGrpc.RatingServiceBlockingStub stub;
}
