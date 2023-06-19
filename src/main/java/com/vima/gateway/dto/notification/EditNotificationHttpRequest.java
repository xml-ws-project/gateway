package com.vima.gateway.dto.notification;

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
public class EditNotificationHttpRequest {

	@NotNull
	Long userId;
	@NotNull
	boolean reservationRequest;
	@NotNull
	boolean reservationCancellation;
	@NotNull
	boolean profileRating;
	@NotNull
	boolean accommodationRating;
	@NotNull
	boolean distinguishedHostStatus;
	@NotNull
	boolean hostsReservationAnswer;
}
