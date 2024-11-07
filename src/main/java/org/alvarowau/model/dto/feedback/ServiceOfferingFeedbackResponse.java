package org.alvarowau.model.dto.feedback;

import org.alvarowau.model.enums.FeedbackRating;

public record ServiceOfferingFeedbackResponse(
        String serviceOfferingName,
        String customerUsername,
        String feedback,
        FeedbackRating rating
){
}
