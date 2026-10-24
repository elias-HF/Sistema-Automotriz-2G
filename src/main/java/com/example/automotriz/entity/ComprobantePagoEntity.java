package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprobante_pago")
public class ComprobantePagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Integer id;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "tipo_comprobante", nullable = false, length = 20)
    private String tipoComprobante;

    @Column(name = "medio_pago", nullable = false, length = 30)
    private String medioPago;

    @Column(name = "estado_pago", nullable = false, length = 20)
    private String estadoPago;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "igv", nullable = false, precision = 10, scale = 2)
    private BigDecimal igv;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_orden", nullable = false, unique = true)
    private OrdenServicioEntity orden;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getTipoComprobante() {return tipoComprobante;}
    public void setTipoComprobante(String tipoComprobante) {this.tipoComprobante = tipoComprobante;}

    public String getMedioPago() {return medioPago;}
    public void setMedioPago(String medioPago) {this.medioPago = medioPago;}

    public String getEstadoPago() {return estadoPago;}
    public void setEstadoPago(String estadoPago) {this.estadoPago = estadoPago;}

    public LocalDateTime getFechaEmision() {return fechaEmision;}
    public void setFechaEmision(LocalDateTime fechaEmision) {this.fechaEmision = fechaEmision;}

    public BigDecimal getSubtotal() {return subtotal;}
    public void setSubtotal(BigDecimal subtotal) {this.subtotal = subtotal;}

    public BigDecimal getIgv() {return igv;}
    public void setIgv(BigDecimal igv) {this.igv = igv;}

    public BigDecimal getTotal() {return total;}
    public void setTotal(BigDecimal total) {this.total = total;}

    public OrdenServicioEntity getOrden() {return orden;}
    public void setOrden(OrdenServicioEntity orden) {this.orden = orden;}
}