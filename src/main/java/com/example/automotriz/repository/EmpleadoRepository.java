package com.example.automotriz.repository;

import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<ClienteEntity, Integer> {
    Optional<EmpleadoEntity> findByUsuario(String usuario);
}