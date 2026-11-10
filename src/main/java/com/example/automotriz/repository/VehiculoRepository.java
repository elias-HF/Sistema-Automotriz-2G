package com.example.automotriz.repository;

import com.example.automotriz.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Integer> {
    List<VehiculoEntity> findByCliente_IdClienteAndActivoTrue(Integer idCliente);
}
