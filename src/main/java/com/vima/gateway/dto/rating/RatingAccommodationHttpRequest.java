package com.vima.gateway.dto.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RatingAccommodationHttpRequest {
    @NotNull
    int value;
    @NotNull
    UUID accommodationId;
    @NotNull
    Long guestId;
}
