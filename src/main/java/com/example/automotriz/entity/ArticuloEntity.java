package com.example.automotriz.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table
@Entity(name = "articulo")
public class ArticuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private int id_articulo;

    @Column(name = "nombre", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "stock", precision = 10, scale = 2, nullable = false)
    private BigDecimal stock;

    @Column(name = "cod", length = 15, unique = true, nullable = false)
    private String cod;

    @Column(name = "stock_min", precision = 10, scale = 2, nullable = false)
    private BigDecimal stock_min;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_unidad", nullable = false)
    private UnidadMedidaEntity unidad;

    public ArticuloEntity() {}

    public int getId_articulo() {return id_articulo;}
    public void setId_articulo(int id_articulo) {this.id_articulo = id_articulo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public BigDecimal getPrecio() {return precio;}
    public void setPrecio(BigDecimal precio) {this.precio = precio;}

    public BigDecimal getStock() {return stock;}
    public void setStock(BigDecimal stock) {this.stock = stock;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public BigDecimal getStock_min() {return stock_min;}
    public void setStock_min(BigDecimal stock_min) {this.stock_min = stock_min;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public UnidadMedidaEntity getUnidad() {return unidad;}
    public void setUnidad(UnidadMedidaEntity unidad) {this.unidad = unidad;}
}
