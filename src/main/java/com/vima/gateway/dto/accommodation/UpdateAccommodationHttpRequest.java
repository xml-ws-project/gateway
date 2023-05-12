package com.vima.gateway.dto.accommodation;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

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
public class UpdateAccommodationHttpRequest {

	@NotBlank
	String accommodationId;
	LocalDate start;
	LocalDate end;
	Double price;
}
