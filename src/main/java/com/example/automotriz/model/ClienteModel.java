package com.example.automotriz.model;

import jakarta.validation.constraints.*;

public class ClienteModel {
    private Integer idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^9[0-9]{8}$", message = "Ingrese un celular válido de 9 dígitos")
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    @Email(message = "Ingrese un correo válido")
    private String correo;

    private String codigo;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}$", message = "Ingrese un DNI válido de 8 dígitos")
    private String dni;

    private boolean activo;

    public ClienteModel() {}
    public ClienteModel(Integer idCliente, String nombre, String telefono, String correo, String codigo, String dni, boolean activo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.codigo = codigo;
        this.dni = dni;
        this.activo = activo;
    }

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
