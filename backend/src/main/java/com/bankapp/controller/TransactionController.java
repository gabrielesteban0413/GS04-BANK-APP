package com.bankapp.controller;

import com.bankapp.dto.TransferRequest;
import com.bankapp.entity.Transaction;
import com.bankapp.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(
            @Valid @RequestBody TransferRequest request,
            @RequestHeader("X-User-Id") String userId) {
        Transaction tx = transactionService.transfer(request, userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transferencia exitosa");
        response.put("transactionId", tx.getTransactionId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getUserTransactions(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(transactionService.getUserTransactions(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestHeader("X-User-Id") String userId) {
        // En producción validar que userId sea ADMIN
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}