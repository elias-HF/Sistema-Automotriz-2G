package com.example.automotriz.model;

import java.math.BigDecimal;

public class ServicioModel {
    private int idServicio;
    private String nombre;
    private String descripcion;
    private String codigo;
    private BigDecimal precio;
    private boolean activo;

    private int idCategoria;

    public ServicioModel() {}
    public ServicioModel(int idServicio, String nombre, String descripcion, String codigo, BigDecimal precio, boolean activo, int idCategoria) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.precio = precio;
        this.activo = activo;
        this.idCategoria = idCategoria;
    }

    public int getIdServicio() {return idServicio;}
    public void setIdServicio(int idServicio) {this.idServicio = idServicio;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public BigDecimal getPrecio() {return precio;}
    public void setPrecio(BigDecimal precio) {this.precio = precio;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public int getIdCategoria() {return idCategoria;}
    public void setIdCategoria(int idCategoria) {this.idCategoria = idCategoria;}
}