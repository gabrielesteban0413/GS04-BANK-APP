package com.bankapp.service;

import com.bankapp.dto.UserCreateRequest;
import com.bankapp.dto.UserResponse;
import com.bankapp.dto.UserUpdateRequest;
import com.bankapp.entity.User;
import com.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("El email ya existe");
        if (userRepository.findByAccountNumber(request.getAccountNumber()).isPresent())
            throw new RuntimeException("El número de cuenta ya existe");

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setAccountNumber(request.getAccountNumber());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setBalance(request.getInitialBalance());
        user.setUserRole(request.getRole());
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        return toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(String userId) {
        return toResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getBalance() != null) user.setBalance(request.getBalance());
        if (request.getRole() != null) user.setUserRole(request.getRole());
        if (request.getIsActive() != null) user.setIsActive(request.getIsActive());
        return toResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(user);
    }

    private UserResponse toResponse(User user) {
        UserResponse resp = new UserResponse();
        resp.setUserId(user.getUserId());
        resp.setFullName(user.getFullName());
        resp.setEmail(user.getEmail());
        resp.setAccountNumber(user.getAccountNumber());
        resp.setBalance(user.getBalance());
        resp.setRole(user.getUserRole());
        resp.setIsActive(user.getIsActive());
        return resp;
    }
}