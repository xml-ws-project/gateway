package com.vima.gateway.dto.notification;

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
public class NotificationHttpResponse {

	Long id;
	boolean reservationRequest;
	boolean reservationCancellation;
	boolean profileRating;
	boolean accommodationRating;
	boolean distinguishedHostStatus;
	boolean hostsReservationAnswer;
}
