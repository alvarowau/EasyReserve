package org.alvarowau.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class TestAuthController {

    // Método accesible solo para ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin-data")
    public String getAdminData() {
        return "Data visible solo para ADMIN";
    }

    // Método accesible solo para CUSTOMER
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer-data")
    public String getCustomerData() {
        return "Data visible solo para CUSTOMER";
    }

    // Método accesible para ADMIN y CUSTOMER
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/common-data")
    public String getCommonData() {
        return "Data visible para ADMIN y CUSTOMER";
    }

    // Método accesible para todos los roles
    @GetMapping("/public-data")
    public String getPublicData() {
        return "Data visible para todos los usuarios autenticados";
    }


}
