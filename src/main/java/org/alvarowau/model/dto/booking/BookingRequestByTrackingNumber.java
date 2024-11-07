package org.alvarowau.model.dto.booking;

public record BookingRequestByTrackingNumber(String appointmentTrackingNumber, String customerUsername) {
}
