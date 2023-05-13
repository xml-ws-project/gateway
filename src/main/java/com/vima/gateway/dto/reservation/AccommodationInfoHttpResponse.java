package com.vima.gateway.dto.reservation;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AccommodationInfoHttpResponse {
    String accomId;
    int minGuests;
    int maxGuests;
    String city;
    String country;
}
