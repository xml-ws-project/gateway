package com.vima.gateway.dto.rating;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class HostRatingHttpResponse {

    Long id;
    int value;
    String username;
    Long guestId;
    LocalDate date;

}
