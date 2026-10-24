package com.example.automotriz.controller;

import com.example.automotriz.entity.ComprobantePagoEntity;
import com.example.automotriz.entity.OrdenServicioEntity;
import com.example.automotriz.repository.ComprobantePagoRepository;
import com.example.automotriz.repository.OrdenRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/comprobante")
public class ComprobanteController {

    private final OrdenRepository ordenRepository;
    private final ComprobantePagoRepository comprobanteRepository;

    public ComprobanteController(
            OrdenRepository ordenRepository,
            ComprobantePagoRepository comprobantePagoRepository) {
        this.ordenRepository = ordenRepository;
        this.comprobanteRepository = comprobantePagoRepository;
    }

    @GetMapping
    public String mostrarComprobante(
            @RequestParam(required = false) Integer ordenId,
            Model model) {

        cargarOrdenesFinalizadas(model);
        model.addAttribute("vista", "comprobante");

        if (ordenId != null) {
            OrdenServicioEntity orden = ordenRepository.findById(ordenId)
                    .orElse(null);

            if (orden == null || !"FINALIZADA".equals(orden.getEstado())) {
                model.addAttribute(
                        "errorComprobante",
                        "La orden no existe o no está finalizada."
                );
            } else {
                model.addAttribute("ordenSeleccionada", orden);

                comprobanteRepository.findByOrden(orden)
                        .ifPresent(comprobante ->
                                model.addAttribute("comprobanteExistente", comprobante));

                cargarImportes(model, orden.getMonto_total());
            }
        }

        return "view/Index";
    }

    @PostMapping("/emitir")
    public String emitirComprobante(
            @RequestParam Integer ordenId,
            @RequestParam String tipoComprobante,
            @RequestParam String medioPago,
            @RequestParam String estadoPago,
            RedirectAttributes redirectAttributes) {

        OrdenServicioEntity orden = ordenRepository.findById(ordenId)
                .orElse(null);

        if (orden == null || !"FINALIZADA".equals(orden.getEstado())) {
            redirectAttributes.addFlashAttribute(
                    "errorComprobante",
                    "Solo se pueden emitir comprobantes de órdenes finalizadas."
            );
            return "redirect:/comprobante";
        }

        if (!tipoComprobante.equals("BOLETA")
                && !tipoComprobante.equals("FACTURA")) {
            redirectAttributes.addFlashAttribute(
                    "errorComprobante", "El tipo de comprobante no es válido."
            );
            return "redirect:/comprobante?ordenId=" + ordenId;
        }

        if (!List.of("YAPE_PLIN", "EFECTIVO", "TARJETA", "TRANSFERENCIA")
                .contains(medioPago)) {
            redirectAttributes.addFlashAttribute(
                    "errorComprobante", "El medio de pago no es válido."
            );
            return "redirect:/comprobante?ordenId=" + ordenId;
        }

        if (!List.of("PENDIENTE", "PAGADO", "PARCIAL").contains(estadoPago)) {
            redirectAttributes.addFlashAttribute(
                    "errorComprobante", "El estado de pago no es válido."
            );
            return "redirect:/comprobante?ordenId=" + ordenId;
        }

        if (comprobanteRepository.existsByOrden(orden)) {
            redirectAttributes.addFlashAttribute(
                    "errorComprobante",
                    "Esta orden ya tiene un comprobante registrado."
            );
            return "redirect:/comprobante?ordenId=" + ordenId;
        }

        BigDecimal subtotal = orden.getMonto_total();

        if (subtotal == null || subtotal.signum() < 0) {
            redirectAttributes.addFlashAttribute(
                    "errorComprobante", "El monto de la orden no es válido."
            );
            return "redirect:/comprobante?ordenId=" + ordenId;
        }

        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);

        BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = subtotal.add(igv)
                .setScale(2, RoundingMode.HALF_UP);

        ComprobantePagoEntity comprobante = new ComprobantePagoEntity();
        comprobante.setOrden(orden);
        comprobante.setTipoComprobante(tipoComprobante);
        comprobante.setMedioPago(medioPago);
        comprobante.setEstadoPago(estadoPago);
        comprobante.setFechaEmision(LocalDateTime.now());
        comprobante.setSubtotal(subtotal);
        comprobante.setIgv(igv);
        comprobante.setTotal(total);

        // El ID de la orden permite generar un código identificable.
        comprobante.setCodigo(String.format(
                Locale.ROOT, "CMP-%05d", orden.getId()
        ));

        comprobanteRepository.save(comprobante);

        redirectAttributes.addFlashAttribute(
                "mensajeComprobante",
                "Comprobante " + comprobante.getCodigo()
                        + " emitido correctamente."
        );

        return "redirect:/comprobante?ordenId=" + ordenId;
    }

    private void cargarOrdenesFinalizadas(Model model) {
        List<OrdenServicioEntity> finalizadas = ordenRepository.findAll()
                .stream()
                .filter(orden -> "FINALIZADA".equals(orden.getEstado()))
                .toList();

        model.addAttribute("ordenesFinalizadas", finalizadas);
    }

    private void cargarImportes(Model model, BigDecimal subtotalOriginal) {
        if (subtotalOriginal == null || subtotalOriginal.signum() < 0) {
            return;
        }

        BigDecimal subtotal = subtotalOriginal.setScale(2, RoundingMode.HALF_UP);
        BigDecimal igv = subtotal.multiply(new BigDecimal("0.18"))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(igv)
                .setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("subtotalTexto", "S/ " + subtotal);
        model.addAttribute("igvTexto", "S/ " + igv);
        model.addAttribute("totalTexto", "S/ " + total);
        model.addAttribute("fechaHora", LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
