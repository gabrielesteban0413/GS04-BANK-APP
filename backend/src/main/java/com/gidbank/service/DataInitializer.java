package com.gidbank.service;

import com.gidbank.entity.User;
import com.gidbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            // 1. Crear el Administrador
            User admin = new User();
            admin.setNombre("Admin");
            admin.setApellido("Principal");
            admin.setEmail("admin@bank.com");
            admin.setCedula("1000000001");
            admin.setCelular("3001234567"); //
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setBalance(new BigDecimal("100000.00"));
            admin.setRole("ADMIN");
            admin.setActivo("A");
            userRepository.save(admin);

            // 2. Crear un Cliente de prueba
            User user = new User();
            user.setNombre("Cliente");
            user.setApellido("Normal");
            user.setEmail("user@bank.com");
            user.setCedula("1000000002");
            user.setCelular("3107654321"); //
            user.setPassword(passwordEncoder.encode("user123"));
            user.setBalance(new BigDecimal("2500.00"));
            user.setRole("USER");
            user.setActivo("A");
            userRepository.save(user);

            System.out.println(">>> [DataInitializer] Usuarios semilla creados con éxito en Oracle.");
            System.out.println(">>> Admin: admin@bank.com / admin123 | User: user@bank.com / user123");
        }
    }
}