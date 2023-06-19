package com.vima.gateway.dto.grpcObjects;

import com.vima.gateway.RatingAccommodationServiceGrpc;
import io.grpc.ManagedChannel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class gRPCObjectRatingAccommodation {
    ManagedChannel channel;
    RatingAccommodationServiceGrpc.RatingAccommodationServiceBlockingStub stub;
}
