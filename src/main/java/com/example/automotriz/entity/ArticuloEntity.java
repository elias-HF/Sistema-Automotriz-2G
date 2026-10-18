package com.example.automotriz.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table
@Entity(name = "articulo")
public class ArticuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private int id_articulo;

    @Column(name = "nombre", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "stock", precision = 10, scale = 2, nullable = false)
    private BigDecimal stock;

    @Column(name = "cod", length = 15, unique = true, nullable = false)
    private String cod;

    @Column(name = "stock_min", precision = 10, scale = 2, nullable = false)
    private BigDecimal stock_min;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_unidad", nullable = false)
    private UnidadMedidaEntity unidad;
}
