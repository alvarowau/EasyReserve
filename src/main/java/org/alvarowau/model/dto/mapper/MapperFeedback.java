package org.alvarowau.model.dto.mapper;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.feedback.ServiceOfferingFeedbackResponse;
import org.alvarowau.model.entity.Feedback;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperFeedback {

    private final ModelMapper modelMapper;

    public ServiceOfferingFeedbackResponse toFeedbackResponse(Feedback feedback) {
        return new ServiceOfferingFeedbackResponse(
                feedback.getBooking().getAppointment().getServiceSchedule().getServiceOffering().getName(),
                feedback.getBooking().getCustomer().getUsername(),
                feedback.getComment(),
                feedback.getRating()
        );
    }
}
