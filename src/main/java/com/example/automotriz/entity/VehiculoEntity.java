package com.example.automotriz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehiculo")
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    @Column(name = "anio_fabricacion", nullable = false)
    private Integer anioFabricacion;

    @Column(name = "placa", length = 10, nullable = false)
    private String placa;

    @Column(name = "kilometros", precision = 10, scale = 2, nullable = false)
    private BigDecimal kilometros;

    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    private String codigo;

    @Column(name = "modelo", length = 25, nullable = false)
    private String modelo;

    @Column(name = "marca", length = 25, nullable = false)
    private String marca;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

    public VehiculoEntity() {}

    public Integer getIdVehiculo() {return idVehiculo;}
    public void setIdVehiculo(Integer idVehiculo) {this.idVehiculo = idVehiculo;}

    public Integer getAnioFabricacion() {return anioFabricacion;}
    public void setAnioFabricacion(Integer anioFabricacion) {this.anioFabricacion = anioFabricacion;}

    public String getPlaca() {return placa;}
    public void setPlaca(String placa) {this.placa = placa;}

    public BigDecimal getKilometros() {return kilometros;}
    public void setKilometros(BigDecimal kilometros) {this.kilometros = kilometros;}

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getModelo() {return modelo;}
    public void setModelo(String modelo) {this.modelo = modelo;}

    public String getMarca() {return marca;}
    public void setMarca(String marca) {this.marca = marca;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public ClienteEntity getCliente() {return cliente;}
    public void setCliente(ClienteEntity cliente) {this.cliente = cliente;}
}
