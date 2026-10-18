package com.example.automotriz.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdenServicioModel {
    private int idOrden;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private String diagnostico;
    private BigDecimal montoTotal;
    private String codigo;
    private String estado;

    private int idVehiculo;

    public OrdenServicioModel() {}
    public OrdenServicioModel(int idOrden, LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida, String diagnostico, BigDecimal montoTotal, String codigo, String estado, int idVehiculo) {
        this.idOrden = idOrden;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraSalida = fechaHoraSalida;
        this.diagnostico = diagnostico;
        this.montoTotal = montoTotal;
        this.codigo = codigo;
        this.estado = estado;
        this.idVehiculo = idVehiculo;
    }

    public int getIdOrden() {return idOrden;}
    public void setIdOrden(int idOrden) {this.idOrden = idOrden;}

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

    public int getIdVehiculo() {return idVehiculo;}
    public void setIdVehiculo(int idVehiculo) {this.idVehiculo = idVehiculo;}
}
