package com.vima.gateway.dto.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EditRatingAccommodationHttpRequest {

    @NotNull
    Long id;

    @NotNull
    int newValue;
}
