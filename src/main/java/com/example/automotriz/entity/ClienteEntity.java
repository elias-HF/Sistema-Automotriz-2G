package com.example.automotriz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int idCliente;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "cod", unique = true, nullable = false, length = 15)
    private String cod;

    @Column(name = "dni", unique = true, nullable = false, length = 8)
    private String dni;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    public ClienteEntity() {}

    public int getIdCliente() {return idCliente;}
    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public String getDni() {return dni;}
    public void setDni(String dni) {this.dni = dni;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}
}

