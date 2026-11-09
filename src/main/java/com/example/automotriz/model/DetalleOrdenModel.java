package com.example.automotriz.model;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class DetalleOrdenModel {
    private Integer idDetalle;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9\\s.,()/#-]{3,100}$", message = "Se han ingresado caracteres inválidos")
    private String nombre;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", inclusive = true, message = "La cantidad mínima es 0.01")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal cantidad;

    @NotNull(message = "El precio aplicado es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio aplicado mínimo es S/ 0.00")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal precioAplicado;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "El subtotal mínimo es 0.01")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal subtotal;

    private Integer idOrden;
    private Integer idServicio;
    private Integer idArticulo;

    public DetalleOrdenModel() {}
    public DetalleOrdenModel(Integer idDetalle, String tipo, String nombre, BigDecimal cantidad, BigDecimal precioAplicado, BigDecimal subtotal, Integer idOrden, Integer idServicio, Integer idArticulo) {
        this.idDetalle = idDetalle;
        this.tipo = tipo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioAplicado = precioAplicado;
        this.subtotal = subtotal;
        this.idOrden = idOrden;
        this.idServicio = idServicio;
        this.idArticulo = idArticulo;
    }

    public Integer getIdDetalle() {return idDetalle;}
    public void setIdDetalle(Integer idDetalle) {this.idDetalle = idDetalle;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public BigDecimal getCantidad() {return cantidad;}
    public void setCantidad(BigDecimal cantidad) {this.cantidad = cantidad;}

    public BigDecimal getPrecioAplicado() {return precioAplicado;}
    public void setPrecioAplicado(BigDecimal precioAplicado) {this.precioAplicado = precioAplicado;}

    public BigDecimal getSubtotal() {return subtotal;}
    public void setSubtotal(BigDecimal subtotal) {this.subtotal = subtotal;}

    public Integer getIdOrden() {return idOrden;}
    public void setIdOrden(Integer idOrden) {this.idOrden = idOrden;}

    public Integer getIdServicio() {return idServicio;}
    public void setIdServicio(Integer idServicio) {this.idServicio = idServicio;}

    public Integer getIdArticulo() {return idArticulo;}
    public void setIdArticulo(Integer idArticulo) {this.idArticulo = idArticulo;}
}
