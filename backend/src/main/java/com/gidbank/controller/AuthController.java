package com.gidbank.controller;

import com.gidbank.dto.LoginRequest;
import com.gidbank.dto.LoginResponse;
import com.gidbank.dto.UserCreateRequest;
import com.gidbank.dto.UserResponse;
import com.gidbank.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreateRequest request) {
        request.setRole("USER");
        // ESTA LÍNEA LLAMA AL SERVICIO
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("X-User-Id") String userId) {
        String role = authService.getUserRole(userId);
        return ResponseEntity.ok(Map.of("role", role));
    }
}