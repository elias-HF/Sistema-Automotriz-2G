package com.example.automotriz.repository;

import com.example.automotriz.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer>{
    Optional<EmpleadoEntity> findByUsuario(String usuario);
}
