package com.gidbank.service;

import com.gidbank.dto.LoginRequest;
import com.gidbank.dto.LoginResponse;
import com.gidbank.dto.UserCreateRequest;
import com.gidbank.dto.UserResponse;
import com.gidbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // Método que el controlador está buscando
    public UserResponse register(UserCreateRequest request) {
        // AQUÍ VA TU LÓGICA DE REGISTRO
        // Ejemplo rápido:
        // User user = new User(request.getCedula(), ...);
        // userRepository.save(user);
        // return new UserResponse(user);
        return null; // Cambia esto por tu lógica real
    }

    // Método que el controlador está buscando
    public String getUserRole(String userId) {
        // AQUÍ VA TU LÓGICA PARA BUSCAR EL ROL EN ORACLE
        // return userRepository.findById(userId).get().getRole();
        return "USER"; // Placeholder temporal
    }

    public LoginResponse login(LoginRequest request) {
        // Lógica de login existente
        return null;
    }
}