package com.gidbank.repository;

import com.gidbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Consultas automáticas que usaremos para el Login y Registro
    Optional<User> findByCedula(String cedula);
    Optional<User> findByEmail(String email);
    boolean existsByCedula(String cedula);
    boolean existsByEmail(String email);
}