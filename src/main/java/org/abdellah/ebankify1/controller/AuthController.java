package org.abdellah.ebankify1.controller;

import lombok.RequiredArgsConstructor;
import org.abdellah.ebankify1.dto.LoginRequest;
import org.abdellah.ebankify1.dto.LoginResponse;
import org.abdellah.ebankify1.dto.UserDTO;
import org.abdellah.ebankify1.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/auth/login")
//    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
//    }
//
//    @PostMapping("/admin/login")
//    public ResponseEntity<LoginResponse> adminLogin(@RequestHeader("Authorization") String basicAuth) {
//        return ResponseEntity.ok(authService.authenticateAdmin(basicAuth));
//    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserDTO> registerAdmin(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.registerAdmin(userDTO));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestHeader("Authorization") String basicAuth) {
        return ResponseEntity.ok(authService.authenticateAdmin(basicAuth));
    }

}