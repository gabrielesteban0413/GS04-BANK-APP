package com.bankapp.service;

import com.bankapp.dto.LoginRequest;
import com.bankapp.dto.LoginResponse;
import com.bankapp.entity.User;
import com.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new RuntimeException("Usuario inactivo");
        }

        return new LoginResponse(
                user.getUserId(),
                user.getFullName(),
                user.getAccountNumber(),
                user.getBalance(),
                user.getUserRole()
        );
    }

    public String getUserRole(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return user.getUserRole();
    }
}