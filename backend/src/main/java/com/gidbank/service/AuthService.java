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
        // 1. Validaciones de seguridad en Backend (por si acaso el frontend es saltado)
        if (request.getCelular() == null || request.getCelular().length() != 10) {
            throw new RuntimeException("El celular debe tener 10 dígitos");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6 || request.getPassword().length() > 12) {
            throw new RuntimeException("La contraseña debe tener entre 6 y 12 caracteres");
        }

        // 2. Verificar si la cédula ya existe (¡Muy importante!)
        if (userRepository.findByCedula(request.getCedula()).isPresent()) {
            throw new RuntimeException("La cédula ya se encuentra registrada");
        }

        // 3. Crear y guardar el usuario
        User user = new User();
        user.setCedula(request.getCedula());
        user.setNombre(request.getFullName());
        user.setCelular(request.getCelular());
        user.setEmail(request.getEmail());
        // Recuerda siempre encriptar la contraseña
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        user.setActivo("A");

        return userRepository.save(user);
    }
    public LoginResponse login(LoginRequest request) {
        // 1. Validar formato numérico
        if (!request.getEmail().matches("\\d+")) {
            throw new RuntimeException("El identificador debe ser numérico");
        }

        // 2. Buscar por cédula
        User user = userRepository.findByCedula(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. VALIDAR PASSWORD (esto debe ir antes de crear la respuesta por seguridad)
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            // --- AQUÍ CREAS EL OBJETO ---
            LoginResponse response = new LoginResponse();

            // --- AQUÍ LO LLENAS ---
            response.setMessage("Bienvenida");
            response.setToken("Token-de-ejemplo");
            response.setUserId(String.valueOf(user.getId()));
            response.setFullName(user.getNombre());
            response.setRole(user.getRole());
            response.setBalance(user.getBalance() != null ? user.getBalance().doubleValue() : 0.0);

            // --- AQUÍ LO RETORNAS ---
            return response;

        } else {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }

    public String forgotPassword(String identifier) {
        // 1. Buscar usuario
        User user = userRepository.findByCedula(identifier)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Aquí iría la lógica para enviar el correo o generar un token temporal
        // Por ahora, retornamos un mensaje de éxito
        return "Instrucciones enviadas al correo registrado para: " + user.getNombre();
    }

    public String getUserRole(String userId) {
        return "USER";
    }
}