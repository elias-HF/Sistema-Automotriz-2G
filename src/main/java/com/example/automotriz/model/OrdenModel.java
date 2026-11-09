package com.example.automotriz.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public class OrdenModel {
    private Integer idOrden;

    @NotNull(message = "La fecha y hora de ingreso son obligatorias")
    private LocalDateTime fechaHoraIngreso;

    private LocalDateTime fechaHoraSalida;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String diagnostico;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El monto total mínimo es S/ 0.00")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal montoTotal;

    private String codigo;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private Integer idCita;

    public OrdenModel() {}
    public OrdenModel(Integer idOrden, LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida, String diagnostico, BigDecimal montoTotal, String codigo, String estado, Integer idCita) {
        this.idOrden = idOrden;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraSalida = fechaHoraSalida;
        this.diagnostico = diagnostico;
        this.montoTotal = montoTotal;
        this.codigo = codigo;
        this.estado = estado;
        this.idCita = idCita;
    }

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

    public Integer getIdCita() {return idCita;}
    public void setIdCita(Integer idCita) {this.idCita = idCita;}
}
