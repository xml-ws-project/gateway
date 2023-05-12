package com.vima.gateway.dto.accommodation;

import com.vima.gateway.enums.PaymentType;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AccommodationHttpRequest {

	@NotBlank
	String name;
	@NotBlank
	String hostId;
	@NotBlank
	String country;
	@NotBlank
	String city;
	@NotBlank
	String street;
	@NotBlank
	String number;
	@NotBlank
	String postalCode;
	List<String> images;
	@NotNull
	int minGuests;
	@NotNull
	int maxGuests;
	@NotNull
	PaymentType paymentType;
	@NotNull
	boolean automaticAcceptance;
	@NotNull
	double regularPrice;
	@NotNull
	List<String> benefitsIds;
	@NotNull
	LocalDate start;
	@NotNull
	LocalDate end;
}
