package com.example.automotriz.model;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaModel {
    private Integer idCita;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser pasada")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String motivo;

    @NotBlank(message = "El estado de la cita es obligatorio")
    private String estado;

    private String codigo;

    @NotBlank(message = "El medio de contacto es obligatorio")
    private String medioContacto;

    private Integer idCliente;
    private Integer idVehiculo;

    public CitaModel() {}
    public CitaModel(Integer idCita, LocalDate fecha, LocalTime hora, String motivo, String estado, String codigo, String medioContacto, Integer idCliente, Integer idVehiculo) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.codigo = codigo;
        this.medioContacto = medioContacto;
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
    }

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

    public Integer getIdCliente() {return idCliente;}
    public void setIdCliente(Integer idCliente) {this.idCliente = idCliente;}

    public Integer getIdVehiculo() {return idVehiculo;}
    public void setIdVehiculo(Integer idVehiculo) {this.idVehiculo = idVehiculo;}
}
