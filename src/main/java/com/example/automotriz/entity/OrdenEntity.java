package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orden")
public class OrdenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    @Column(name = "fecha_hora_ingreso", nullable = false)
    private LocalDateTime fechaHoraIngreso;

    @Column(name = "fecha_hora_salida", nullable = true)
    private LocalDateTime fechaHoraSalida;

    @Column(name = "diagnostico", length = 200, nullable = false)
    private String diagnostico;

    @Column(name = "monto_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoTotal;

    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    private String codigo;

    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cita", nullable = false)
    private CitaEntity cita;

    public OrdenEntity() {}

    public Integer getIdOrden() {return idOrden;}
    public void setIdOrden(Integer idOrden) {this.idOrden = idOrden;}

    public LocalDateTime getFechaHoraIngreso() {return fechaHoraIngreso;}
    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) {this.fechaHoraIngreso = fechaHoraIngreso;}

    public LocalDateTime getFechaHoraSalida() {return fechaHoraSalida;}
    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {this.fechaHoraSalida = fechaHoraSalida;}

    public String getDiagnostico() {return diagnostico;}
    public void setDiagnostico(String diagnostico) {this.diagnostico = diagnostico;}

    public BigDecimal getMontoTotal() {return montoTotal;}
    public void setMontoTotal(BigDecimal montoTotal) {this.montoTotal = montoTotal;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public CitaEntity getCita() {return cita;}
    public void setCita(CitaEntity cita) {this.cita = cita;}
}
