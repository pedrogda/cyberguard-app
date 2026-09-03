package com.pedroaugusto.cyberguard_app.controllers;

import com.pedroaugusto.cyberguard_app.model.User;
import com.pedroaugusto.cyberguard_app.services.AuthService;
import dto.LoginRequest;
import dto.LoginResponse;
import dto.RegisterRequest;
import dto.RegisterResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;

    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        RegisterResponse response =
                new RegisterResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }
}
