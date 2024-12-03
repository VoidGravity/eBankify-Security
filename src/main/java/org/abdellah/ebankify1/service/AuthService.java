package org.abdellah.ebankify1.service;

import lombok.RequiredArgsConstructor;
import org.abdellah.ebankify1.dto.LoginRequest;
import org.abdellah.ebankify1.dto.LoginResponse;
import org.abdellah.ebankify1.exception.AuthenticationFailedException;
import org.abdellah.ebankify1.model.User;
import org.abdellah.ebankify1.model.UserRole;
import org.abdellah.ebankify1.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return LoginResponse.builder()
                    .token(token)
                    .email(userDetails.getUsername())
                    .role(userDetails.getAuthorities().iterator().next().getAuthority())
                    .build();
        } catch (Exception e) {
            throw new AuthenticationFailedException("Invalid email or password");
        }
    }

    @Transactional(readOnly = true)
    public LoginResponse authenticateAdmin(String basicAuth) {
        try {
            // Remove "Basic " prefix
            String base64Credentials = basicAuth.substring("Basic ".length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);

            // Verify that it's an admin
            User user = userService.getUserByEmail(values[0]);
            if (user.getRole() != UserRole.ADMIN) {
                throw new AuthenticationFailedException("Not authorized as admin");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(values[0], values[1])
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return LoginResponse.builder()
                    .token(token)
                    .email(userDetails.getUsername())
                    .role("ROLE_ADMIN")
                    .build();
        } catch (Exception e) {
            throw new AuthenticationFailedException("Invalid admin credentials");
        }
    }
}