package com.gidbank.dto;

import java.math.BigDecimal;

public class UserUpdateRequest {
    private String fullName;
    private String email;
    private BigDecimal balance;
    private String role;
    private String active; // "A" o "I"

    // --- Getters y Setters ---
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getActive() { return active; }
    public void setActive(String active) { this.active = active; }
}