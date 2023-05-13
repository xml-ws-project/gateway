package com.vima.gateway.dto.specialInfo;

import com.vima.gateway.enums.accommodation.PeriodType;

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
public class SpecialInfoHttpRequest {
	@NotNull
	String accommodationId;
	@NotNull
	LocalDate specialPeriodStart;
	@NotNull
	LocalDate specialPeriodEnd;
	@NotNull
	double specialPrice;
	@NotNull
	PeriodType periodType;
}
