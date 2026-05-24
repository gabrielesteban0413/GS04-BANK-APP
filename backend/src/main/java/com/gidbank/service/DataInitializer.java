package com.gidbank.service;

import com.gidbank.entity.User;
import com.gidbank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    // Cambiamos a final para asegurar la inyección
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor para inyección obligatoria
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Ahora userRepository no será null
        if (userRepository.count() == 0) {

            User admin = new User();
            admin.setNombre("Admin");
            admin.setEmail("admin@bank.com");
            admin.setCedula("1000000001");
            admin.setCelular("3001234567");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setBalance(new BigDecimal("100000.00"));
            admin.setRole("ADMIN");
            admin.setActivo("A");
            userRepository.save(admin);

            User user = new User();
            user.setNombre("Cliente Normal");
            user.setEmail("user@bank.com");
            user.setCedula("1000000002");
            user.setCelular("3107654321");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setBalance(new BigDecimal("2500.00"));
            user.setRole("USER");
            user.setActivo("A");
            userRepository.save(user);

            System.out.println(">>> [DataInitializer] Usuarios semilla creados con éxito.");
        }
    }
}