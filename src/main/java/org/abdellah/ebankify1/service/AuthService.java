package org.abdellah.ebankify1.service;

import lombok.RequiredArgsConstructor;
import org.abdellah.ebankify1.dto.LoginRequest;
import org.abdellah.ebankify1.dto.LoginResponse;
import org.abdellah.ebankify1.dto.UserDTO;
import org.abdellah.ebankify1.mapper.UserMapper;
import org.abdellah.ebankify1.model.UserRole;
import org.abdellah.ebankify1.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

//    @Transactional(readOnly = true)
    public LoginResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .email(userDetails.getUsername())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();
    }

//    @Transactional(readOnly = true)
    public LoginResponse authenticateAdmin(String basicAuth) {
        String base64Credentials = basicAuth.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));

        final String[] values = credentials.split(":", 2);
        String email = values[0];
        String password = values[1];

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new org.springframework.security.access.AccessDeniedException("User is not an admin");
        }

        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .email(userDetails.getUsername())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();
    }
    public UserDTO registerAdmin(UserDTO userDTO) {
        userDTO.setRole(UserRole.ADMIN);

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userService.createUser(userDTO);
    }

}