package com.example.automotriz.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimiento_almacen")
public class MovimientoAlmacenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private int id_movimiento;

    @Column(name = "tipo", length = 50 , nullable = false)
    private String tipo = "SALIDA";

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "cod", nullable = false, length = 15, unique = true)
    private String cod;

    @Column(name = "motivo", length = 200, nullable = false)
    private String motivo;

    @Column(name = "cantidad", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenServicioEntity orden;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_articulo", nullable = false)
    private ArticuloEntity articulo;

    public MovimientoAlmacenEntity() {}

    public int getId_movimiento() {return id_movimiento;}
    public void setId_movimiento(int id_movimiento) {this.id_movimiento = id_movimiento;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public String getMotivo() {return motivo;}
    public void setMotivo(String motivo) {this.motivo = motivo;}

    public BigDecimal getCantidad() {return cantidad;}
    public void setCantidad(BigDecimal cantidad) {this.cantidad = cantidad;}

    public OrdenServicioEntity getOrden() {return orden;}
    public void setOrden(OrdenServicioEntity orden) {this.orden = orden;}

    public ArticuloEntity getArticulo() {return articulo;}
    public void setArticulo(ArticuloEntity articulo) {this.articulo = articulo;}
}
