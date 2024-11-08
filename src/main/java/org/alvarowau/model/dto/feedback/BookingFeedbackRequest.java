package org.alvarowau.model.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de retroalimentación para una reserva. Incluye el número de reserva, el comentario y la calificación.")
public record BookingFeedbackRequest(

        @Schema(description = "Número único de la reserva para la que se proporciona la retroalimentación.", example = "B123456")
        String bookingNumber,

        @Schema(description = "Comentario o retroalimentación proporcionada por el cliente sobre la reserva.", example = "Excelente servicio, todo a tiempo.")
        String feedback,

        @Schema(description = "Calificación de la reserva, con valores entre 1 y 5.", example = "5")
        int rating
) {
}
