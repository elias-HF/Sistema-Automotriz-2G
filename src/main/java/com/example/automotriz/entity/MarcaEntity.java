package com.example.automotriz.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "marca")
public class MarcaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private int id_marca;

    @Column(name = "nombre", length = 50, unique = true, nullable = false)
    private String nombre;

    public MarcaEntity() {}

    public int getId_marca() {return id_marca;}
    public void setId_marca(int id_marca) {this.id_marca = id_marca;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
}
