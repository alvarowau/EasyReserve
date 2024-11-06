package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.exception.schedule.ServiceOfferingNotFoundException;
import org.alvarowau.model.dto.feedback.FeedbackRequest;
import org.alvarowau.model.dto.feedback.FeedbackResponse;
import org.alvarowau.model.dto.feedback.ProviderAverageRating;
import org.alvarowau.model.dto.mapper.MapperFeedback;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.entity.Booking;
import org.alvarowau.model.entity.Feedback;
import org.alvarowau.model.enums.FeedbackRating;
import org.alvarowau.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackManagementService {

    private final FeedbackRepository feedbackRepository;
    private final BookingManagementService bookingManagementService;
    private final MapperFeedback feedbackMapper;
    private final SecurityContextUtil securityContextUtil;
    private final ServiceOfferingManagementService serviceOfferingManagementService;

    public FeedbackResponse submitFeedbackByCustomer(FeedbackRequest feedbackRequest) {
        Booking booking = bookingManagementService.findByBookingNumber(feedbackRequest.bookingNumber());
        Feedback feedback = buildFeedback(feedbackRequest, booking);
        return feedbackMapper.toFeedbackResponse(feedbackRepository.save(feedback));
    }

    private Feedback buildFeedback(FeedbackRequest feedbackRequest, Booking booking) {
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

    public List<FeedbackResponse> getAllFeedbacksByCustomer() {
        return feedbackRepository
                .findByBookingCustomerUsername(securityContextUtil.getAuthenticatedUsername()).stream()
                .map(feedbackMapper::toFeedbackResponse).toList();
    }

    public List<FeedbackResponse> getFeedbacksByServiceOfferingName(String serviceOfferingName) {
        List<ServiceOfferingResponse> services = serviceOfferingManagementService
                .getServiceOfferingsByProviderUsername(securityContextUtil.getAuthenticatedUsername());

        for (ServiceOfferingResponse service : services) {
            if (service.name().equals(serviceOfferingName)) {
                return feedbackRepository
                        .findDistinctByServiceOfferingName(serviceOfferingName)
                        .stream()
                        .map(feedbackMapper::toFeedbackResponse)
                        .toList();
            }
        }

        throw new ServiceOfferingNotFoundException(serviceOfferingName);
    }

    public List<FeedbackResponse> getFeedbacksByProviderUsername(String providerUsername) {
        return feedbackRepository
                .findByBookingAppointmentServiceScheduleServiceOfferingProviderUsername(providerUsername)
                .stream().map(feedbackMapper::toFeedbackResponse).toList();
    }

    public ProviderAverageRating getAverageRatingByProviderUsername(String providerUsername) {
        BigDecimal averageRating = feedbackRepository.findAverageRatingByProviderUsername(providerUsername);

        if (averageRating == null) {
            averageRating = BigDecimal.ZERO;
        }

        BigDecimal roundedRating = averageRating.setScale(1, RoundingMode.HALF_UP);

        return createProviderAverageRating(providerUsername, roundedRating);
    }

    private ProviderAverageRating createProviderAverageRating(String providerUsername, BigDecimal averageRating) {
        return new ProviderAverageRating(providerUsername, averageRating);
    }

}
