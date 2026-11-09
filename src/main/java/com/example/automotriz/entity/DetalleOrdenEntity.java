package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_orden")
public class DetalleOrdenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @Column(name = "tipo", length = 25, nullable = false)
    private String tipo;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "cantidad", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @Column(name = "precio_aplicado", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioAplicado;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenEntity orden;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_servicio", nullable = true)
    private ServicioEntity servicio;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_articulo", nullable = true)
    private ArticuloEntity articulo;

    public DetalleOrdenEntity() {}

    public Integer getIdDetalle() {return idDetalle;}
    public void setIdDetalle(Integer idDetalle) {this.idDetalle = idDetalle;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public BigDecimal getCantidad() {return cantidad;}
    public void setCantidad(BigDecimal cantidad) {this.cantidad = cantidad;}

    public BigDecimal getPrecioAplicado() {return precioAplicado;}
    public void setPrecioAplicado(BigDecimal precioAplicado) {this.precioAplicado = precioAplicado;}

    public BigDecimal getSubtotal() {return subtotal;}
    public void setSubtotal(BigDecimal subtotal) {this.subtotal = subtotal;}

    public OrdenEntity getOrden() {return orden;}
    public void setOrden(OrdenEntity orden) {this.orden = orden;}

    public ServicioEntity getServicio() {return servicio;}
    public void setServicio(ServicioEntity servicio) {this.servicio = servicio;}

    public ArticuloEntity getArticulo() {return articulo;}
    public void setArticulo(ArticuloEntity articulo) {this.articulo = articulo;}
}
