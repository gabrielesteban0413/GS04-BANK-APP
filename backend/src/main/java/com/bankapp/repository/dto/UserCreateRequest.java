package com.bankapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotBlank private String fullName;
    @Email @NotBlank private String email;
    @NotBlank private String accountNumber;
    @NotBlank private String password;
    @NotNull private Double initialBalance;
    @NotBlank private String role; // "ADMIN" o "USER"
}