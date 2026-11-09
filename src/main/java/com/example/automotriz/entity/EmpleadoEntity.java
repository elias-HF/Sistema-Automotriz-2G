package com.example.automotriz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado")
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "correo", length = 50, unique = true, nullable = false)
    private String correo;

    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    private String codigo;

    @Column(name = "usuario", length = 25, unique = true, nullable = false)
    private String usuario;

    @Column(name = "contrasenia", length = 255, nullable = false)
    private String contrasenia;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "rol", length = 25, nullable = false)
    private String rol;

    public EmpleadoEntity() {}

    public Integer getIdEmpleado() {return idEmpleado;}
    public void setIdEmpleado(Integer idEmpleado) {this.idEmpleado = idEmpleado;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getUsuario() {return usuario;}
    public void setUsuario(String usuario) {this.usuario = usuario;}

    public String getContrasenia() {return contrasenia;}
    public void setContrasenia(String contrasenia) {this.contrasenia = contrasenia;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public String getRol() {return rol;}
    public void setRol(String rol) {this.rol = rol;}
}
