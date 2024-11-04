package org.alvarowau.model.enums;

public enum FeedbackRating {
    POOR(1, "Pobre"),
    AVERAGE(2, "Promedio"),
    GOOD(3, "Bueno"),
    VERY_GOOD(4, "Muy Bueno"),
    EXCELLENT(5, "Excelente");

    private final int value;
    private final String description;

    FeedbackRating(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    // Método para obtener el enum a partir de un número
    public static FeedbackRating fromValue(int value) {
        for (FeedbackRating rating : FeedbackRating.values()) {
            if (rating.getValue() == value) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Valor no válido: " + value);
    }
}
