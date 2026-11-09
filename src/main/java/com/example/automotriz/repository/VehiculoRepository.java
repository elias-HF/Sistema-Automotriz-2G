package com.example.automotriz.repository;

import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Integer> { }
