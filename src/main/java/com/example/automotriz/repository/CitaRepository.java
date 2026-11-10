package com.example.automotriz.repository;

import com.example.automotriz.entity.CitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<CitaEntity, Integer> {
    // Citas de un rango de fechas (el calendario pide solo lo que está mostrando)
    @Query("select c from CitaEntity c join fetch c.cliente join fetch c.vehiculo " +
            "where c.fecha >= :desde and c.fecha < :hasta order by c.fecha, c.hora")
    List<CitaEntity> listarPorRango(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    boolean existsByVehiculo_IdVehiculoAndFechaAndEstado(Integer idVehiculo, LocalDate fecha, String estado);

    boolean existsByVehiculo_IdVehiculoAndFechaAndEstadoAndIdCitaNot(
            Integer idVehiculo, LocalDate fecha, String estado, Integer idCita);

    @Query("select c from CitaEntity c join fetch c.cliente join fetch c.vehiculo " +
            "where c.estado = 'PENDIENTE' order by c.fecha, c.hora")
    List<CitaEntity> listarPendientes();
}
