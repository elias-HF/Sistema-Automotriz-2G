package com.example.automotriz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "telefono", length = 9, nullable = false)
    private String telefono;

    @Column(name = "correo", length = 100, unique = true, nullable = false)
    private String correo;

    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    private String codigo;

    @Column(name = "dni", length = 8, unique = true, nullable = false)
    private String dni;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    public ClienteEntity() {}

    public Integer getIdCliente() {return idCliente;}
    public void setIdCliente(Integer idCliente) {this.idCliente = idCliente;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getDni() {return dni;}
    public void setDni(String dni) {this.dni = dni;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}
}