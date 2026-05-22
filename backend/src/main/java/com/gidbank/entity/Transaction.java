package com.bankapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "BANK_TRANSACTIONS")
@Data
public class Transaction {
    @Id
    @Column(name = "TRANSACTION_ID", length = 36)
    private String transactionId;
    @ManyToOne
    @JoinColumn(name = "FROM_USER_ID", nullable = false)
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "TO_USER_ID", nullable = false)
    private User toUser;
    private Double amount;
    private String concept;
    private String status;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
