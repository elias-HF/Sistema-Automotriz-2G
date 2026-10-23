package com.example.automotriz.repository;

import com.example.automotriz.entity.OrdenServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdenRepository extends JpaRepository<OrdenServicioEntity, Integer> {
    Optional<OrdenServicioEntity> findByCod(String cod);
    boolean existsByCod(String cod);
}

