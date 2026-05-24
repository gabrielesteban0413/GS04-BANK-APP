package com.gidbank.dto;

import com.fasterxml.jackson.annotation.JsonProperty; // <--- AGREGA ESTA LÍNEA
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserCreateRequest {

    @JsonProperty("nombre")
    private String fullName;

    private String email;
    private String cedula;
    private String celular;
    private String password;
    private BigDecimal initialBalance;
    private String role;
}