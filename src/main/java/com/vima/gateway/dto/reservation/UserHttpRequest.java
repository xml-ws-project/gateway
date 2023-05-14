package com.vima.gateway.dto.reservation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserHttpRequest {

    @NotNull
    String id;

    @NotNull
    String role;
}
