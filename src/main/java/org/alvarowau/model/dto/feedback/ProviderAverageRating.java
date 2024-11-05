package org.alvarowau.model.dto.feedback;

import java.math.BigDecimal;

public record ProviderAverageRating(String providerUsername, BigDecimal averageRating) {
}