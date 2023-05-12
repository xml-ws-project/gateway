package com.vima.gateway.converter;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalDateConverter {
	public static Timestamp convertLocalDateToGoogleTimestamp(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
		return Timestamp.newBuilder()
			.setSeconds(instant.getEpochSecond())
			.setNanos(instant.getNano())
			.build();
	}
}
