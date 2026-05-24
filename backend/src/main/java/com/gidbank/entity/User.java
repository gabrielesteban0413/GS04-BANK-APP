package com.gidbank.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CEDULA", nullable = false)
    private String cedula;

    // Eliminamos el @JsonProperty para que coincida
    // con el nombre de la variable "nombre"
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(nullable = false, length = 15)
    private String celular;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String role = "USER";

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false, length = 1)
    private String activo = "A";

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}