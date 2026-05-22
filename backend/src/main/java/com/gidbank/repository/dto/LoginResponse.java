package com.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String userId;
    private String fullName;
    private String accountNumber;
    private Double balance;
    private String role;  // Añadir rol para que Android lo sepa
}