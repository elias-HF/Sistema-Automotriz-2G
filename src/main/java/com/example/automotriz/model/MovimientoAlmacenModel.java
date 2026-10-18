package com.example.automotriz.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimientoAlmacenModel {
    private int idMovimiento;
    private String tipo;
    private LocalDate fecha;
    private String codigo;
    private String motivo;
    private BigDecimal cantidad;

    private int idOrden;
    private int idArticulo;

    public MovimientoAlmacenModel() {}
    public MovimientoAlmacenModel(int idMovimiento, String tipo, LocalDate fecha, String codigo, String motivo, BigDecimal cantidad, int idOrden, int idArticulo) {
        this.idMovimiento = idMovimiento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.codigo = codigo;
        this.motivo = motivo;
        this.cantidad = cantidad;
        this.idOrden = idOrden;
        this.idArticulo = idArticulo;
    }

    public int getIdMovimiento() {return idMovimiento;}
    public void setIdMovimiento(int idMovimiento) {this.idMovimiento = idMovimiento;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getMotivo() {return motivo;}
    public void setMotivo(String motivo) {this.motivo = motivo;}

    public BigDecimal getCantidad() {return cantidad;}
    public void setCantidad(BigDecimal cantidad) {this.cantidad = cantidad;}

    public int getIdOrden() {return idOrden;}
    public void setIdOrden(int idOrden) {this.idOrden = idOrden;}

    public int getIdArticulo() {return idArticulo;}
    public void setIdArticulo(int idArticulo) {this.idArticulo = idArticulo;}
}
