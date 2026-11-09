package com.example.automotriz.model;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class ServicioModel {
    private Integer idServicio;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String descripcion;

    private String codigo;

    @NotNull(message = "El precio por defecto es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio por defecto mínimo es S/ 0.00")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal precioPorDefecto;

    private boolean activo;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    public ServicioModel() {}
    public ServicioModel(Integer idServicio, String nombre, String descripcion, String codigo, BigDecimal precioPorDefecto, boolean activo, String categoria) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.precioPorDefecto = precioPorDefecto;
        this.activo = activo;
        this.categoria = categoria;
    }

    public Integer getIdServicio() {return idServicio;}
    public void setIdServicio(Integer idServicio) {this.idServicio = idServicio;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public BigDecimal getPrecioPorDefecto() {return precioPorDefecto;}
    public void setPrecioPorDefecto(BigDecimal precioPorDefecto) {this.precioPorDefecto = precioPorDefecto;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public String getCategoria() {return categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}
}
