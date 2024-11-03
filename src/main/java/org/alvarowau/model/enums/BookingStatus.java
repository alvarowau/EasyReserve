package org.alvarowau.model.enums;

public enum BookingStatus {
    PENDING("Pendiente"),
    CONFIRMED("Confirmado"),
    CANCELED("Cancelado"),
    COMPLETED("Completado");

    private final String description;

    BookingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
