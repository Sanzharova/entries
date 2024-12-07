package com.example.controller;

import com.example.dto.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthenticationApi {

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody RegistrationRequest registerRequest);
}