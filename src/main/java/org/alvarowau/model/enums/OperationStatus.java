package org.alvarowau.model.enums;

public enum OperationStatus {
    SUCCESS("Éxito"),
    FAILURE("Sin Éxito");

    private final String description;

    OperationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
