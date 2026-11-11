package com.example.automotriz.repository;

import com.example.automotriz.entity.OrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepository extends JpaRepository<OrdenEntity, Integer> {

    @Query("select o from OrdenEntity o join fetch o.cita c join fetch c.cliente join fetch c.vehiculo " +
            "where o.estado <> :estado order by o.idOrden desc")
    List<OrdenEntity> listarPorEstadoDistinto(@Param("estado") String estado);

    @Query("select o from OrdenEntity o join fetch o.cita c join fetch c.cliente join fetch c.vehiculo " +
            "where o.idOrden = :id")
    Optional<OrdenEntity> buscarConCita(@Param("id") Integer id);

    @Query("select o from OrdenEntity o join fetch o.cita c join fetch c.cliente join fetch c.vehiculo " +
            "where o.estado = :estado order by o.idOrden desc")
    List<OrdenEntity> listarPorEstado(@Param("estado") String estado);

    @Query("select count(o) from OrdenEntity o where o.estado = 'FINALIZADA' " +
            "and o.fechaHoraSalida >= :desde and o.fechaHoraSalida < :hasta")
    long contarFinalizadas(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("select o.estado, count(o) from OrdenEntity o " +
            "where o.fechaHoraIngreso >= :desde and o.fechaHoraIngreso < :hasta group by o.estado")
    List<Object[]> contarPorEstado(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("select o.fechaHoraIngreso from OrdenEntity o where o.estado <> 'CANCELADA' " +
            "and o.fechaHoraIngreso >= :desde and o.fechaHoraIngreso < :hasta")
    List<LocalDateTime> fechasIngreso(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("select o from OrdenEntity o join fetch o.cita c join fetch c.cliente join fetch c.vehiculo " +
            "where o.fechaHoraIngreso >= :desde and o.fechaHoraIngreso < :hasta order by o.idOrden desc")
    List<OrdenEntity> listarPorRango(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
}
