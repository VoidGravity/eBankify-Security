package org.abdellah.ebankify1.controller;

import lombok.RequiredArgsConstructor;
import org.abdellah.ebankify1.dto.LoginRequest;
import org.abdellah.ebankify1.security.JwtService;
import org.abdellah.ebankify1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestHeader("Authorization") String basicAuth) {
        return ResponseEntity.ok().build();
    }


}
