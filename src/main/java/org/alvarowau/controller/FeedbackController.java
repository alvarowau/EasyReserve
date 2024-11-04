package org.alvarowau.controller;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.feedback.FeedbackRequest;
import org.alvarowau.model.dto.feedback.FeedbackResponse;
import org.alvarowau.service.AppointmentFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final AppointmentFacade appointmentFacade;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        return ResponseEntity.ok(appointmentFacade.submitFeedback(feedbackRequest));
    }
}