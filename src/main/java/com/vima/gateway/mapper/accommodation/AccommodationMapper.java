package com.vima.gateway.mapper.accommodation;

import com.vima.gateway.*;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.SearchHttpResponse;
import com.vima.gateway.dto.accommodation.*;
import com.vima.gateway.enums.accommodation.PaymentType;

import java.util.ArrayList;
import java.util.List;

public class AccommodationMapper {

	public static AccommodationHttpResponse convertGrpcToHttp(AccommodationResponse grpcResponse) {
		return AccommodationHttpResponse.builder()
			.id(grpcResponse.getId())
			.name(grpcResponse.getName())
			.hostId(grpcResponse.getHostId())
			.country(grpcResponse.getCountry())
			.city(grpcResponse.getCity())
			.street(grpcResponse.getStreet())
			.number(grpcResponse.getNumber())
			.postalCode(grpcResponse.getPostalCode())
			.benefits(AdditionalBenefitMapper.convertGrpcToHttpList(grpcResponse.getBenefitsList()))
			.images(grpcResponse.getImagesList())
			.maxGuests(grpcResponse.getMaxGuests())
			.minGuests(grpcResponse.getMinGuests())
			.regularPrice(grpcResponse.getRegularPrice())
			.paymentType(PaymentType.valueOf(grpcResponse.getPaymentType().toString()))
			.automaticAcceptance(grpcResponse.getAutomaticAcceptance())
			.build();
	}

	public static UpdateAccommodationRequest convertHttpToGrpcUpdate(UpdateAccommodationHttpRequest httpRequest) {
		var updateRequestBuilder = UpdateAccommodationRequest.newBuilder()
			.setAccommodationId(httpRequest.getAccommodationId())
			.setPrice(httpRequest.getPrice());

		UpdateAccommodationRequest updateRequest;

		if (httpRequest.getStart() != null && httpRequest.getEnd() != null) {
			com.vima.gateway.DateRange dateRange = DateRange.newBuilder()
				.setStart(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getStart()))
				.setEnd(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getEnd()))
				.build();
			updateRequest = updateRequestBuilder.setPeriod(dateRange).build();
		} else {
			updateRequest = updateRequestBuilder.build();
		}

		return updateRequest;
	}

	public static AccommodationRequest convertHttpToGrpc(AccommodationHttpRequest httpRequest) {
		com.vima.gateway.DateRange dateRange = DateRange.newBuilder()
			.setStart(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getStart()))
			.setEnd(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getEnd()))
			.build();

		return AccommodationRequest.newBuilder()
			.setName(httpRequest.getName())
			.setHostId(httpRequest.getHostId())
			.setCountry(httpRequest.getCountry())
			.setCity(httpRequest.getCity())
			.setStreet(httpRequest.getStreet())
			.setNumber(httpRequest.getNumber())
			.setPostalCode(httpRequest.getPostalCode())
			.addAllImages(httpRequest.getImages())
			.setMinGuests(httpRequest.getMinGuests())
			.setMaxGuests(httpRequest.getMaxGuests())
			.setPaymentType(com.vima.gateway.PaymentType.valueOf(httpRequest.getPaymentType().toString()))
			.setAutomaticAcceptance(httpRequest.isAutomaticAcceptance())
			.setRegularPrice(httpRequest.getRegularPrice())
			.addAllBenefitsIds(httpRequest.getBenefitsIds())
			.setAvailablePeriod(dateRange)
			.build();
	}

	public static List<AccommodationHttpResponse> convertGrpcToHttpList(AccommodationList grpcResponseList) {
		List<AccommodationHttpResponse> httpResponseList = new ArrayList<>();
		grpcResponseList.getResponseList().forEach(response -> {
			httpResponseList.add(convertGrpcToHttp(response));
		});
		return httpResponseList;
	}

	public static SearchRequest convertSearchRequest(SearchHttpRequest httpRequest) {
		com.vima.gateway.DateRange period = DateRange.newBuilder()
			.build();

		if (httpRequest.getStart() != null && httpRequest.getEnd() != null) {
			period = DateRange.newBuilder()
				.setStart(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getStart()))
				.setEnd(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getEnd()))
				.build();
		}

		return SearchRequest.newBuilder()
			.setCountry(httpRequest.getCountry())
			.setCity(httpRequest.getCity())
			.setGuests(httpRequest.getGuests())
			.setPeriod(period)
			.setPageSize(httpRequest.getPageSize())
			.setPageNumber(httpRequest.getPageNumber())
			.build();
	}

	public static SearchHttpResponse convertToSearchResponse(SearchResponse response) {
		return SearchHttpResponse.builder()
			.accommodation(convertGrpcToHttp(response.getAccommodation()))
			.unitPrice(response.getUnitPrice())
			.totalPrice(response.getTotalPrice())
			.build();
	}

	public static List<SearchHttpResponse> convertToSearchList(SearchList grpcResponse) {
		List<SearchHttpResponse> responseList = new ArrayList<>();
		grpcResponse.getResponseList().forEach(response -> {
			responseList.add(convertToSearchResponse(response));
		});
		return responseList;
	}

	public static AccommodationFilterRequest convertToFilterRequest(FilterHttpRequest request){

			AccommodationFilterRequest.Builder builder = AccommodationFilterRequest.newBuilder()
					.setMinPrice(request.getMinPrice())
					.setMaxPrice(request.getMaxPrice())
					.setHostId(request.getHostName());
			for(String s: request.getBenefits()){
				builder.addBenefits(s);
			}
			return builder.build();


	}
}
