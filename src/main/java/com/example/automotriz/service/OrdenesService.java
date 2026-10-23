package com.example.automotriz.service;

import com.example.automotriz.entity.OrdenServicioEntity;
import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.repository.OrdenRepository;
import com.example.automotriz.repository.VehiculoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class OrdenesService {

    private final OrdenRepository ordenRepository;
    private final VehiculoRepository vehiculoRepository;

    public OrdenesService(OrdenRepository ordenRepository, VehiculoRepository vehiculoRepository) {
        this.ordenRepository = ordenRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional(readOnly = true)
    public List<OrdenServicioEntity> listarTodas() {
        return ordenRepository.findAll();
    }

    @Transactional(readOnly = true)
    public OrdenServicioEntity buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "Ingresa el código de la orden.");
        }

        return ordenRepository.findByCod(codigo.trim())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la orden: " + codigo));
    }

    @Transactional
    public OrdenServicioEntity registrar(
            int idVehiculo,
            String diagnostico,
            BigDecimal montoTotal) {

        if (diagnostico == null || diagnostico.isBlank()) {
            throw new IllegalArgumentException(
                    "El diagnóstico es obligatorio.");
        }

        if (diagnostico.trim().length() > 200) {
            throw new IllegalArgumentException(
                    "El diagnóstico no puede superar los 200 caracteres.");
        }

        if (montoTotal == null || montoTotal.signum() < 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser igual o mayor a cero.");
        }

        VehiculoEntity vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el vehículo seleccionado."));

        if (!vehiculo.isActivo()) {
            throw new IllegalArgumentException(
                    "No se puede crear una orden para un vehículo inactivo.");
        }

        OrdenServicioEntity orden = new OrdenServicioEntity();

        orden.setVehiculo(vehiculo);
        orden.setFechaHora_ingreso(LocalDateTime.now());
        orden.setFechaHora_salida(null);
        orden.setDiagnostico(diagnostico.trim());
        orden.setMonto_total(montoTotal);
        orden.setEstado("PENDIENTE");

        // Código temporal para poder guardar y obtener el ID generado.
        orden.setCod("TMP-" +
                UUID.randomUUID().toString().substring(0, 8));

        OrdenServicioEntity guardada =
                ordenRepository.saveAndFlush(orden);

        String codigo = String.format(
                Locale.ROOT, "ORD-%05d", guardada.getId_orden());

        if (ordenRepository.existsByCod(codigo)) {
            throw new IllegalStateException(
                    "El código generado ya está registrado: " + codigo);
        }

        guardada.setCod(codigo);

        return ordenRepository.save(guardada);
    }

    @Transactional
    public OrdenServicioEntity finalizar(int idOrden) {
        OrdenServicioEntity orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la orden solicitada."));

        if ("CANCELADA".equals(orden.getEstado())) {
            throw new IllegalArgumentException(
                    "No se puede finalizar una orden cancelada.");
        }

        if (!"FINALIZADA".equals(orden.getEstado())) {
            orden.setEstado("FINALIZADA");
            orden.setFechaHora_salida(LocalDateTime.now());

            orden = ordenRepository.save(orden);
        }

        return orden;
    }
}