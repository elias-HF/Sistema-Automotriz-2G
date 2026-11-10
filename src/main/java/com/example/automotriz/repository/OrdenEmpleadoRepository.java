package com.example.automotriz.repository;

import com.example.automotriz.entity.OrdenEmpleadoEntity;
import com.example.automotriz.entity.OrdenEmpleadoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenEmpleadoRepository extends JpaRepository<OrdenEmpleadoEntity, OrdenEmpleadoId> { }