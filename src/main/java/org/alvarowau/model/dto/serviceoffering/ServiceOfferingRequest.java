package org.alvarowau.model.dto.serviceoffering;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ServiceOfferingRequest(
        @NotBlank(message = "Service name cannot be empty")
        String name,

        @Positive(message = "Duration must be a positive number")
        int duration
) {}
