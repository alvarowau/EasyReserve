package org.alvarowau.model.dto.feedback;

public record FeedbackRequest(String bookingNumber,
                              String feedback,
                              int rating) {
}
