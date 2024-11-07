package org.alvarowau.model.dto.booking;

import org.alvarowau.model.enums.OperationStatus;

public record BookingCancellationStatusResponse(String username, String bookingNumber, String reason, OperationStatus status) {
}
