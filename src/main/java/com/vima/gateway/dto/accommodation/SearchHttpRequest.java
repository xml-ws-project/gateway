package com.vima.gateway.dto.accommodation;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

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
public class SearchHttpRequest {

	String country;
	String city;
	int guests;
	LocalDate start;
	LocalDate end;
	int pageSize;
	int pageNumber;
}
