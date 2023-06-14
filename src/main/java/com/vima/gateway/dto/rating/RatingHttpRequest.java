package com.vima.gateway.dto.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RatingHttpRequest {

    @NotNull
    int value;

    @NotNull
    Long hostId;

    @NotNull
    Long guestId;
}
