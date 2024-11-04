package org.alvarowau.model.enums;

public enum ActionType {
    CREATE("Crear"),
    UPDATE("Actualizar"),
    DELETE("Borrar"),
    DEACTIVATE("Desactivar"),
    REACTIVATE("Reactivar"),
    LOGIN("Iniciar Sesión"),
    LOGOUT("Cerrar Sesión"),
    PASSWORD_CHANGE("Cambio de Contraseña"),
    ROLE_CHANGE("Cambio de Rol"),
    ACCOUNT_LOCK("Bloquear Cuenta"),
    ACCOUNT_UNLOCK("Desbloquear Cuenta"),
    CANCEL_BOOKING("Cancelar Reserva");;

    private final String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}