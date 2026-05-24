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
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            LoginResponse response = new LoginResponse();
            response.setMessage("Bienvenida");
            response.setToken("Token-de-ejemplo");

            // 1. Usamos String.valueOf para el ID (Long a String)
            response.setUserId(String.valueOf(user.getId()));

            // 2. Usamos getNombre() porque en tu entidad se llama "nombre"
            response.setFullName(user.getNombre());

            response.setRole(user.getRole());

            // 3. Convertimos el BigDecimal de la base de datos a Double
            if (user.getBalance() != null) {
                response.setBalance(user.getBalance().doubleValue());
            } else {
                response.setBalance(0.0);
            }

            return response;
        } else {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }


    public String getUserRole(String userId) {
        return "USER";
    }
}