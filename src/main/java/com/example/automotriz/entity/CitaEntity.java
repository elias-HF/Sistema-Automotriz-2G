package com.example.automotriz.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
public class CitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private int id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name ="motivo", length = 200, nullable = false)
    private String motivo;

    @Column(name = "estado" ,length = 50, nullable = false)
    private String estado = "PENDIENTE";

    @Column(name = "cod", length = 15, unique = true, nullable = false)
    private String cod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_contacto", nullable = false)
    private MedioContactoEntity contacto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private VehiculoEntity vehiculo;

    public CitaEntity() {}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public VehiculoEntity getVehiculo() {return vehiculo;}
    public void setVehiculo(VehiculoEntity vehiculo) {this.vehiculo = vehiculo;}

    public MedioContactoEntity getContacto() {return contacto;}
    public void setContacto(MedioContactoEntity contacto) {this.contacto = contacto;}

    public ClienteEntity getCliente() {return cliente;}
    public void setCliente(ClienteEntity cliente) {this.cliente = cliente;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public String getMotivo() {return motivo;}
    public void setMotivo(String motivo) {this.motivo = motivo;}

    public LocalTime getHora() {return hora;}
    public void setHora(LocalTime hora) {this.hora = hora;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
}