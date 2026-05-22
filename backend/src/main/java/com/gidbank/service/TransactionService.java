package com.gidbank.service;

import com.gidbank.dto.TransferRequest;
import com.gidbank.entity.Transaction;
import com.gidbank.entity.User;
import com.gidbank.repository.TransactionRepository;
import com.gidbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction transfer(TransferRequest request, String fromUserId) {
        // 1. Buscar usuario origen por ID (Long)
        User fromUser = userRepository.findById(Long.parseLong(fromUserId))
                .orElseThrow(() -> new RuntimeException("Usuario origen no existe"));

        // 2. Buscar usuario destino por Cédula (que mapeamos en el repositorio)
        User toUser = userRepository.findByCedula(request.getTargetAccountNumber())
                .orElseThrow(() -> new RuntimeException("Cuenta destino no existe"));

        // 3. Validar que no se transfiera a sí mismo
        if (fromUser.getId().equals(toUser.getId())) {
            throw new RuntimeException("No puedes transferirte a ti mismo");
        }

        // 4. Validar saldo suficiente (compareTo devuelve -1 si el saldo es menor al monto)
        if (fromUser.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Saldo insuficiente. Saldo actual: " + fromUser.getBalance());
        }

        // 5. Operaciones financieras usando BigDecimal
        fromUser.setBalance(fromUser.getBalance().subtract(request.getAmount()));
        toUser.setBalance(toUser.getBalance().add(request.getAmount()));

        // 6. Guardar saldos actualizados
        userRepository.save(fromUser);
        userRepository.save(toUser);

        // 7. Crear el registro de la transacción
        Transaction tx = new Transaction();
        tx.setFromUser(fromUser);
        tx.setToUser(toUser);
        tx.setAmount(request.getAmount());
        tx.setDescription(request.getConcept()); // Usamos 'description' que mapeamos en la entidad

        return transactionRepository.save(tx);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getUserTransactions(String userId) {
        Long id = Long.parseLong(userId);
        // Buscamos las transacciones donde el usuario sea el origen O el destino
        return transactionRepository.findByFromUserIdOrToUserIdOrderByCreatedAtDesc(id, id);
    }
}