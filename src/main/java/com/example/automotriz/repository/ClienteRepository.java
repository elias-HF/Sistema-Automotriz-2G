package com.example.automotriz.repository;

import com.example.automotriz.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    List<ClienteEntity> findByActivoTrue();
    List<ClienteEntity> findByActivoFalse();
}
