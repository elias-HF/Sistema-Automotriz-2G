package com.example.automotriz.model;

public class ClienteModel {
    private int idCliente;
    private String nombre;
    private String telefono;
    private String correo;
    private String codigo;
    private String dni;
    private boolean activo;

    public ClienteModel() {}
    public ClienteModel(int idCliente, String nombre, String telefono, String correo, String codigo, String dni, boolean activo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.codigo = codigo;
        this.dni = dni;
        this.activo = activo;
    }

    public int getIdCliente() {return idCliente;}
    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public String getDni() {return dni;}
    public void setDni(String dni) {this.dni = dni;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
}