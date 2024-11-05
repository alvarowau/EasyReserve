package org.alvarowau.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ApiErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> allErrorAttributes = super.getErrorAttributes(webRequest, options);

        Map<String, Object> errorAttributes = new HashMap<>();
        int statusCode = (int) allErrorAttributes.get("status");
        errorAttributes.put("estado", HttpStatus.valueOf(statusCode));
        errorAttributes.put("fecha", LocalDateTime.now()); // Utiliza LocalDateTime directamente

        String mensaje = getErrorMessage(webRequest);
        errorAttributes.put("mensaje", mensaje);
        errorAttributes.put("ruta", webRequest.getDescription(false));

        errorAttributes.put("errorCode", "some_code"); // Código de error
        errorAttributes.put("details", "some_details"); // Detalles adicionales

        return errorAttributes;
    }

    private String getErrorMessage(WebRequest webRequest) {
        Throwable throwable = getError(webRequest);
        if (throwable instanceof ResponseStatusException) {
            return ((ResponseStatusException) throwable).getReason() != null
                    ? ((ResponseStatusException) throwable).getReason()
                    : "Error desconocido";
        } else if (throwable.getCause() != null) {
            return throwable.getCause().getMessage() != null
                    ? throwable.getCause().getMessage()
                    : throwable.getCause().toString();
        }
        return throwable.toString();
    }
}
