package com.example.automotriz.entity;

import jakarta.persistence.*;

@Table
@Entity(name = "empleado")
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private int id_empleado;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "correo", length = 100, unique = true, nullable = false)
    private String correo;

    @Column(name = "cod", length = 15, unique = true, nullable = false)
    private String cod;

    @Column(name = "usuario", length = 50, unique = true, nullable = false)
    private String usuario;

    @Column(name = "contrasenia", length = 255, nullable = false)
    private String contrasenia;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity id_rol;

    public EmpleadoEntity() {}

    public int getId_empleado() {return id_empleado;}
    public void setId_empleado(int id_empleado) {this.id_empleado = id_empleado;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public String getUsuario() {return usuario;}
    public void setUsuario(String usuario) {this.usuario = usuario;}

    public String getContrasenia() {return contrasenia;}
    public void setContrasenia(String contrasenia) {this.contrasenia = contrasenia;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public RolEntity getId_rol() {return id_rol;}
    public void setId_rol(RolEntity id_rol) {this.id_rol = id_rol;}
}
