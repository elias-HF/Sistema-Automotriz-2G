package com.example.automotriz.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "modelo")
public class ModeloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private int id_modelo;

    @Column(name = "nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_marca", nullable = false)
    private MarcaEntity marca;

    public ModeloEntity() {}

    public int getId_modelo() {return id_modelo;}
    public void setId_modelo(int id_modelo) {this.id_modelo = id_modelo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public MarcaEntity getMarca() {return marca;}
    public void setMarca(MarcaEntity marca) {this.marca = marca;}
}
