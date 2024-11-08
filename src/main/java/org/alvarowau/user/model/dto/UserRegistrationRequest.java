package org.alvarowau.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de registro de usuario, que incluye todos los detalles necesarios para crear una cuenta de usuario.")
public record UserRegistrationRequest(

        @NotBlank
        @Schema(description = "El nombre de usuario del nuevo usuario.", example = "john_doe")
        String username,

        @NotBlank
        @Schema(description = "La contraseña del usuario para la cuenta.", example = "Password123!")
        String password,

        @NotBlank
        @Schema(description = "La repetición de la contraseña para confirmar que es correcta.", example = "Password123!")
        String passwordRepeat,

        @NotBlank
        @Schema(description = "El correo electrónico del usuario.", example = "john.doe@example.com")
        String email,

        @NotBlank
        @Schema(description = "El primer nombre del usuario.", example = "John")
        String firstName,

        @NotBlank
        @Schema(description = "El apellido del usuario.", example = "Doe")
        String lastName,

        @NotBlank
        @Schema(description = "El número de documento de identidad del usuario.", example = "12345678A")
        String idDocument,

        @NotBlank
        @Schema(description = "El número de teléfono principal del usuario.", example = "+1234567890")
        String primaryPhone,

        @Schema(description = "El número de teléfono secundario del usuario (opcional).", example = "+0987654321")
        String secondaryPhone, // Puede ser opcional

        @Schema(description = "Correo electrónico adicional del usuario (opcional).", example = "john.alternate@example.com")
        String additionalEmail, // Puede ser opcional

        @NotBlank
        @Schema(description = "La calle de la dirección del usuario.", example = "123 Main St")
        String street,

        @NotBlank
        @Schema(description = "La ciudad de la dirección del usuario.", example = "Springfield")
        String city,

        @NotBlank
        @Schema(description = "El estado o provincia de la dirección del usuario.", example = "Illinois")
        String state,

        @NotBlank
        @Schema(description = "El código postal de la dirección del usuario.", example = "62704")
        String zipCode,

        @NotBlank
        @Schema(description = "El país de la dirección del usuario.", example = "USA")
        String country
) {
}
