package com.gidbank.controller;

import com.gidbank.dto.*;
import com.gidbank.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateRequest request) {
        // El servicio recibe el DTO y devuelve la respuesta
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("X-User-Id") String userId) {
        String role = authService.getUserRole(userId);
        return ResponseEntity.ok(Map.of("role", role));
    }
}