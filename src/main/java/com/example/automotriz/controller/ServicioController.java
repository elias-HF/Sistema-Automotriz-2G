package com.example.automotriz.controller;

import com.example.automotriz.model.ServicioModel;
import com.example.automotriz.service.ServicioService;
import com.example.automotriz.util.toast;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicios")
public class ServicioController {
    private static final String REDIRECT = "redirect:/administracion?elemento=servicios";
    private static final String MSJ_DUPLICADO = "Ya existe un servicio con ese nombre";

    private final ServicioService servicioService;
    public ServicioController(ServicioService servicioService) {this.servicioService = servicioService;}

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute ServicioModel servicioModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            servicioService.registrarServicio(servicioModel);
            toast.exito(redirect, "Servicio registrado correctamente");
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, MSJ_DUPLICADO);
        }
        return REDIRECT;
    }

    @PostMapping("/modificar")
    public String modificar(@Valid @ModelAttribute ServicioModel servicioModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            servicioService.modificarServicio(servicioModel);
            toast.exito(redirect, "Servicio modificado correctamente");
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
            servicioService.desactivarServicio(id);
            toast.exito(redirect, "Servicio desactivado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @PostMapping("/activar")
    public String activar(@RequestParam Integer id, RedirectAttributes redirect) {
        try {
            servicioService.activarServicio(id);
            toast.exito(redirect, "Servicio activado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public ServicioModel buscar(@PathVariable Integer id) {
        return servicioService.buscarPorId(id);
    }
}