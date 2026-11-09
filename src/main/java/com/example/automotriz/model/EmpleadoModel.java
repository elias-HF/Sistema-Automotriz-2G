package com.example.automotriz.model;

import jakarta.validation.constraints.*;

public class EmpleadoModel {
    private Integer idEmpleado;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    @Email(message = "Ingrese un correo válido")
    private String correo;

    private String codigo;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(max = 25, message = "Máximo 25 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El usuario solo puede contener letras, números y guiones bajos")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 25, message = "La contraseña debe de tener entre 8 y 25 caracteres")
    private String contrasenia;

    private boolean activo;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    public EmpleadoModel() {}
    public EmpleadoModel(Integer idEmpleado, String nombre, String correo, String codigo, String usuario, String contrasenia, boolean activo, String rol) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.correo = correo;
        this.codigo = codigo;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.activo = activo;
        this.rol = rol;
    }

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
