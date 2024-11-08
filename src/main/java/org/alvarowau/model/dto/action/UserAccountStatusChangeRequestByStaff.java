package org.alvarowau.model.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para cambiar el estado de una cuenta de usuario por parte de un miembro del personal, incluyendo el motivo de eliminación.")
public record UserAccountStatusChangeRequestByStaff(

        @Schema(description = "Nombre de usuario de la cuenta que se desea eliminar.", example = "john_doe")
        String usernameToDelete,

        @Schema(description = "Razón para eliminar la cuenta del usuario.", example = "El usuario ha solicitado la eliminación de su cuenta.")
        String deletionReason
) {
}