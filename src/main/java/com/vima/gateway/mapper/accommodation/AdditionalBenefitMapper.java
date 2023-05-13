package com.vima.gateway.mapper.accommodation;

import com.vima.gateway.AdditionalBenefitRequest;
import com.vima.gateway.AdditionalBenefitResponse;
import com.vima.gateway.dto.additionalBenefit.AdditionalBenefitHttpRequest;
import com.vima.gateway.dto.additionalBenefit.AdditionalBenefitHttpResponse;

import java.util.ArrayList;
import java.util.List;

public class AdditionalBenefitMapper {

	public static AdditionalBenefitRequest convertHttpToGrpc(AdditionalBenefitHttpRequest httpRequest) {
		return AdditionalBenefitRequest.newBuilder()
			.setName(httpRequest.getName())
			.setIcon(httpRequest.getIcon())
			.build();
	}

	public static AdditionalBenefitHttpResponse convertGrpcToHttp(AdditionalBenefitResponse grpcResponse) {
		return AdditionalBenefitHttpResponse.builder()
			.id(grpcResponse.getId())
			.name(grpcResponse.getName())
			.icon(grpcResponse.getIcon())
			.build();
	}

	public static List<AdditionalBenefitHttpResponse> convertGrpcToHttpList(List<AdditionalBenefitResponse> grpcResponseList) {
		List<AdditionalBenefitHttpResponse> responseList = new ArrayList<>();
		grpcResponseList.forEach(grpcResponse -> {
			responseList.add(convertGrpcToHttp(grpcResponse));
		});
		return responseList;
	}

}
