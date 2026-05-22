package com.bankapp.config;

import com.bankapp.repository.UserRepository;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader("X-User-Id");
        if (userId == null) {
            response.setStatus(401);
            return false;
        }
        var user = userRepository.findById(userId);
        if (user.isEmpty() || !"ADMIN".equals(user.get().getUserRole())) {
            response.setStatus(403);
            return false;
        }
        return true;
    }
}