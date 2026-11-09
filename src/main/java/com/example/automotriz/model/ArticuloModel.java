package com.example.automotriz.model;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ArticuloModel {
    private Integer idArticulo;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\s\\-/().,]+$", message = "Se han ingresado caracteres inválidos")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String descripcion;

    @NotNull(message = "El precio por defecto es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio por defecto mínimo es S/ 0.00")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal precioPorDefecto;

    @NotNull(message = "El stock es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El valor mínimo es 0.00")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal stock;

    private String codigo;

    @NotNull(message = "El stock es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El valor mínimo es 0.00")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal stockMinimo;

    private boolean activo;

    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida;

    public ArticuloModel() {}
    public ArticuloModel(Integer idArticulo, String nombre, String descripcion, BigDecimal precioPorDefecto, BigDecimal stock, String codigo, BigDecimal stockMinimo, boolean activo, String unidadMedida) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioPorDefecto = precioPorDefecto;
        this.stock = stock;
        this.codigo = codigo;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
        this.unidadMedida = unidadMedida;
    }

    public Integer getIdArticulo() {return idArticulo;}
    public void setIdArticulo(Integer idArticulo) {this.idArticulo = idArticulo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public BigDecimal getPrecioPorDefecto() {return precioPorDefecto;}
    public void setPrecioPorDefecto(BigDecimal precioPorDefecto) {
        this.precioPorDefecto = precioPorDefecto;}

    public BigDecimal getStock() {return stock;}
    public void setStock(BigDecimal stock) {this.stock = stock;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public BigDecimal getStockMinimo() {return stockMinimo;}
    public void setStockMinimo(BigDecimal stockMinimo) {this.stockMinimo = stockMinimo;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public String getUnidadMedida() {return unidadMedida;}
    public void setUnidadMedida(String unidadMedida) {this.unidadMedida = unidadMedida;}
}
