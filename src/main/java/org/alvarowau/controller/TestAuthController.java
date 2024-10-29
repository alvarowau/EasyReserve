package org.alvarowau.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that provides endpoints for user data based on roles.
 */
@RestController
@RequestMapping("/user")
public class TestAuthController {

    /**
     * Endpoint accessible only to ADMIN role.
     *
     * @return A string message indicating access for ADMIN.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin-data")
    public String getAdminData() {
        return "Data visible solo para ADMIN";
    }

    /**
     * Endpoint accessible only to CUSTOMER role.
     *
     * @return A string message indicating access for CUSTOMER.
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer-data")
    public String getCustomerData() {
        return "Data visible solo para CUSTOMER";
    }

    /**
     * Endpoint accessible to both ADMIN and CUSTOMER roles.
     *
     * @return A string message indicating access for ADMIN and CUSTOMER.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/common-data")
    public String getCommonData() {
        return "Data visible para ADMIN y CUSTOMER";
    }

    /**
     * Public endpoint accessible to all authenticated users.
     *
     * @return A string message indicating public access.
     */
    @GetMapping("/public-data")
    public String getPublicData() {
        return "Data visible para todos los usuarios autenticados";
    }
}
