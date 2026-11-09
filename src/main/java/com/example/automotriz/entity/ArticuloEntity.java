package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "articulo")
public class ArticuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private Integer idArticulo;

    @Column(name = "nombre", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "precio_por_defecto", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioPorDefecto;

    @Column(name = "stock", precision = 10, scale = 2, nullable = false)
    private BigDecimal stock;

    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    private String codigo;

    @Column(name = "stock_minimo", precision = 10, scale = 2, nullable = false)
    private BigDecimal stockMinimo;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "unidad_medida", length = 5, nullable = false)
    private String unidadMedida;

    public ArticuloEntity() {}

    public Integer getIdArticulo() {return idArticulo;}
    public void setIdArticulo(Integer idArticulo) {this.idArticulo = idArticulo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public BigDecimal getPrecioPorDefecto() {return precioPorDefecto;}
    public void setPrecioPorDefecto(BigDecimal precioPorDefecto) {this.precioPorDefecto = precioPorDefecto;}

    public BigDecimal getStock() {return stock;}
    public void setStock(BigDecimal stock) {this.stock = stock;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public BigDecimal getStockMinimo() {return stockMinimo;}
    public void setStockMinimo(BigDecimal stockMinimo) {this.stockMinimo = stockMinimo;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public String getUnidadMedida() {return unidadMedida;}
    public void setUnidadMedida(String unidadMedida) {this.unidadMedida = unidadMedida;}
}
