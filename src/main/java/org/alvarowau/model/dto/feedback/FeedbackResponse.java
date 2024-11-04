package org.alvarowau.model.dto.feedback;

import org.alvarowau.model.enums.FeedbackRating;

public record FeedbackResponse (
        String serviceOfferingName,
        String CustomerUsername,
        String feedback,
        FeedbackRating rating
){
}
