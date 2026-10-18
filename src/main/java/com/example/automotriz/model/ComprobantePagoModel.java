package com.example.automotriz.model;

import java.time.LocalDateTime;

public class ComprobantePagoModel {
    private int idComprobante;
    private LocalDateTime fechaHoraEmision;
    private String metodoPago;
    private String codigo;
    private String tipoComprobante;
    private String estado;
    private int idOrden;

    public ComprobantePagoModel() {}
    public ComprobantePagoModel(int idComprobante, LocalDateTime fechaHoraEmision, String metodoPago, String codigo, String tipoComprobante, String estado, int idOrden) {
        this.idComprobante = idComprobante;
        this.fechaHoraEmision = fechaHoraEmision;
        this.metodoPago = metodoPago;
        this.codigo = codigo;
        this.tipoComprobante = tipoComprobante;
        this.estado = estado;
        this.idOrden = idOrden;
    }

    public int getIdComprobante() {return idComprobante;}
    public void setIdComprobante(int idComprobante) {this.idComprobante = idComprobante;}

    public LocalDateTime getFechaHoraEmision() {return fechaHoraEmision;}
    public void setFechaHoraEmision(LocalDateTime fechaHoraEmision) {this.fechaHoraEmision = fechaHoraEmision;}

    public String getMetodoPago() {return metodoPago;}
    public void setMetodoPago(String metodoPago) {this.metodoPago = metodoPago;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getTipoComprobante() {return tipoComprobante;}
    public void setTipoComprobante(String tipoComprobante) {this.tipoComprobante = tipoComprobante;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public int getIdOrden() {return idOrden;}
    public void setIdOrden(int idOrden) {this.idOrden = idOrden;}
}
