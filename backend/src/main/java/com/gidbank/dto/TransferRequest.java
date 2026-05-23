package com.gidbank.dto;

import java.math.BigDecimal;

public class TransferRequest {
    private String targetAccountNumber; // En nuestro caso, recibirá la cédula del destino
    private BigDecimal amount;
    private String concept;

    // --- Getters y Setters Manuales ---
    public String getTargetAccountNumber() { return targetAccountNumber; }
    public void setTargetAccountNumber(String targetAccountNumber) { this.targetAccountNumber = targetAccountNumber; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getConcept() { return concept; }
    public void setConcept(String concept) { this.concept = concept; }
}