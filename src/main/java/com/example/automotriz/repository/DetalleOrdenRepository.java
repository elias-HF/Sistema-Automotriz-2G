package com.example.automotriz.repository;

import com.example.automotriz.entity.DetalleOrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrdenEntity, Integer> {

    @Query("select d from DetalleOrdenEntity d left join fetch d.articulo left join fetch d.servicio " +
            "where d.orden.idOrden = :idOrden order by d.idDetalle")
    List<DetalleOrdenEntity> listarPorOrden(@Param("idOrden") Integer idOrden);
}
