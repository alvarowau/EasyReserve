package org.alvarowau.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador que proporciona endpoints para acceder a datos de usuario según roles.
 */
@RestController
@RequestMapping("/test")
@Tag(name = "Autorización de acceso", description = "Controlador para probar el acceso a datos según los roles de usuario")
public class TestAuthController {

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/staff-data")
    @Operation(
            summary = "Obtener datos del personal",
            description = "Permite acceder a datos visibles solo para usuarios con rol STAFF",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Acceso exitoso a los datos del personal")
            }
    )
    public String getStaffData() {
        return "Data visible solo para ADMIN";
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/provider-data")
    @Operation(
            summary = "Obtener datos del proveedor",
            description = "Permite acceder a datos visibles solo para usuarios con rol PROVIDER",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Acceso exitoso a los datos del proveedor")
            }
    )
    public String getProviderData() {
        return "Data visible solo para provider";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer-data")
    @Operation(
            summary = "Obtener datos del cliente",
            description = "Permite acceder a datos visibles solo para usuarios con rol CUSTOMER",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Acceso exitoso a los datos del cliente")
            }
    )
    public String getCustomerData() {
        return "Data visible solo para CUSTOMER";
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER')")
    @GetMapping("/common-data")
    @Operation(
            summary = "Obtener datos comunes para STAFF y CUSTOMER",
            description = "Permite acceder a datos visibles para usuarios con rol STAFF o CUSTOMER",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Acceso exitoso a los datos comunes")
            }
    )
    public String getStaffAndCustomerData() {
        return "Data visible para STAFF y CUSTOMER";
    }

    @GetMapping("/public-data")
    @Operation(
            summary = "Obtener datos públicos",
            description = "Permite acceder a datos visibles para todos los usuarios sin restricciones de rol",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Acceso exitoso a los datos públicos")
            }
    )
    public String getPublicData() {
        return "Data visible para todos";
    }
}
