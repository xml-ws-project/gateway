package com.vima.gateway.dto;

import com.vima.gateway.dto.accommodation.AccommodationHttpResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SearchHttpResponse {

	AccommodationHttpResponse accommodation;
	double unitPrice;
	double totalPrice;
}
