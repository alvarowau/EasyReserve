package org.alvarowau.model.dto.booking;

import org.alvarowau.model.enums.BookingStatus;

import java.time.LocalDate;

public record BookingResponseCreate(String trackingNumber,
                                    LocalDate date,
                                    String timeRange,
                                    String bookingNumber,
                                    BookingStatus status,
                                    String usernameCustomer) {
}