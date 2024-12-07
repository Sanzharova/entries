package com.example.controller;

import com.example.dto.RegistrationRequest;
import com.example.service.AuthenticationService;
import com.example.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, PasswordResetService passwordResetService) {
        this.authenticationService = authenticationService;
        this.passwordResetService = passwordResetService;
    }

    @Override
    public ResponseEntity<String> register(RegistrationRequest registerRequest) {
        return ResponseEntity.status(201).body(authenticationService.registrationUser(registerRequest));
    }

    @Override
    public ResponseEntity<String> sendResetToken(String email) {
        try {
            passwordResetService.sendResetToken(email);
            return ResponseEntity.ok("Password reset link sent to your email.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> validateResetToken(String token) {
        if (passwordResetService.validateResetToken(token).isPresent()) {
            return ResponseEntity.ok("Token is valid. You can proceed with resetting your password.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }
}