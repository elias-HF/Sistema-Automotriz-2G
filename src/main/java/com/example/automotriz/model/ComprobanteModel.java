package com.example.automotriz.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public class ComprobanteModel {
    private Integer idComprobante;
    private String codigo;

    @NotBlank(message = "El tipo de comprobante es obligatorio")
    private String tipoComprobante;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @NotBlank(message = "El estado del pago es obligatorio")
    private String estadoPago;

    @NotNull(message = "La fecha y hora de emisión son obligatorias")
    private LocalDateTime fechaHoraEmision;

    @NotNull(message = "El pago es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El valor mínimo es S/ 0.00")
    @Digits(integer = 8, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal pago;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El valor mínimo es S/ 0.00")
    @Digits(integer = 10, fraction = 2, message = "Máximo 8 enteros y 2 decimales")
    private BigDecimal total;

    private Integer idOrden;

    public ComprobanteModel() {}
    public ComprobanteModel(Integer idComprobante, String codigo, String tipoComprobante, String metodoPago, String estadoPago, LocalDateTime fechaHoraEmision, BigDecimal pago, BigDecimal total, Integer id_orden) {
        this.idComprobante = idComprobante;
        this.codigo = codigo;
        this.tipoComprobante = tipoComprobante;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
        this.fechaHoraEmision = fechaHoraEmision;
        this.pago = pago;
        this.total = total;
        this.idOrden = id_orden;
    }

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

    public Integer getIdOrden() {return idOrden;}
    public void setIdOrden(Integer idOrden) {this.idOrden = idOrden;}
}
