package com.example.service;

import com.example.entity.PasswordResetTokenEntity;
import com.example.entity.UserEntity;
import com.example.repository.PasswordResetTokenRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository,
                                UserRepository userRepository,
                                EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void sendResetToken(String email) {
        Optional<UserEntity> user = userRepository.findByUsername(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        String token = UUID.randomUUID().toString();
        PasswordResetTokenEntity resetToken = new PasswordResetTokenEntity();
        resetToken.setToken(token);
        resetToken.setUser(user.get());
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        emailService.sendEmail(email, "Password Reset", "Click the link to reset your password: " + resetLink);
    }

    public Optional<UserEntity> validateResetToken(String token) {
        Optional<PasswordResetTokenEntity> resetToken = tokenRepository.findByToken(token);
        if (resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            return Optional.of(resetToken.get().getUser());
        }
        return Optional.empty();
    }

    public void resetPassword(String token, String newPassword) {
        Optional<UserEntity> userOptional = validateResetToken(token);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            tokenRepository.deleteByToken(token);
        } else {
            throw new IllegalArgumentException("Invalid or expired token");
        }
    }
}
