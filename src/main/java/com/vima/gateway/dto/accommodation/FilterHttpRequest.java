package com.vima.gateway.dto.accommodation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FilterHttpRequest {
    int minPrice ;
    int maxPrice;
    List<String> benefits;
    String hostName;
}
