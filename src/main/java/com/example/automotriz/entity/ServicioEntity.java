package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicio")
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(name = "nombre", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    private String codigo;

    @Column(name = "precio_por_defecto", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioPorDefecto;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "categoria", length = 25, nullable = false)
    private String categoria;

    public ServicioEntity() {}

    public Integer getIdServicio() {return idServicio;}
    public void setIdServicio(Integer idServicio) {this.idServicio = idServicio;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public BigDecimal getPrecioPorDefecto() {return precioPorDefecto;}
    public void setPrecioPorDefecto(BigDecimal precioPorDefecto) {this.precioPorDefecto = precioPorDefecto;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public String getCategoria() {return categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}
}
