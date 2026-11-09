package com.example.automotriz.model;

public class OrdenEmpleadoModel {
    private Integer idOrden;
    private Integer idEmpleado;

    public OrdenEmpleadoModel() {}
    public OrdenEmpleadoModel(Integer idOrden, Integer idEmpleado) {
        this.idOrden = idOrden;
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdOrden() {return idOrden;}
    public void setIdOrden(Integer idOrden) {this.idOrden = idOrden;}

    public Integer getIdEmpleado() {return idEmpleado;}
    public void setIdEmpleado(Integer idEmpleado) {this.idEmpleado = idEmpleado;}
}
