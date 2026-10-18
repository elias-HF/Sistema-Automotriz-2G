package com.example.automotriz.model;

import java.math.BigDecimal;

public class VehiculoModel {
    private int idVehiculo;
    private String placa;
    private int añoFabricacion;
    private BigDecimal kilometros;
    private String codigo;
    private boolean activo;

    private int idModelo;
    private int idCliente;

    public VehiculoModel() {}
    public VehiculoModel(int idVehiculo, String placa, int añoFabricacion, BigDecimal kilometros, String codigo, boolean activo, int idModelo, int idCliente) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.añoFabricacion = añoFabricacion;
        this.kilometros = kilometros;
        this.codigo = codigo;
        this.activo = activo;
        this.idModelo = idModelo;
        this.idCliente = idCliente;
    }

    public int getIdVehiculo() {return idVehiculo;}
    public void setIdVehiculo(int idVehiculo) {this.idVehiculo = idVehiculo;}

    public String getPlaca() {return placa;}
    public void setPlaca(String placa) {this.placa = placa;}

    public int getAñoFabricacion() {return añoFabricacion;}
    public void setAñoFabricacion(int añoFabricacion) {this.añoFabricacion = añoFabricacion;}

    public BigDecimal getKilometros() {return kilometros;}
    public void setKilometros(BigDecimal kilometros) {this.kilometros = kilometros;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public int getIdModelo() {return idModelo;}
    public void setIdModelo(int idModelo) {this.idModelo = idModelo;}

    public int getIdCliente() {return idCliente;}
    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}
}
