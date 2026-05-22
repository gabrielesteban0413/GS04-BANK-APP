package com.bankapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "BANK_USERS")
@Data
public class User {
    @Id
    @Column(name = "USER_ID", length = 36)
    private String userId;
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false)
    private String accountNumber;
    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;
    private Double balance;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "USER_ROLE", nullable = false)
    private String userRole;
}
