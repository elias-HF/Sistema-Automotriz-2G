package com.example.automotriz.controller;

import com.example.automotriz.entity.CitaEntity;
import com.example.automotriz.entity.OrdenServicioEntity;
import com.example.automotriz.repository.CitaRepository;
import com.example.automotriz.repository.VehiculoRepository;
import com.example.automotriz.service.CitaService;
import com.example.automotriz.service.OrdenesService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/ordenes")
public class OrdenesController {

    private final OrdenesService ordenService;
    private final CitaService citaService;
    private final VehiculoRepository vehiculoRepository;
    private final CitaRepository citaRepository;

    public OrdenesController(
            OrdenesService ordenService,CitaService citaService,
            VehiculoRepository vehiculoRepository, CitaRepository citaRepository) {
        this.ordenService = ordenService;
        this.citaService = citaService;
        this.vehiculoRepository = vehiculoRepository;
        this.citaRepository = citaRepository;
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearOrden(
            @RequestParam(required = false) Integer citaId,
            Model model) {

        if (citaId != null) {
            citaRepository.findById(citaId).ifPresent(cita -> {
            model.addAttribute("citaSeleccionada", cita);
            });
        }

        model.addAttribute("vehiculos", vehiculoRepository.findByActivoTrue());
        model.addAttribute("vista", "ordenes");

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
                        "orden",
                        ordenService.buscarPorCodigo(codigo.trim()));
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

            return "redirect:/ordenes?codigo=" + orden.getCod();

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
            OrdenServicioEntity orden =
                    ordenService.finalizar(idOrden);

            redirectAttributes.addFlashAttribute(
                    "mensajeOrden",
                    "La orden se finalizó correctamente.");

            return "redirect:/ordenes/comprobante?codigo="
                    + orden.getCod();

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorOrden", e.getMessage());

            return "redirect:/ordenes";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarOrden(
            @RequestParam int idOrden,
            @RequestParam String diagnostico,
            @RequestParam BigDecimal montoTotal,
            RedirectAttributes redirectAttributes) {

        try {
            OrdenServicioEntity orden = ordenService.actualizarOrden(idOrden, diagnostico, montoTotal);
            redirectAttributes.addFlashAttribute("mensajeOrden", "¡Los datos de la orden se guardaron correctamente en la base de datos!");
            return "redirect:/ordenes?codigo=" + orden.getCod();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorOrden", e.getMessage());
            return "redirect:/ordenes";
        }
    }

    // Cargar orden finalizada para el comprobante
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

    private void cargarFormulario(Model model) {
        model.addAttribute(
                "vehiculos", vehiculoRepository.findByActivoTrue());
    }
}