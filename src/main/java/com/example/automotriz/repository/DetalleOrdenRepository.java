package com.example.automotriz.repository;

import com.example.automotriz.entity.DetalleOrdenEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrdenEntity, Integer> {

    @Query("select d from DetalleOrdenEntity d left join fetch d.articulo left join fetch d.servicio " +
            "where d.orden.idOrden = :idOrden order by d.idDetalle")
    List<DetalleOrdenEntity> listarPorOrden(@Param("idOrden") Integer idOrden);

    // ---------- Dashboard ----------

    @Query("select s.categoria, sum(d.subtotal) from DetalleOrdenEntity d join d.servicio s " +
            "where d.orden.idOrden in (select c.orden.idOrden from ComprobanteEntity c " +
            "where c.estadoPago = 'PAGADO' and c.fechaHoraEmision >= :desde and c.fechaHoraEmision < :hasta) " +
            "group by s.categoria")
    List<Object[]> ingresosServiciosPorCategoria(@Param("desde") LocalDateTime desde,
                                                 @Param("hasta") LocalDateTime hasta);

    @Query("select sum(d.subtotal) from DetalleOrdenEntity d where d.tipo = 'ARTICULO' " +
            "and d.orden.idOrden in (select c.orden.idOrden from ComprobanteEntity c " +
            "where c.estadoPago = 'PAGADO' and c.fechaHoraEmision >= :desde and c.fechaHoraEmision < :hasta)")
    BigDecimal ingresosArticulos(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    @Query("select d.nombre, count(d) from DetalleOrdenEntity d " +
            "where d.tipo = 'SERVICIO' and d.orden.estado = 'FINALIZADA' " +
            "and d.orden.fechaHoraSalida >= :desde and d.orden.fechaHoraSalida < :hasta " +
            "group by d.nombre order by count(d) desc")
    List<Object[]> topServicios(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta,
                                Pageable pageable);
}