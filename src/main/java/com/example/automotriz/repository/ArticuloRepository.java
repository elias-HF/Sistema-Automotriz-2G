package com.example.automotriz.repository;

import com.example.automotriz.entity.ArticuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<ArticuloEntity, Integer> {
    List<ArticuloEntity> findByActivoTrueOrderByNombreAsc();
}