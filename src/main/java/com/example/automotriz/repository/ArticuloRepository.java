
package com.example.automotriz.repository;

import com.example.automotriz.entity.ArticuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloRepository
        extends JpaRepository<ArticuloEntity, Integer> {

    @Query("""
        SELECT a FROM articulo a
        WHERE a.activo = true
          AND (
            :buscar = ''
            OR LOWER(a.nombre) LIKE LOWER(CONCAT('%', :buscar, '%'))
            OR LOWER(a.cod) LIKE LOWER(CONCAT('%', :buscar, '%'))
          )
        ORDER BY a.nombre
        """)
    List<ArticuloEntity> buscarActivos(
            @Param("buscar") String buscar);

    long countByActivoTrue();

    @Query("""
        SELECT COUNT(a) FROM articulo a
        WHERE a.activo = true
          AND a.stock > a.stock_min
        """)
    long contarStockSuficiente();

    @Query("""
        SELECT COUNT(a) FROM articulo a
        WHERE a.activo = true
          AND a.stock <= a.stock_min
        """)
    long contarStockCritico();
}
