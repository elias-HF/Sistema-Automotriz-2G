package com.example.automotriz.service;

import com.example.automotriz.entity.ComprobantePagoEntity;
import com.example.automotriz.entity.OrdenServicioEntity;
import com.example.automotriz.repository.ComprobantePagoRepository;
import com.example.automotriz.repository.OrdenRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;

@Service
public class ComprobanteService {

    private final OrdenRepository ordenRepository;
    private final ComprobantePagoRepository comprobanteRepository;

    private static final Set<String> TIPOS =
            Set.of("BOLETA", "FACTURA");

    private static final Set<String> MEDIOS_PAGO =
            Set.of("YAPE_PLIN", "EFECTIVO",
                    "TARJETA", "TRANSFERENCIA");

    private static final Set<String> ESTADOS_PAGO =
            Set.of("PENDIENTE", "PAGADO", "PARCIAL");

    public ComprobanteService(
            OrdenRepository ordenRepository,
            ComprobantePagoRepository comprobanteRepository) {
        this.ordenRepository = ordenRepository;
        this.comprobanteRepository = comprobanteRepository;
    }

    @Transactional
    public ComprobantePagoEntity emitir(
            Integer ordenId,
            String tipoComprobante,
            String medioPago,
            String estadoPago) {

        if (ordenId == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar una orden.");
        }

        OrdenServicioEntity orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La orden seleccionada no existe."));

        if (!"FINALIZADA".equals(orden.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se pueden emitir comprobantes de órdenes finalizadas.");
        }

        if (tipoComprobante == null ||
                !TIPOS.contains(tipoComprobante)) {
            throw new IllegalArgumentException(
                    "El tipo de comprobante no es válido.");
        }

        if (medioPago == null ||
                !MEDIOS_PAGO.contains(medioPago)) {
            throw new IllegalArgumentException(
                    "El medio de pago no es válido.");
        }

        if (estadoPago == null ||
                !ESTADOS_PAGO.contains(estadoPago)) {
            throw new IllegalArgumentException(
                    "El estado de pago no es válido.");
        }

        if (comprobanteRepository.existsByOrden(orden)) {
            throw new IllegalArgumentException(
                    "Esta orden ya tiene un comprobante registrado.");
        }

        BigDecimal subtotal = orden.getMonto_total();

        if (subtotal == null || subtotal.signum() < 0) {
            throw new IllegalArgumentException(
                    "El monto de la orden no es válido.");
        }

        // Cálculo de importes
        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);

        BigDecimal igv = subtotal
                .multiply(new BigDecimal("0.18"))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = subtotal.add(igv)
                .setScale(2, RoundingMode.HALF_UP);

        // Registro del comprobante
        ComprobantePagoEntity comprobante =
                new ComprobantePagoEntity();

        comprobante.setOrden(orden);
        comprobante.setTipoComprobante(tipoComprobante);
        comprobante.setMedioPago(medioPago);
        comprobante.setEstadoPago(estadoPago);
        comprobante.setFechaEmision(LocalDateTime.now());
        comprobante.setSubtotal(subtotal);
        comprobante.setIgv(igv);
        comprobante.setTotal(total);

        comprobante.setCodigo(String.format(
                Locale.ROOT, "CMP-%05d", orden.getId()));

        return comprobanteRepository.save(comprobante);
    }
}

