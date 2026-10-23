package com.example.automotriz.repository;

import com.example.automotriz.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<CitaEntity, Integer> {
    List<CitaEntity> findByFecha(LocalDate fecha);
    boolean existsByCod(String cod);
}