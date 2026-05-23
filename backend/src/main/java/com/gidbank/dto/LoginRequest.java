package com.gidbank.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Métodos manuales para quitar el error de inmediato
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}