package com.vima.gateway.mapper;

import com.vima.gateway.DataRange;
import com.vima.gateway.SpecialInfoRequest;
import com.vima.gateway.SpecialInfoResponse;
import com.vima.gateway.converter.LocalDateConverter;
import com.vima.gateway.dto.specialInfo.SpecialInfoHttpRequest;
import com.vima.gateway.dto.specialInfo.SpecialInfoHttpResponse;
import com.vima.gateway.enums.PeriodType;

import java.util.UUID;

public class SpecialInfoMapper {

	public static SpecialInfoHttpResponse convertGrpcToHttp(SpecialInfoResponse grpcResponse) {
		return SpecialInfoHttpResponse.builder()
			.id(UUID.fromString(grpcResponse.getId().getValue()))
			.accommodationId(UUID.fromString(grpcResponse.getAccommodationId().getValue()))
			.specialPrice(grpcResponse.getSpecialPrice())
			.periodType(PeriodType.valueOf(grpcResponse.getPeriodType().toString()))
			.build();
	}

	public static SpecialInfoRequest convertHttpToGrpc(SpecialInfoHttpRequest httpRequest) {
		com.vima.gateway.DataRange dateRange = DataRange.newBuilder()
			.setStart(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getSpecialPeriodStart()))
			.setEnd(LocalDateConverter.convertLocalDateToGoogleTimestamp(httpRequest.getSpecialPeriodEnd()))
			.build();

		return SpecialInfoRequest.newBuilder()
			.setAccommodationId(httpRequest.getAccommodationId())
			.setSpecialPeriod(dateRange)
			.setSpecialPrice(httpRequest.getSpecialPrice())
			.setPeriodType(com.vima.gateway.PeriodType.valueOf(httpRequest.getPeriodType().toString()))
			.build();
	}
}
