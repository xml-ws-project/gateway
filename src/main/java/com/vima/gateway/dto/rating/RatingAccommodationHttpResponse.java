package com.vima.gateway.dto.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingAccommodationHttpResponse {
    Long id;
    int value;
    UUID accommodationId;
    Long guestId;
    LocalDate date;
}
