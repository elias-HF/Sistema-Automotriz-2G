package com.example.automotriz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orden_empleado")
public class OrdenEmpleadoEntity {

    @EmbeddedId
    private OrdenEmpleadoId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idOrden")
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenEntity orden;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idEmpleado")
    @JoinColumn(name = "id_empleado", nullable = false)
    private EmpleadoEntity empleado;

    public OrdenEmpleadoEntity() {}

    public OrdenEmpleadoEntity(OrdenEntity orden, EmpleadoEntity empleado) {
        this.id = new OrdenEmpleadoId(orden.getIdOrden(), empleado.getIdEmpleado());
        this.orden = orden;
        this.empleado = empleado;
    }

    public OrdenEmpleadoId getId() {return id;}
    public OrdenEntity getOrden() {return orden;}
    public EmpleadoEntity getEmpleado() {return empleado;}
}
