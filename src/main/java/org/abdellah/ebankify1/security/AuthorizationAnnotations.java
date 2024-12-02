package org.abdellah.ebankify1.security;

import org.springframework.security.access.prepost.PreAuthorize;

public class AuthorizationAnnotations {
    @PreAuthorize("hasRole('ADMIN')")
    public @interface AdminOnly {}

    @PreAuthorize("hasRole('USER')")
    public @interface UserOnly {}

    @PreAuthorize("hasRole('EMPLOYEE')")
    public @interface EmployeeOnly {}

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public @interface EmployeeOrAdminOnly {}
}