package com.example.automotriz.model;

public class EmpleadoModel {
    private int idEmpleado;
    private String nombre;
    private String correo;
    private String codigo;
    private String usuario;
    private String contrasenia;
    private boolean activo;

    private int idRol;

    public EmpleadoModel() {}
    public EmpleadoModel(int idEmpleado, String nombre, String correo, String codigo, String usuario, String contrasenia, boolean activo, int idRol) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.correo = correo;
        this.codigo = codigo;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.activo = activo;
        this.idRol = idRol;
    }

    public int getIdEmpleado() {return idEmpleado;}
    public void setIdEmpleado(int idEmpleado) {this.idEmpleado = idEmpleado;}

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

    public int getIdRol() {return idRol;}
    public void setIdRol(int idRol) {this.idRol = idRol;}
}