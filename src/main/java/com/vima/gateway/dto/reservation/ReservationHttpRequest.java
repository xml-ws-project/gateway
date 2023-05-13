package com.vima.gateway.dto.reservation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReservationHttpRequest {

    @NotNull
    int numOfGuests;

    @NotNull
    LocalDate start;

    @NotNull
    LocalDate end;

    @NotBlank
    String accomId;

    @NotNull
    int minGuests;

    @NotNull
    int maxGuests;

    @NotBlank
    String city;

    @NotBlank
    String country;

    @NotBlank
    String userId;
}
