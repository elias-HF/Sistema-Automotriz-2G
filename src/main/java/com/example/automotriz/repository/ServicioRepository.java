package com.example.automotriz.repository;

import com.example.automotriz.entity.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Integer> {
    List<ServicioEntity> findByActivoTrueOrderByNombreAsc();
}