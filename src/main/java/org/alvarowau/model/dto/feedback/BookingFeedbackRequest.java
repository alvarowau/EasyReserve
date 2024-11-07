package org.alvarowau.model.dto.feedback;

public record BookingFeedbackRequest(String bookingNumber,
                                     String feedback,
                                     int rating) {
}
