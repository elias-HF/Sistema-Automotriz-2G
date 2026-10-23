package com.example.automotriz.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaModel {
    private int idCita;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado;
    private String codigo;

    private int idCliente;
    private int idContacto;
    private int idVehiculo;

    public CitaModel() {}
    public CitaModel(int idCita, int idVehiculo, int idContacto, int idCliente, String codigo, String estado, String motivo, LocalTime hora, LocalDate fecha) {
        this.idCita = idCita;
        this.idVehiculo = idVehiculo;
        this.idContacto = idContacto;
        this.idCliente = idCliente;
        this.codigo = codigo;
        this.estado = estado;
        this.motivo = motivo;
        this.hora = hora;
        this.fecha = fecha;
    }

    public int getIdCita() {return idCita;}
    public void setIdCita(int idCita) {this.idCita = idCita;}

    public int getIdVehiculo() {return idVehiculo;}
    public void setIdVehiculo(int idVehiculo) {this.idVehiculo = idVehiculo;}

    public int getIdContacto() {return idContacto;}
    public void setIdContacto(int idContacto) {this.idContacto = idContacto;}

    public int getIdCliente() {return idCliente;}
    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public String getMotivo() {return motivo;}
    public void setMotivo(String motivo) {this.motivo = motivo;}

    public LocalTime getHora() {return hora;}
    public void setHora(LocalTime hora) {this.hora = hora;}

    public LocalDate getFecha() {return fecha;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
}