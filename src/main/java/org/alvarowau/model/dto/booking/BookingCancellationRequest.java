package org.alvarowau.model.dto.booking;

public record BookingCancellationRequest(String bookingNumber,
                                         String customerUsername,
                                         String reason) {
}
