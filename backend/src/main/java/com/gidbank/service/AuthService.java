package com.gidbank.service;

import com.gidbank.dto.LoginRequest;
import com.gidbank.dto.LoginResponse;
import com.gidbank.dto.UserCreateRequest;
import com.gidbank.dto.UserResponse;
import com.gidbank.entity.User;
import com.gidbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. LÓGICA DE LOGIN COMPLETA
    public LoginResponse login(LoginRequest request) {
        // Buscar al usuario por correo
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas (Correo no encontrado)"));

        // Validar que la contraseña ingresada coincida con el hash de Oracle
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas (Contraseña inválida)");
        }

        // Si todo está bien, responder con los datos básicos para Android
        return new LoginResponse(
                String.valueOf(user.getId()),
                user.getNombre() + " " + user.getApellido(),
                user.getRole()
        );
    }

    // 2. LÓGICA DE REGISTRO COMPLETA (Cumpliendo reglas obligatorias)
    public UserResponse register(UserCreateRequest request) {
        // Validaciones contra duplicados
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya se encuentra registrado");
        }
        if (userRepository.findByCedula(request.getCedula()).isPresent()) {
            throw new RuntimeException("La cédula ya se encuentra registrada");
        }

        // Crear la nueva entidad User
        User user = new User();

        // Mapear nombre y apellido desde el nombre completo
        String[] parts = request.getFullName().split(" ", 2);
        user.setNombre(parts[0]);
        user.setApellido(parts.length > 1 ? parts[1] : "");

        user.setEmail(request.getEmail());
        user.setCedula(request.getCedula());
        user.setCelular(request.getCelular()); // <-- Campo obligatorio de celular
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encriptar clave

        // Todo cliente nuevo inicia con saldo en cero si no se define uno inicial
        user.setBalance(request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO);
        user.setRole("USER");
        user.setActivo("A"); // Estado "A" de Activo

        // Guardar en Oracle y retornar la respuesta estructurada
        User savedUser = userRepository.save(user);
        return toResponse(savedUser);
    }

    // 3. OBTENER ROL DEL USUARIO (Corregido)
    public String getUserRole(String userId) {
        return userRepository.findById(Long.parseLong(userId))
                .map(user -> user.getRole()) // <-- Corregido aquí
                .orElse("UNKNOWN");
    }

    // Método auxiliar para transformar la entidad en respuesta limpia
    private UserResponse toResponse(User user) {
        UserResponse resp = new UserResponse();
        resp.setUserId(String.valueOf(user.getId()));
        resp.setFullName(user.getNombre() + " " + user.getApellido());
        resp.setEmail(user.getEmail());
        resp.setAccountNumber(user.getCedula());
        resp.setBalance(user.getBalance());
        resp.setRole(user.getRole());
        resp.setActive(user.getActivo());
        return resp;
    }
}