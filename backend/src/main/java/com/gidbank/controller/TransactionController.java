package com.gidbank.controller;

import com.gidbank.dto.TransferRequest;
import com.gidbank.entity.Transaction;
import com.gidbank.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(
            @Valid @RequestBody TransferRequest request,
            @RequestHeader("X-User-Id") String userId) {

        // Ejecuta la transferencia
        Transaction tx = transactionService.transfer(request, userId);

        // Construye la respuesta de éxito
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transferencia exitosa");
        // Convertimos el ID numérico de Oracle a String para la respuesta JSON
        response.put("transactionId", String.valueOf(tx.getId()));

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