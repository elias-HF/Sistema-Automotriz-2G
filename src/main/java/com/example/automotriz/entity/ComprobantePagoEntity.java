package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity(name = "comprobante_pago")
public class ComprobantePagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private int id_comprobante;

    @Column(name = "fechaHora_emision", nullable = false)
    private LocalDateTime fechaHora_emision;

    @Column(name = "met_pago", length = 50, nullable = false)
    private String met_pago = "EFECTIVO";

    @Column(name = "cod", nullable = false, length = 15, unique = true)
    private String cod;

    @Column(name = "tipo_comp", length = 50, nullable = false)
    private String tipo_comp = "FACTURA";

    @Column(name = "estado", length = 50, nullable = false)
    private String estado = "PENDIENTE";

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenServicioEntity orden;

    public ComprobantePagoEntity() {}

    public int getId_comprobante() {return id_comprobante;}
    public void setId_comprobante(int id_comprobante) {this.id_comprobante = id_comprobante;}

    public LocalDateTime getFechaHora_emision() {return fechaHora_emision;}
    public void setFechaHora_emision(LocalDateTime fechaHora_emision) {this.fechaHora_emision = fechaHora_emision;}

    public String getMet_pago() {return met_pago;}
    public void setMet_pago(String met_pago) {this.met_pago = met_pago;}

    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}

    public String getTipo_comp() {return tipo_comp;}
    public void setTipo_comp(String tipo_comp) {this.tipo_comp = tipo_comp;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public OrdenServicioEntity getOrden() {return orden;}
    public void setOrden(OrdenServicioEntity orden) {this.orden = orden;}
}