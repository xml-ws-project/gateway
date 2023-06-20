package com.vima.gateway.dto.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AvgAccommodationRateHttpResponse {
    double avgRate;
    String name;
}
