package org.alvarowau.model.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Promedio de calificación de un proveedor. Incluye el nombre de usuario del proveedor y su calificación promedio.")
public record ProviderAverageRating(

        @Schema(description = "Nombre de usuario del proveedor cuya calificación promedio se está mostrando.", example = "providerJohnDoe")
        String providerUsername,

        @Schema(description = "Calificación promedio del proveedor, representada como un valor decimal.", example = "4.75")
        BigDecimal averageRating
) {}
