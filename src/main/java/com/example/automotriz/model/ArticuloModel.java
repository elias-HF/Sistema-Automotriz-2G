package com.example.automotriz.model;

import java.math.BigDecimal;

public class ArticuloModel {
    private int idArticulo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal stock;
    private String codigo;
    private BigDecimal stockMinimo;
    private boolean activo;

    private int idUnidad;

    public ArticuloModel() {}
    public ArticuloModel(int idArticulo, String nombre, String descripcion, BigDecimal precio, BigDecimal stock, String codigo, BigDecimal stockMinimo, boolean activo, int idUnidad) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.codigo = codigo;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
        this.idUnidad = idUnidad;
    }

    public int getIdArticulo() {return idArticulo;}
    public void setIdArticulo(int idArticulo) {this.idArticulo = idArticulo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public BigDecimal getPrecio() {return precio;}
    public void setPrecio(BigDecimal precio) {this.precio = precio;}

    public BigDecimal getStock() {return stock;}
    public void setStock(BigDecimal stock) {this.stock = stock;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public BigDecimal getStockMinimo() {return stockMinimo;}
    public void setStockMinimo(BigDecimal stockMinimo) {this.stockMinimo = stockMinimo;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public int getIdUnidad() {return idUnidad;}
    public void setIdUnidad(int idUnidad) {this.idUnidad = idUnidad;}
}
