package com.gidbank.service;

import com.gidbank.dto.*;
import com.gidbank.entity.User;
import com.gidbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("El email ya existe");
        if (userRepository.findByCedula(request.getCedula()).isPresent())
            throw new RuntimeException("La cédula ya existe");

        User user = new User();
        // Ahora guardamos el nombre completo directamente
        user.setNombre(request.getFullName());
        user.setEmail(request.getEmail());
        user.setCedula(request.getCedula());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBalance(request.getInitialBalance());
        user.setRole(request.getRole());
        user.setActivo("A");

        return toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(String userId) {
        return toResponse(userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizamos solo nombre completo
        if (request.getFullName() != null) user.setNombre(request.getFullName());

        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getBalance() != null) user.setBalance(request.getBalance());
        if (request.getRole() != null) user.setRole(request.getRole());
        if (request.getActive() != null) user.setActivo(request.getActive());

        return toResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(user);
    }

    private UserResponse toResponse(User user) {
        UserResponse resp = new UserResponse();
        resp.setUserId(String.valueOf(user.getId()));
        // Ya no necesitamos concatenar nombre + apellido
        resp.setFullName(user.getNombre());
        resp.setEmail(user.getEmail());
        resp.setAccountNumber(user.getCedula());
        resp.setBalance(user.getBalance());
        resp.setRole(user.getRole());
        resp.setActive(user.getActivo());
        return resp;
    }
}