package com.vima.gateway.dto.reservation;

import com.vima.gateway.enums.reservation.ReservationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReservationHttpResponse {

    String id;
    int numOfGuests;
    ReservationStatus status;
    AccommodationInfoHttpResponse accomInfo;
    String userId;
    LocalDate start;
    LocalDate end;
}
