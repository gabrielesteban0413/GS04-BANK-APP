package com.bankapp.service;

import com.bankapp.entity.User;
import com.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUserId(UUID.randomUUID().toString());
            admin.setFullName("Administrador Principal");
            admin.setEmail("admin@bank.com");
            admin.setAccountNumber("ES00");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setBalance(10000.0);
            admin.setUserRole("ADMIN");
            admin.setIsActive(true);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);

            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setFullName("Cliente Normal");
            user.setEmail("user@bank.com");
            user.setAccountNumber("ES01");
            user.setPasswordHash(passwordEncoder.encode("user123"));
            user.setBalance(2500.0);
            user.setUserRole("USER");
            user.setIsActive(true);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);

            System.out.println("Usuarios creados: admin@bank.com / admin123 | user@bank.com / user123");
        }
    }
}