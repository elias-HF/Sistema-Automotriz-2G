package com.example.automotriz.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private int id_categoria;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    public CategoriaEntity() {}

    public int getId_categoria() {return id_categoria;}
    public void setId_categoria(int id_categoria) {this.id_categoria = id_categoria;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}
}
