package com.bankapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TransferRequest {
    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser mayor a cero")
    @DecimalMin(value = "0.01", message = "Monto mínimo 0.01€")
    @DecimalMax(value = "1000000.00", message = "Monto máximo 1.000.000€")
    private Double amount;

    @NotBlank(message = "La cuenta destino es obligatoria")
    @Pattern(regexp = "^ES[0-9]{2}$", message = "Formato de cuenta: ES seguido de 2 dígitos (ej: ES01)")
    private String targetAccountNumber;

    @Size(max = 255, message = "El concepto no puede exceder 255 caracteres")
    private String concept;
}