package org.alvarowau.model.dto.serviceoffering;

public record ServiceOfferingResponse(
        String name,
        int duration,
        String providerUsername) {
}
