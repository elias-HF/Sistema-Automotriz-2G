package com.example.automotriz.repository;

import com.example.automotriz.entity.ComprobantePagoEntity;
import com.example.automotriz.entity.OrdenServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComprobantePagoRepository extends JpaRepository<ComprobantePagoEntity, Integer> {

    Optional<ComprobantePagoEntity> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    boolean existsByOrden(OrdenServicioEntity orden);
    Optional<ComprobantePagoEntity> findByOrden(OrdenServicioEntity orden);
}
