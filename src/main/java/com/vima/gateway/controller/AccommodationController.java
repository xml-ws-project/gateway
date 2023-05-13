package com.vima.gateway.controller;

import com.vima.gateway.Empty;
import com.vima.gateway.AccommodationList;
import com.vima.gateway.AccommodationResponse;
import com.vima.gateway.AccommodationServiceGrpc;
import com.vima.gateway.Uuid;
import com.vima.gateway.dto.accommodation.AccommodationHttpRequest;
import com.vima.gateway.dto.accommodation.AccommodationHttpResponse;
import com.vima.gateway.dto.accommodation.SearchHttpRequest;
import com.vima.gateway.dto.accommodation.UpdateAccommodationHttpRequest;
import com.vima.gateway.dto.additionalBenefit.AdditionalBenefitHttpRequest;
import com.vima.gateway.dto.additionalBenefit.AdditionalBenefitHttpResponse;
import com.vima.gateway.dto.grpcObjects.gRPCObjectAccom;
import com.vima.gateway.dto.specialInfo.SpecialInfoHttpRequest;
import com.vima.gateway.dto.specialInfo.SpecialInfoHttpResponse;
import com.vima.gateway.mapper.accommodation.AccommodationMapper;
import com.vima.gateway.mapper.accommodation.AdditionalBenefitMapper;
import com.vima.gateway.mapper.accommodation.SpecialInfoMapper;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

	@PostMapping(value = "/")
	public ResponseEntity<AccommodationHttpResponse> create(@RequestBody @Valid final AccommodationHttpRequest request) {
		AccommodationResponse response = getBlockingStub().getStub().create(AccommodationMapper.convertHttpToGrpc(request));
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(AccommodationMapper.convertGrpcToHttp(response));
	}

	@PatchMapping("/")
	public ResponseEntity<?> update(final @RequestBody @Valid UpdateAccommodationHttpRequest request) {
		var response = getBlockingStub().getStub().update(AccommodationMapper.convertHttpToGrpcUpdate(request));
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccommodationHttpResponse> findById(@PathVariable("id") final String id) {
		var response = getBlockingStub().getStub().findById(Uuid.newBuilder().setValue(id).build());
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(AccommodationMapper.convertGrpcToHttp(response));
	}


	@GetMapping("/all/{id}")
	public ResponseEntity<List<AccommodationHttpResponse>> findAllByHostId(@PathVariable("id") final String id) {
		var response = getBlockingStub().getStub().findAllByHostId(Uuid.newBuilder().setValue(id).build());
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(AccommodationMapper.convertGrpcToHttpList(response));
	}

	@PostMapping("/benefit")
	public ResponseEntity<AdditionalBenefitHttpResponse> addBenefit(@RequestBody @Valid final AdditionalBenefitHttpRequest benefit) {
		var response = getBlockingStub().getStub().addBenefit(AdditionalBenefitMapper.convertHttpToGrpc(benefit));
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(AdditionalBenefitMapper.convertGrpcToHttp(response));
	}

	@PostMapping("/special-period")
	public ResponseEntity<SpecialInfoHttpResponse> createSpecialPeriod(@RequestBody @Valid final SpecialInfoHttpRequest request) {
		var response = getBlockingStub().getStub().createSpecialPeriod(SpecialInfoMapper.convertHttpToGrpc(request));
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(SpecialInfoMapper.convertGrpcToHttp(response));
	}

	@GetMapping("/all")
	public ResponseEntity<List<AccommodationHttpResponse>> findAll() {
		Empty empty = Empty.newBuilder().build();
		AccommodationList response = getBlockingStub().getStub().findAll(empty);
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(AccommodationMapper.convertGrpcToHttpList(response));
	}

	@PostMapping("/search")
	public ResponseEntity<List<AccommodationHttpResponse>> search(@RequestBody @Valid final SearchHttpRequest searchRequest) {
		AccommodationList response = getBlockingStub().getStub().searchAccommodation(AccommodationMapper.convertSearchRequest(searchRequest));
		getBlockingStub().getChannel().shutdown();
		return ResponseEntity.ok(AccommodationMapper.convertGrpcToHttpList(response));
	}

	private gRPCObjectAccom getBlockingStub() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9093)
			.usePlaintext()
			.build();
		return gRPCObjectAccom.builder()
			.channel(channel)
			.stub(AccommodationServiceGrpc.newBlockingStub(channel))
			.build();
	}
}
