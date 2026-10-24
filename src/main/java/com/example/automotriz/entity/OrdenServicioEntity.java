package com.example.automotriz.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table
@Entity(name = "orden_servicio")
public class OrdenServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private int id;

    @Column(name = "fecha_hora_ingreso", nullable = false)
    private LocalDateTime fechaHora_ingreso;

    @Column(name = "fecha_hora_salida")
    private LocalDateTime fechaHora_salida;

    @Column(name = "diagnostico", nullable = false, length = 200)
    private String diagnostico;

    @Column(name = "monto_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal monto_total;

    @Column(name = "cod", length = 15, unique = true, nullable = false)
    private String cod;

    @Column(name = "estado", length = 50, nullable = false)
    private String estado = "PENDIENTE";

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private VehiculoEntity vehiculo;

    public OrdenServicioEntity() {}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public LocalDateTime getFechaHora_ingreso() {return fechaHora_ingreso;}
    public void setFechaHora_ingreso(LocalDateTime fechaHora_ingreso) {this.fechaHora_ingreso = fechaHora_ingreso;}

    public LocalDateTime getFechaHora_salida() {return fechaHora_salida;}
    public void setFechaHora_salida(LocalDateTime fechaHora_salida) {this.fechaHora_salida = fechaHora_salida;}

    public String getDiagnostico() {return diagnostico;}
    public void setDiagnostico(String diagnostico) {this.diagnostico = diagnostico;}

    public BigDecimal getMonto_total() {return monto_total;}
    public void setMonto_total(BigDecimal monto_total) {this.monto_total = monto_total;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public VehiculoEntity getVehiculo() {return vehiculo;}
    public void setVehiculo(VehiculoEntity vehiculo) {this.vehiculo = vehiculo;}
}
