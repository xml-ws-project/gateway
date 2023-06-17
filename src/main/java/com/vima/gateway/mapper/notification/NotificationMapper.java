package com.vima.gateway.mapper.notification;

import com.vima.gateway.dto.notification.EditNotificationHttpRequest;
import com.vima.gateway.dto.notification.NotificationHttpResponse;

import communication.EditNotificationRequest;
import communication.NotificationOptionsResponse;

public class NotificationMapper {

	public static EditNotificationRequest convertHttpToGrpcEditRequest(EditNotificationHttpRequest request) {
		return EditNotificationRequest.newBuilder()
			.setId(request.getUserId())
			.setAccommodationRating(request.isAccommodationRating())
			.setDistinguishedHostStatus(request.isDistinguishedHostStatus())
			.setProfileRating(request.isProfileRating())
			.setReservationRequest(request.isReservationRequest())
			.setReservationCancellation(request.isReservationCancellation())
			.setHostsReservationAnswer(request.isHostsReservationAnswer())
			.build();
	}

	public static NotificationHttpResponse convertGrpcToHttpResponse(NotificationOptionsResponse response) {
		return NotificationHttpResponse.builder()
			.id(response.getId())
			.accommodationRating(response.getAccommodationRating())
			.distinguishedHostStatus(response.getDistinguishedHostStatus())
			.hostsReservationAnswer(response.getHostsReservationAnswer())
			.profileRating(response.getProfileRating())
			.reservationCancellation(response.getReservationCancellation())
			.reservationRequest(response.getReservationRequest())
			.build();
	}
}
