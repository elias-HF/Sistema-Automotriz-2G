package com.example.automotriz.controller;

import com.example.automotriz.model.EmpleadoModel;
import com.example.automotriz.service.EmpleadoService;
import com.example.automotriz.util.toast;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
    private static final String REDIRECT = "redirect:/administracion?elemento=empleados";
    private static final String MSJ_DUPLICADO = "El usuario o el correo electrónico ya están registrados";

    private final EmpleadoService empleadoService;
    public EmpleadoController(EmpleadoService empleadoService) {this.empleadoService = empleadoService;}

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute EmpleadoModel empleadoModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            empleadoService.registrarEmpleado(empleadoModel);
            toast.exito(redirect, "Empleado registrado correctamente");
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, MSJ_DUPLICADO);
        }
        return REDIRECT;
    }

    @PostMapping("/modificar")
    public String modificar(@Valid @ModelAttribute EmpleadoModel empleadoModel,
                            BindingResult result, RedirectAttributes redirect) {
        boolean sinPassword = empleadoModel.getContrasenia() == null
                || empleadoModel.getContrasenia().isBlank();

        // Al modificar, la contraseña vacía es válida (significa "no cambiarla")
        var errores = result.getFieldErrors().stream()
                .filter(e -> !(e.getField().equals("contrasenia") && sinPassword))
                .map(FieldError::getDefaultMessage)
                .toList();

        if (!errores.isEmpty()) {
            toast.error(redirect, errores);
            return REDIRECT;
        }
        try {
            empleadoService.modificarEmpleado(empleadoModel);
            toast.exito(redirect, "Empleado modificado correctamente");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, MSJ_DUPLICADO);
        }
        return REDIRECT;
    }

    @PostMapping("/desactivar")
    public String desactivar(@RequestParam Integer id, Principal principal,
                             RedirectAttributes redirect) {
        try {
            empleadoService.desactivarEmpleado(id, principal.getName());
            toast.exito(redirect, "Empleado desactivado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @PostMapping("/activar")
    public String activar(@RequestParam Integer id, RedirectAttributes redirect) {
        try {
            empleadoService.activarEmpleado(id);
            toast.exito(redirect, "Empleado activado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public EmpleadoModel buscar(@PathVariable Integer id) {
        EmpleadoModel model = empleadoService.buscarPorId(id);
        model.setContrasenia(null); // nunca enviar el hash al navegador
        return model;
    }
}