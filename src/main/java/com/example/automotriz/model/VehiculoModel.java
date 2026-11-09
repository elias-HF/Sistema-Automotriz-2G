package com.example.automotriz.model;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class VehiculoModel {
    private Integer idVehiculo;

    @NotNull(message = "El año de fabricación es obligatorio")
    @Min(value = 1900, message = "El año no puede ser menor a 1900")
    private Integer anioFabricacion;

    @NotBlank(message = "La placa es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}-[0-9]{3}$", message = "Ingrese una placa válida")
    private String placa;

    @NotNull(message = "El kilometraje es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El kilometraje mínimo es 0.00 km")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal kilometros;

    private String codigo;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 25, message = "El modelo no puede superar los 25 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9]+(?:[ -][A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9]+)*$", message = "Se han ingresado caracteres inválidos")
    private String modelo;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 25, message = "La marca no puede superar los 25 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9]+(?:[ -][A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9]+)*$", message = "Se han ingresado caracteres inválidos")
    private String marca;

    private boolean activo;
    private Integer idCliente;

    public VehiculoModel() {}
    public VehiculoModel(Integer idVehiculo, Integer anioFabricacion, String placa, BigDecimal kilometros, String codigo, String modelo, String marca, boolean activo, Integer idCliente) {
        this.idVehiculo = idVehiculo;
        this.anioFabricacion = anioFabricacion;
        this.placa = placa;
        this.kilometros = kilometros;
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.activo = activo;
        this.idCliente = idCliente;
    }

    public Integer getIdVehiculo() {return idVehiculo;}
    public void setIdVehiculo(Integer idVehiculo) {this.idVehiculo = idVehiculo;}

    public Integer getAnioFabricacion() {return anioFabricacion;}
    public void setAnioFabricacion(Integer anioFabricacion) {this.anioFabricacion = anioFabricacion;}

    public String getPlaca() {return placa;}
    public void setPlaca(String placa) {this.placa = placa;}

    public BigDecimal getKilometros() {return kilometros;}
    public void setKilometros(BigDecimal kilometros) {this.kilometros = kilometros;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getModelo() {return modelo;}
    public void setModelo(String modelo) {this.modelo = modelo;}

    public String getMarca() {return marca;}
    public void setMarca(String marca) {this.marca = marca;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public Integer getIdCliente() {return idCliente;}
    public void setIdCliente(Integer idCliente) {this.idCliente = idCliente;}
}
