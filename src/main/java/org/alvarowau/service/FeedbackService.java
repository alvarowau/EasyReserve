package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.feedback.FeedbackRequest;
import org.alvarowau.model.dto.feedback.FeedbackResponse;
import org.alvarowau.model.dto.mapper.MapperFeedback;
import org.alvarowau.model.entity.Booking;
import org.alvarowau.model.entity.Feedback;
import org.alvarowau.model.enums.FeedbackRating;
import org.alvarowau.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingService bookingService;
    private final MapperFeedback feedbackMapper;

    public FeedbackResponse submitFeedback(FeedbackRequest feedbackRequest) { // Renombrado a submitFeedback
        Booking booking = bookingService.findByBookingNumber(feedbackRequest.bookingNumber());
        Feedback feedback = buildFeedback(feedbackRequest, booking); // Renombrado a buildFeedback
        return feedbackMapper.toFeedbackResponse(feedbackRepository.save(feedback));
    }

    private Feedback buildFeedback(FeedbackRequest feedbackRequest, Booking booking) { // Renombrado a buildFeedback
        int rating = feedbackRequest.rating();
        if (rating > 5) {
            rating = 5;
        } else if (rating < 1) {
            rating = 1;
        }
        return Feedback.builder()
                .booking(booking)
                .comment(feedbackRequest.feedback())
                .rating(FeedbackRating.fromValue(rating))
                .build();
    }
}
