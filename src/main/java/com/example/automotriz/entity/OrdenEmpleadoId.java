package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/* Clase de java para vincular las id's de orden y empleado como una sola primary key */
@Embeddable
public class OrdenEmpleadoId implements Serializable {

    @Column(name = "id_orden")
    private Integer idOrden;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    public OrdenEmpleadoId() {}
    public OrdenEmpleadoId(Integer idOrden, Integer idEmpleado) {
        this.idOrden = idOrden;
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdOrden() {return idOrden;}
    public void setIdOrden(Integer idOrden) {this.idOrden = idOrden;}

    public Integer getIdEmpleado() {return idEmpleado;}
    public void setIdEmpleado(Integer idEmpleado) {this.idEmpleado = idEmpleado;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdenEmpleadoId)) return false;
        OrdenEmpleadoId that = (OrdenEmpleadoId) o;
        return Objects.equals(idOrden, that.idOrden) && Objects.equals(idEmpleado, that.idEmpleado);
    }

    @Override
    public int hashCode() {return Objects.hash(idOrden, idEmpleado);}
}
