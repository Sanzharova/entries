package com.example.controller;

import com.example.dto.RegistrationRequest;
import com.example.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<String> register(RegistrationRequest registerRequest) {
        return ResponseEntity.status(201).body(authenticationService.registrationUser(registerRequest));
    }
}