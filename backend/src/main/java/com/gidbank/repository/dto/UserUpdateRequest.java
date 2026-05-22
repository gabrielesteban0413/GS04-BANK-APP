package com.bankapp.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private Double balance;
    private String role;
    private Boolean isActive;
}