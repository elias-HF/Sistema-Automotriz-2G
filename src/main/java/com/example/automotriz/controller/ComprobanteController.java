
package com.example.automotriz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/comprobante")
public class ComprobanteController {

    // Datos de prueba para mostrar órdenes finalizadas.
    private final List<OrdenDemo> ordenesFinalizadas = List.of(
            new OrdenDemo(
                    124L, "MYT-089", "Toyota", "Yaris", "Carlos Manuel",
                    "130.00", "165.84", "250.71", "45.13", "295.84"
            ),
            new OrdenDemo(
                    125L, "BXZ-451", "Nissan", "Sentra", "Ariana Lucia",
                    "180.00", "220.00", "338.98", "61.02", "400.00"
            )
    );

    @GetMapping
    public String mostrarFacturacion(
            @RequestParam(required = false) Long ordenId,
            Model model) {

        model.addAttribute("vista", "comprobante");
        model.addAttribute("ordenesFinalizadas", ordenesFinalizadas);

        OrdenDemo ordenSeleccionada = ordenesFinalizadas.stream()
                .filter(orden -> orden.getId().equals(ordenId))
                .findFirst()
                .orElse(null);

        model.addAttribute("ordenSeleccionada", ordenSeleccionada);

        if (ordenSeleccionada != null) {
            model.addAttribute("costoArticulosTexto",
                    "S/ " + ordenSeleccionada.getCostoArticulos());
            model.addAttribute("costoServiciosTexto",
                    "S/ " + ordenSeleccionada.getCostoServicios());
            model.addAttribute("subtotalTexto",
                    "S/ " + ordenSeleccionada.getSubtotal());
            model.addAttribute("igvTexto",
                    "S/ " + ordenSeleccionada.getIgv());
            model.addAttribute("totalTexto",
                    "S/ " + ordenSeleccionada.getTotal());
        }

        model.addAttribute("fechaHora",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss")
                ));

        return "view/Index";
    }

    @PostMapping("/emitir")
    public String emitirComprobante(
            @RequestParam Long ordenId,
            @RequestParam String tipoComprobante,
            @RequestParam String medioPago,
            @RequestParam String estadoPago,
            RedirectAttributes redirectAttributes) {

        boolean ordenExiste = ordenesFinalizadas.stream()
                .anyMatch(orden -> orden.getId().equals(ordenId));

        if (!ordenExiste) {
            redirectAttributes.addFlashAttribute(
                    "errorComprobante",
                    "La orden seleccionada no existe o no está finalizada."
            );
            return "redirect:/comprobante";
        }

        // Validación inicial de los datos recibidos.
        if (!List.of("BOLETA", "FACTURA").contains(tipoComprobante)
                || !List.of("YAPE_PLIN", "EFECTIVO", "TARJETA",
                "TRANSFERENCIA").contains(medioPago)
                || !List.of("PENDIENTE", "PAGADO", "PARCIAL")
                .contains(estadoPago)) {

            redirectAttributes.addFlashAttribute(
                    "errorComprobante",
                    "Verifica el tipo de comprobante y los datos del pago."
            );
            return "redirect:/comprobante?ordenId=" + ordenId;
        }

        // Temporal: todavía no se guarda en una base de datos.
        redirectAttributes.addFlashAttribute(
                "mensajeComprobante",
                "Comprobante de prueba procesado para la orden #" + ordenId
                        + ". Tipo: " + tipoComprobante
                        + ". Estado de pago: " + estadoPago + "."
        );

        return "redirect:/comprobante?ordenId=" + ordenId;
    }

    // DTO sencillo para los datos ficticios.
    public static class OrdenDemo {
        private final Long id;
        private final VehiculoDemo vehiculo;
        private final ClienteDemo cliente;
        private final String costoArticulos;
        private final String costoServicios;
        private final String subtotal;
        private final String igv;
        private final String total;

        public OrdenDemo(
                Long id, String placa, String marca, String modelo,
                String nombreCliente, String costoArticulos,
                String costoServicios, String subtotal,
                String igv, String total) {

            this.id = id;
            this.vehiculo = new VehiculoDemo(placa, marca, modelo);
            this.cliente = new ClienteDemo(nombreCliente);
            this.costoArticulos = costoArticulos;
            this.costoServicios = costoServicios;
            this.subtotal = subtotal;
            this.igv = igv;
            this.total = total;
        }

        public Long getId() { return id; }
        public VehiculoDemo getVehiculo() { return vehiculo; }
        public ClienteDemo getCliente() { return cliente; }
        public String getCostoArticulos() { return costoArticulos; }
        public String getCostoServicios() { return costoServicios; }
        public String getSubtotal() { return subtotal; }
        public String getIgv() { return igv; }
        public String getTotal() { return total; }
    }

    public static class VehiculoDemo {
        private final String placa;
        private final String marca;
        private final String modelo;

        public VehiculoDemo(String placa, String marca, String modelo) {
            this.placa = placa;
            this.marca = marca;
            this.modelo = modelo;
        }

        public String getPlaca() { return placa; }
        public String getMarca() { return marca; }
        public String getModelo() { return modelo; }
    }

    public static class ClienteDemo {
        private final String nombre;

        public ClienteDemo(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() { return nombre; }
    }
}
