package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprobante")
public class ComprobanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Integer idComprobante;

    @Column(name = "codigo", length = 10, nullable = false, unique = true)
    private String codigo;

    @Column(name = "tipo_comprobante", length = 25, nullable = false)
    private String tipoComprobante;

    @Column(name = "metodo_pago", length = 25, nullable = false)
    private String metodoPago;

    @Column(name = "estado_pago", length = 25, nullable = false)
    private String estadoPago;

    @Column(name = "fecha_hora_emision", nullable = false)
    private LocalDateTime fechaHoraEmision;

    @Column(name = "pago", precision = 10, scale = 2, nullable = false)
    private BigDecimal pago;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_orden", nullable = false, unique = true)
    private OrdenEntity orden;

    public ComprobanteEntity() {}

    public Integer getIdComprobante() {return idComprobante;}
    public void setIdComprobante(Integer idComprobante) {this.idComprobante = idComprobante;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getTipoComprobante() {return tipoComprobante;}
    public void setTipoComprobante(String tipoComprobante) {this.tipoComprobante = tipoComprobante;}

    public String getMetodoPago() {return metodoPago;}
    public void setMetodoPago(String metodoPago) {this.metodoPago = metodoPago;}

    public String getEstadoPago() {return estadoPago;}
    public void setEstadoPago(String estadoPago) {this.estadoPago = estadoPago;}

    public LocalDateTime getFechaHoraEmision() {return fechaHoraEmision;}
    public void setFechaHoraEmision(LocalDateTime fechaHoraEmision) {this.fechaHoraEmision = fechaHoraEmision;}

    public BigDecimal getPago() {return pago;}
    public void setPago(BigDecimal pago) {this.pago = pago;}

    public BigDecimal getTotal() {return total;}
    public void setTotal(BigDecimal total) {this.total = total;}

    public OrdenEntity getOrden() {return orden;}
    public void setOrden(OrdenEntity orden) {this.orden = orden;}
}
