package com.example.service;

import com.example.dto.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;


@Service
@Slf4j
public class AuthenticationService {

    private final com.example.repository.UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthenticationService(com.example.repository.UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String registrationUser(RegistrationRequest registerRequest) {

        com.example.entity.UserEntity user = new com.example.entity.UserEntity();

        user.setUsername(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserRole(com.example.entity.UserRole.USER);
        user.setCreatedAt(Timestamp.from(Instant.now()).toLocalDateTime());
        user.setIsAccountExpired(Boolean.TRUE);
        user.setIsActive(Boolean.TRUE);
        user.setIsAccountLocked(Boolean.TRUE);
        user.setIsEnabled(Boolean.TRUE);

        userRepository.save(user);
        return "User registered successfully";
    }
}