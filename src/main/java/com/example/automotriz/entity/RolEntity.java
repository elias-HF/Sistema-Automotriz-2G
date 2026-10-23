package com.example.automotriz.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "rol")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private int id_rol;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    public RolEntity() {}

    public int getId_rol() {return id_rol;}
    public void setId_rol(int id_rol) {this.id_rol = id_rol;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
}