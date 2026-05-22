package com.bankapp.service;

import com.bankapp.dto.TransferRequest;
import com.bankapp.entity.Transaction;
import com.bankapp.entity.User;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction transfer(TransferRequest request, String fromUserId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("Usuario origen no existe"));
        User toUser = userRepository.findByAccountNumber(request.getTargetAccountNumber())
                .orElseThrow(() -> new RuntimeException("Cuenta destino no existe"));

        if (fromUser.getUserId().equals(toUser.getUserId())) {
            throw new RuntimeException("No puedes transferirte a ti mismo");
        }
        if (fromUser.getBalance() < request.getAmount()) {
            throw new RuntimeException("Saldo insuficiente. Saldo actual: " + fromUser.getBalance());
        }

        fromUser.setBalance(fromUser.getBalance() - request.getAmount());
        toUser.setBalance(toUser.getBalance() + request.getAmount());
        userRepository.save(fromUser);
        userRepository.save(toUser);

        Transaction tx = new Transaction();
        tx.setTransactionId(UUID.randomUUID().toString());
        tx.setFromUser(fromUser);
        tx.setToUser(toUser);
        tx.setAmount(request.getAmount());
        tx.setConcept(request.getConcept());
        tx.setStatus("COMPLETED");
        tx.setCreatedAt(LocalDateTime.now());
        return transactionRepository.save(tx);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getUserTransactions(String userId) {
        return transactionRepository.findByFromUser_UserIdOrderByCreatedAtDesc(userId);
    }
}