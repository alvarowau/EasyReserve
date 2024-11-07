package org.alvarowau.populate;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.feedback.BookingFeedbackRequest;
import org.alvarowau.model.entity.Booking;
import org.alvarowau.service.BookingManagementService;
import org.alvarowau.service.FeedbackManagementService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FeedbackPopulator {

    private final BookingManagementService bookingManagementService;
    private final FeedbackManagementService feedbackManagementService;
    private final List<String> listFeedbacks = listFeedbacks();
    private final Random random = new Random();

    public void createFeedbacks() {
        List<Booking> bookings = bookingManagementService.findAll();
        List<BookingFeedbackRequest> requestList = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingFeedbackRequest request = new BookingFeedbackRequest(
                    booking.getBookingNumber(),
                    getRandomCancellationReason(),
                    random.nextInt(5) + 1
            );
            requestList.add(request);
        }

        for (BookingFeedbackRequest request : requestList) {
            feedbackManagementService.submitFeedbackByCustomer(request);
        }

    }

    private String getRandomCancellationReason() {
        return listFeedbacks.get(random.nextInt(listFeedbacks.size()));
    }

    private List<String> listFeedbacks() {
        return List.of(
                "Excelente servicio, quedé muy satisfecho.",
                "La atención fue buena, pero hubo un pequeño retraso.",
                "Muy profesional y amable, gracias.",
                "El servicio no cumplió mis expectativas.",
                "Todo perfecto, muy recomendado.",
                "El proceso fue rápido y sencillo.",
                "Hubo algunos problemas con la comunicación.",
                "Me sentí muy bien atendido durante todo el proceso.",
                "El personal fue muy atento y profesional.",
                "Esperaba más puntualidad en el servicio.",
                "Muy buena relación calidad-precio.",
                "El lugar estaba limpio y bien organizado.",
                "Tuve una experiencia agradable y sin problemas.",
                "Hubo algo de confusión al inicio, pero se resolvió.",
                "No estoy del todo satisfecho con el servicio.",
                "Volveré sin duda alguna, gracias por todo.",
                "El tiempo de espera fue mayor al esperado.",
                "No recibí el trato que esperaba.",
                "Muy contento con el resultado final.",
                "La calidad del servicio superó mis expectativas."
        );

    }
}