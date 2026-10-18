
package com.example.automotriz.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehiculo")
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private int id_vehiculo;

    @Column(name = "placa", length = 10, nullable = false, unique = true)
    private String placa;

    @Column(name = "año_fabricacion", nullable = false)
    private int año_fabricacion;

    @Column(name = "kilometros", nullable = false, precision = 10, scale = 2)
    private BigDecimal kilometros;

    @Column(name = "cod", unique = true, length = 15, nullable = false)
    private int cod;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_modelo", nullable = false)
    private ModeloEntity modelo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

    public VehiculoEntity() {}

    public int getId_vehiculo() {return id_vehiculo;}
    public void setId_vehiculo(int id_vehiculo) {this.id_vehiculo = id_vehiculo;}

    public String getPlaca() {return placa;}
    public void setPlaca(String placa) {this.placa = placa;}

    public int getAño_fabricacion() {return año_fabricacion;}
    public void setAño_fabricacion(int año_fabricacion) {this.año_fabricacion = año_fabricacion;}

    public BigDecimal getKilometros() {return kilometros;}
    public void setKilometros(BigDecimal kilometros) {this.kilometros = kilometros;}

    public int getCod() {return cod;}
    public void setCod(int cod) {this.cod = cod;}

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public ModeloEntity getModelo() {return modelo;}
    public void setModelo(ModeloEntity modelo) {this.modelo = modelo;}

    public ClienteEntity getCliente() {return cliente;}
    public void setCliente(ClienteEntity cliente) {this.cliente = cliente;}
}