package org.alvarowau.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that provides endpoints for user data based on roles.
 */
@RestController
@RequestMapping("/test")
public class TestAuthController {

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/staff-data")
    public String getStaffData() {
        return "Data visible solo para ADMIN";
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/provider-data")
    public String getProviderData() {
        return "Data visible solo para provider";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer-data")
    public String getCustomerData() {
        return "Data visible solo para CUSTOMER";
    }

    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER')")
    @GetMapping("/common-data")
    public String getStaffAndCustomerData() {
        return "Data visible para STAFF y CUSTOMER";
    }

    @GetMapping("/public-data")
    public String getPublicData() {
        return "Data visible para todos";
    }
}
