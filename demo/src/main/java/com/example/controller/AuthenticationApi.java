package com.example.controller;

import com.example.dto.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
public interface AuthenticationApi {

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody RegistrationRequest registerRequest);

    @PostMapping("/forgot-password")
    ResponseEntity<String> sendResetToken(@RequestParam String email);

    @GetMapping("/reset-password")
    ResponseEntity<String> validateResetToken(@RequestParam String token);
}