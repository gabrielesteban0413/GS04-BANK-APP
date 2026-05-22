package com.gidbank.dto;

public class LoginResponse {
    private String userId;
    private String name;
    private String role;

    // CONSTRUCTOR VACÍO (Obligatorio para Spring)
    public LoginResponse() {}

    // CONSTRUCTOR CON PARÁMETROS (El que está chillando)
    public LoginResponse(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    // --- Getters y Setters ---
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}