package com.example.automotriz.repository;

import com.example.automotriz.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository <CitaEntity, Integer> { }
