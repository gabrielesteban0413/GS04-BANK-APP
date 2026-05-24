package com.gidbank.dto;

public class LoginResponse {
    private String message;
    private String token;
    private String userId;
    private String fullName; // Cambiado de 'name' a 'fullName'
    private String role;
    private Double balance;  // Agregado

    public LoginResponse() {}

    // Getters y Setters limpios
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}