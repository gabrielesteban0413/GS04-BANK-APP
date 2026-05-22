package com.bankapp.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String userId;
    private String fullName;
    private String email;
    private String accountNumber;
    private Double balance;
    private String role;
    private Boolean isActive;
}