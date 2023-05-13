package com.vima.gateway.dto.accommodation;

import com.vima.gateway.dto.additionalBenefit.AdditionalBenefitHttpResponse;
import com.vima.gateway.enums.accommodation.PaymentType;

import java.util.List;

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
public class AccommodationHttpResponse {

	String id;
	String name;
	String hostId;
	String country;
	String city;
	String street;
	String number;
	String postalCode;
	List<AdditionalBenefitHttpResponse> benefits;
	List<String> images;
	int minGuests;
	int maxGuests;
	double regularPrice;
	PaymentType paymentType;
}
