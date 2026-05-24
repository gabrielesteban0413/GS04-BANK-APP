package com.gidbank.service;

import com.gidbank.dto.*;
import com.gidbank.repository.UserRepository;
import com.gidbank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Necesario para encriptar
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // <--- ESTA LÍNEA TE FALTABA

    public User register(UserCreateRequest request) {
        // --- DEPURACIÓN: Verifica qué trae el DTO ---
        System.out.println("DEBUG: Registro recibido: " + request);
        if (request.getFullName() == null) {
            System.out.println("ERROR CRÍTICO: El nombre (fullName) recibido es NULL");
        }

        User user = new User();
        user.setCedula(request.getCedula());
        user.setNombre(request.getFullName());
        user.setCelular(request.getCelular());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        user.setActivo("A");

        // --- DEPURACIÓN: Verifica el objeto antes de persistir ---
        System.out.println("DEBUG: Objeto User a guardar: " + user);

        return userRepository.save(user);
    }
    public LoginResponse login(LoginRequest request) {
        // Implementar lógica de login cuando la necesites
        return new LoginResponse();
    }

    public String getUserRole(String userId) {
        return "USER";
    }
}