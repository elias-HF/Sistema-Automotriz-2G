package com.example.automotriz.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "unidad_medida")
public class UnidadMedidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad")
    private int id_unidad;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "abreviatura", length = 10, nullable = false)
    private String abreviatura;

    public UnidadMedidaEntity() {}

    public int getId_unidad() {return id_unidad;}
    public void setId_unidad(int id_unidad) {this.id_unidad = id_unidad;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getAbreviatura() {return abreviatura;}
    public void setAbreviatura(String abreviatura) {this.abreviatura = abreviatura;}
}
