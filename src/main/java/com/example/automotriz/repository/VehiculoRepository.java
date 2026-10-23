package com.example.automotriz.repository;

import com.example.automotriz.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Integer> {
    List<VehiculoEntity> findByCliente_IdClienteAndActivoTrue(int idCliente);
    List<VehiculoEntity> findByActivoTrue();
}