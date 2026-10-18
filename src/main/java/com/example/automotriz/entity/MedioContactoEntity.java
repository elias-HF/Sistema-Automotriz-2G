package com.example.automotriz.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "medio_contacto")
public class MedioContactoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private int id_contacto;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    public MedioContactoEntity() {}

    public int getId_contacto() {return id_contacto;}
    public void setId_contacto(int id_contacto) {this.id_contacto = id_contacto;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
}
