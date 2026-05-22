package com.gidbank.config;

import com.gidbank.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("X-User-Id");

        // Si no viene el ID de usuario en la cabecera, dejamos pasar o manejamos según tu lógica
        if (userId == null || userId.isEmpty()) {
            return true;
        }

        // Buscamos el rol del usuario para validar
        return userRepository.findById(Long.parseLong(userId))
                .map(user -> {
                    // Guarda el rol en los atributos de la petición por si los controladores lo necesitan
                    request.setAttribute("userRole", user.getRole());
                    return true;
                })
                .orElse(true); // Si no existe, puedes cambiarlo a false o manejar un error 401
    }
}