package com.gidbank.dto;

import java.math.BigDecimal;

public class UserCreateRequest {
    private String fullName;
    private String email;
    private String cedula;
    private String celular;
    private String password;
    private BigDecimal initialBalance;
    private String role;

    // --- Agrega estos dos métodos al final de los Getters/Setters ---
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}