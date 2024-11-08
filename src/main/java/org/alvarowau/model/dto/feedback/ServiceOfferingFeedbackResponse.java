package org.alvarowau.model.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;
import org.alvarowau.model.enums.FeedbackRating;

@Schema(description = "Respuesta con los comentarios y calificación de un cliente para una oferta de servicio.")
public record ServiceOfferingFeedbackResponse(

        @Schema(description = "Nombre de la oferta de servicio sobre la que se está proporcionando feedback.", example = "Premium Service Package")
        String serviceOfferingName,

        @Schema(description = "Nombre de usuario del cliente que deja el feedback.", example = "customerJohnDoe")
        String customerUsername,

        @Schema(description = "Comentario proporcionado por el cliente sobre la oferta de servicio.", example = "Great service, would recommend!")
        String feedback,

        @Schema(description = "Calificación dada por el cliente utilizando una escala predefinida.", example = "4")
        FeedbackRating rating
) {
}
