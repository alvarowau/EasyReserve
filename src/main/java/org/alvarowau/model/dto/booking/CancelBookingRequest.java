package org.alvarowau.model.dto.booking;

public record CancelBookingRequest(String bookingNumber,
                                   String usernameCustomer,
                                   String reason) {
}
