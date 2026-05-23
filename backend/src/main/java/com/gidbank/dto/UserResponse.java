package com.gidbank.dto;

import java.math.BigDecimal;

public class UserResponse {
    private String userId;
    private String fullName;
    private String email;
    private String accountNumber;
    private BigDecimal balance;
    private String role;
    private String active;

    // --- Getters y Setters ---
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getActive() { return active; }
    public void setActive(String active) { this.active = active; }
}