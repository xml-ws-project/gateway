package com.vima.gateway.dto.reservation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class HostHttpResponse {

    @NotNull
    String id;

    @NotNull
    boolean accept;
}
