package com.vima.gateway.dto.specialInfo;

import com.vima.gateway.enums.PeriodType;

import java.time.LocalDate;
import java.util.UUID;

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
public class SpecialInfoHttpResponse {

	UUID id;
	UUID accommodationId;
//	LocalDate specialPeriodStart;
//	LocalDate specialPeriodEnd;
	double specialPrice;
	PeriodType periodType;
}
