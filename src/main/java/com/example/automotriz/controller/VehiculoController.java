package com.example.automotriz.controller;

import com.example.automotriz.model.VehiculoModel;
import com.example.automotriz.service.VehiculoService;
import com.example.automotriz.util.toast;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {
    private static final String REDIRECT = "redirect:/administracion?elemento=vehiculos";
    private static final String MSJ_DUPLICADO = "La placa ya está registrada";

    private final VehiculoService vehiculoService;
    public VehiculoController(VehiculoService vehiculoService) {this.vehiculoService = vehiculoService;}

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute VehiculoModel vehiculoModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            vehiculoService.registrarVehiculo(vehiculoModel);
            toast.exito(redirect, "Vehículo registrado correctamente");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, MSJ_DUPLICADO);
        }
        return REDIRECT;
    }

    @PostMapping("/modificar")
    public String modificar(@Valid @ModelAttribute VehiculoModel vehiculoModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            vehiculoService.modificarVehiculo(vehiculoModel);
            toast.exito(redirect, "Vehículo modificado correctamente");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, MSJ_DUPLICADO);
        }
        return REDIRECT;
    }

    @PostMapping("/desactivar")
    public String desactivar(@RequestParam Integer id, RedirectAttributes redirect) {
        try {
            vehiculoService.desactivarVehiculo(id);
            toast.exito(redirect, "Vehículo desactivado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @PostMapping("/activar")
    public String activar(@RequestParam Integer id, RedirectAttributes redirect) {
        try {
            vehiculoService.activarVehiculo(id);
            toast.exito(redirect, "Vehículo activado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public VehiculoModel buscar(@PathVariable Integer id) {
        return vehiculoService.buscarPorId(id);
    }
}