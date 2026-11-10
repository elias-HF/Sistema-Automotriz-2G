package com.example.automotriz.controller;

import com.example.automotriz.model.CitaModel;
import com.example.automotriz.model.VehiculoModel;
import com.example.automotriz.service.CitaService;
import com.example.automotriz.service.ClienteService;
import com.example.automotriz.service.VehiculoService;
import com.example.automotriz.util.toast;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaController {
    private static final String REDIRECT = "redirect:/citas";

    private final CitaService citaService;
    private final ClienteService clienteService;
    private final VehiculoService vehiculoService;

    public CitaController(CitaService citaService,
                          ClienteService clienteService,
                          VehiculoService vehiculoService) {
        this.citaService = citaService;
        this.clienteService = clienteService;
        this.vehiculoService = vehiculoService;
    }

    @GetMapping({"", "/"})
    public String citas(@RequestParam(required = false)
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                        @RequestParam(required = false) Integer citaId,
                        Model model) {
        model.addAttribute("vista", "citas");
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("fechaSeleccionada", fecha);
        model.addAttribute("citaSeleccionada", citaId);
        return "view/Index";
    }

    // ---------- Datos para el calendario ----------

    @GetMapping("/api/listar")
    @ResponseBody
    public List<CitaModel> listar(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return citaService.listarPorRango(desde, hasta);
    }

    @GetMapping("/api/vehiculos")
    @ResponseBody
    public List<VehiculoModel> vehiculosDelCliente(@RequestParam Integer clienteId) {
        return vehiculoService.listarActivosPorCliente(clienteId);
    }

    // ---------- Acciones ----------

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute CitaModel citaModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            CitaModel guardada = citaService.registrarCita(citaModel);
            toast.exito(redirect, "Cita " + guardada.getCodigo() + " agendada correctamente");
            return irACita(guardada);
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, "No se pudo guardar la cita");
        }
        return REDIRECT;
    }

    @PostMapping("/modificar")
    public String modificar(@Valid @ModelAttribute CitaModel citaModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            CitaModel guardada = citaService.modificarCita(citaModel);
            toast.exito(redirect, "Cita " + guardada.getCodigo() + " modificada correctamente");
            return irACita(guardada);
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, "No se pudo guardar la cita");
        }
        return REDIRECT;
    }

    @PostMapping("/cancelar")
    public String cancelar(@RequestParam Integer id, RedirectAttributes redirect) {
        try {
            CitaModel cancelada = citaService.cancelarCita(id);
            toast.exito(redirect, "Cita " + cancelada.getCodigo() + " cancelada");
            return irACita(cancelada);
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    // Vuelve al calendario en el día de la cita y con su detalle abierto
    private String irACita(CitaModel cita) {
        return REDIRECT + "?fecha=" + cita.getFecha() + "&citaId=" + cita.getIdCita();
    }
}
