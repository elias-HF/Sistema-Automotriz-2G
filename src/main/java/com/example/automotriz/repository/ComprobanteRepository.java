package com.example.automotriz.repository;

import com.example.automotriz.entity.ComprobanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComprobanteRepository extends JpaRepository<ComprobanteEntity, Integer> {

    @Query("select c from ComprobanteEntity c join fetch c.orden o join fetch o.cita ci " +
            "join fetch ci.cliente join fetch ci.vehiculo order by c.idComprobante desc")
    List<ComprobanteEntity> listarTodos();

    @Query("select c.orden.idOrden from ComprobanteEntity c")
    List<Integer> listarIdsOrden();

    boolean existsByOrden_IdOrden(Integer idOrden);

    Optional<ComprobanteEntity> findByOrden_IdOrden(Integer idOrden);
}