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
    private Integer idCita;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "motivo", length = 200, nullable = false)
    private String motivo;

    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    private String codigo;

    @Column(name = "medio_contacto", length = 25, nullable = false)
    private String medioContacto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private VehiculoEntity vehiculo;

    public CitaEntity() {}

    public Integer getIdCita() {return idCita;}
    public void setIdCita(Integer idCita) {this.idCita = idCita;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public LocalTime getHora() {return hora;}
    public void setHora(LocalTime hora) {this.hora = hora;}

    public String getMotivo() {return motivo;}
    public void setMotivo(String motivo) {this.motivo = motivo;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getMedioContacto() {return medioContacto;}
    public void setMedioContacto(String medioContacto) {this.medioContacto = medioContacto;}

    public ClienteEntity getCliente() {return cliente;}
    public void setCliente(ClienteEntity cliente) {this.cliente = cliente;}

    public VehiculoEntity getVehiculo() {return vehiculo;}
    public void setVehiculo(VehiculoEntity vehiculo) {this.vehiculo = vehiculo;}
}
