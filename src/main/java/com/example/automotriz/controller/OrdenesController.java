
package com.example.automotriz.controller;

import com.example.automotriz.entity.OrdenServicioEntity;
import com.example.automotriz.repository.CitaRepository;
import com.example.automotriz.repository.VehiculoRepository;
import com.example.automotriz.service.OrdenesService;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ordenes")
public class OrdenesController {

    private final OrdenesService ordenService;
    private final VehiculoRepository vehiculoRepository;
    private final CitaRepository citaRepository;

    public OrdenesController(
            OrdenesService ordenService,
            VehiculoRepository vehiculoRepository,
            CitaRepository citaRepository) {

        this.ordenService = ordenService;
        this.vehiculoRepository = vehiculoRepository;
        this.citaRepository = citaRepository;
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearOrden(
            @RequestParam(required = false) Integer citaId,
            Model model,
            RedirectAttributes redirectAttributes) {

        model.addAttribute("vista", "ordenes");
        model.addAttribute("vehiculos", vehiculoRepository.findByActivoTrue());

        if (citaId != null) {
            return citaRepository.findById(citaId)
                    .map(cita -> {
                        if (!"PENDIENTE".equalsIgnoreCase(cita.getEstado())) {
                            redirectAttributes.addFlashAttribute(
                                    "errorOrden",
                                    "Solo se pueden convertir citas pendientes en órdenes."
                            );
                            return "redirect:/ordenes";
                        }

                        model.addAttribute("citaSeleccionada", cita);
                        model.addAttribute("diagnosticoInicial", cita.getMotivo());

                        return "view/Index";
                    })
                    .orElseGet(() -> {
                        redirectAttributes.addFlashAttribute(
                                "errorOrden", "No se encontró la cita seleccionada."
                        );
                        return "redirect:/ordenes";
                    });
        }

        return "view/Index";
    }

    // Mostrar módulo y buscar orden por código
    @GetMapping
    public String mostrarOrdenes(
            @RequestParam(required = false) String codigo,
            Model model) {

        cargarFormulario(model);
        model.addAttribute("vista", "ordenes");

        if (codigo != null && !codigo.isBlank()) {
            model.addAttribute("codigoBuscado", codigo.trim());

            try {
                model.addAttribute(
                        "orden", ordenService.buscarPorCodigo(codigo.trim()));
            } catch (IllegalArgumentException e) {
                model.addAttribute("errorOrden", e.getMessage());
            }
        }

        return "view/Index";
    }

    // Crear orden
    @PostMapping("/crear")
    public String crearOrden(
            @RequestParam int idVehiculo,
            @RequestParam String diagnostico,
            @RequestParam BigDecimal montoTotal,
            RedirectAttributes redirectAttributes) {

        try {
            OrdenServicioEntity orden = ordenService.registrar(
                    idVehiculo, diagnostico, montoTotal);

            redirectAttributes.addFlashAttribute(
                    "mensajeOrden", "Orden creada correctamente.");

            redirectAttributes.addAttribute("codigo", orden.getCod());
            return "redirect:/ordenes";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorOrden", e.getMessage());

            return "redirect:/ordenes";
        }
    }

    // Actualizar datos de una orden existente
    @PostMapping("/actualizar")
    public String actualizarOrden(
            @RequestParam int idOrden,
            @RequestParam String diagnostico,
            @RequestParam BigDecimal montoTotal,
            RedirectAttributes redirectAttributes) {

        try {
            OrdenServicioEntity orden = ordenService.actualizarOrden(
                    idOrden, diagnostico, montoTotal);

            redirectAttributes.addFlashAttribute(
                    "mensajeOrden",
                    "Los datos de la orden se guardaron correctamente.");

            redirectAttributes.addAttribute("codigo", orden.getCod());
            return "redirect:/ordenes";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorOrden", e.getMessage());

            return "redirect:/ordenes";
        }
    }

    // Finalizar orden
    @PostMapping("/finalizar")
    public String finalizarOrden(
            @RequestParam int idOrden,
            RedirectAttributes redirectAttributes) {

        try {
            OrdenServicioEntity orden = ordenService.finalizar(idOrden);

            redirectAttributes.addFlashAttribute(
                    "mensajeOrden", "La orden se finalizó correctamente.");

            redirectAttributes.addAttribute("codigo", orden.getCod());
            return "redirect:/ordenes/comprobante";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorOrden", e.getMessage());

            return "redirect:/ordenes";
        }
    }

    // Mostrar los datos de la orden en la vista de comprobantes
    @GetMapping("/comprobante")
    public String mostrarComprobante(
            @RequestParam String codigo,
            Model model) {

        cargarFormulario(model);
        model.addAttribute("vista", "comprobante");
        model.addAttribute("codigoBuscado", codigo);

        try {
            model.addAttribute(
                    "orden", ordenService.buscarPorCodigo(codigo));
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorOrden", e.getMessage());
            model.addAttribute("vista", "ordenes");
        }

        return "view/Index";
    }

    // Cargar datos necesarios para los formularios
    private void cargarFormulario(Model model) {
        model.addAttribute(
                "vehiculos", vehiculoRepository.findByActivoTrue());
    }
}
