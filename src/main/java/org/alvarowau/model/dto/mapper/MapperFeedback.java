package org.alvarowau.model.dto.mapper;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.feedback.FeedbackRequest;
import org.alvarowau.model.dto.feedback.FeedbackResponse;
import org.alvarowau.model.entity.Booking;
import org.alvarowau.model.entity.Feedback;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperFeedback {

    private final ModelMapper modelMapper;

    public FeedbackResponse toFeedbackResponse(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getBooking().getAppointment().getServiceSchedule().getServiceOffering().getName(),
                feedback.getBooking().getCustomer().getUsername(),
                feedback.getComment(),
                feedback.getRating()
        );
    }
}
