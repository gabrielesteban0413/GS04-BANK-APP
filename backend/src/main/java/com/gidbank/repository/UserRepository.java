package com.gidbank.repository;

import com.gidbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importación necesaria
import org.springframework.stereotype.Repository;
import java.util.List; // Importación necesaria
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT column_name FROM user_tab_columns WHERE table_name = 'USERS'", nativeQuery = true)
    List<String> findColumns();

    Optional<User> findByCedula(String cedula);
    Optional<User> findByEmail(String email);
    boolean existsByCedula(String cedula);
    boolean existsByEmail(String email);
}