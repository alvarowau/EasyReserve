package org.alvarowau.model.dto;

import org.alvarowau.model.enums.BookingStatus;

public record BookingHistoryResponse(String bookingNumber, String appointmentDate, BookingStatus status) {}
