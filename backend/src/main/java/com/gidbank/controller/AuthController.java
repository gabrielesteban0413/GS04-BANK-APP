package com.gidbank.controller;


import com.gidbank.dto.LoginRequest;
import com.gidbank.dto.UserCreateRequest;
import com.gidbank.entity.User;
import com.gidbank.service.AuthService;

import jakarta.validation.Valid; // Asegúrate de tener la dependencia de validation en tu pom.xml
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Aquí llamas al servicio que tiene la lógica real
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateRequest request) {
        try {
            User user = authService.register(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("X-User-Id") String userId) {
        String role = authService.getUserRole(userId);
        return ResponseEntity.ok(Map.of("role", role));
    }

}