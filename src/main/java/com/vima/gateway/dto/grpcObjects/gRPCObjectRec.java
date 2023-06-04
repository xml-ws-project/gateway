package com.vima.gateway.dto.grpcObjects;

import com.vima.gateway.RecommendationServiceGrpc;
import io.grpc.ManagedChannel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class gRPCObjectRec {
    ManagedChannel channel;
    RecommendationServiceGrpc.RecommendationServiceBlockingStub stub;

}
