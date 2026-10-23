package com.example.automotriz.repository;

import com.example.automotriz.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    List<ClienteEntity> findByActivoTrue();
}