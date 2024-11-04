package org.alvarowau.model.dto.booking;

import org.alvarowau.model.enums.OperationStatus;

public record BookingCancellationResponse(String username, String bookingNumber, String reason, OperationStatus status) {
}
