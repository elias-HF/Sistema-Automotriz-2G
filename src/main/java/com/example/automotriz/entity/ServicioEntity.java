package com.example.automotriz.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table
@Entity(name = "servicio")
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private int id_servicio;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "cod", length = 15, unique = true, nullable = false)
    private String cod;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaEntity id_categoria;

    public ServicioEntity() {}

    public int getId_servicio() {return id_servicio;}
    public void setId_servicio(int id_servicio) {this.id_servicio = id_servicio;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public BigDecimal getPrecio() {return precio;}
    public void setPrecio(BigDecimal precio) {this.precio = precio;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public CategoriaEntity getId_categoria() {return id_categoria;}
    public void setId_categoria(CategoriaEntity id_categoria) {this.id_categoria = id_categoria;}
}
