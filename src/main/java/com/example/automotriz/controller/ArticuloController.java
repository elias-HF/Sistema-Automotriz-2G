package com.example.automotriz.controller;

import com.example.automotriz.model.ArticuloModel;
import com.example.automotriz.service.ArticuloService;
import com.example.automotriz.util.toast;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/articulos")
public class ArticuloController {
    private static final String REDIRECT = "redirect:/almacen";
    private static final String MSJ_DUPLICADO = "Ya existe un artículo con ese nombre";

    private final ArticuloService articuloService;
    public ArticuloController(ArticuloService articuloService) {this.articuloService = articuloService;}

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute ArticuloModel articuloModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            articuloService.registrarArticulo(articuloModel);
            toast.exito(redirect, "Artículo registrado correctamente");
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, MSJ_DUPLICADO);
        }
        return REDIRECT;
    }

    @PostMapping("/modificar")
    public String modificar(@Valid @ModelAttribute ArticuloModel articuloModel,
                            BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList());
            return REDIRECT;
        }
        try {
            articuloService.modificarArticulo(articuloModel);
            toast.exito(redirect, "Artículo modificado correctamente");
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
            articuloService.desactivarArticulo(id);
            toast.exito(redirect, "Artículo desactivado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @PostMapping("/activar")
    public String activar(@RequestParam Integer id, RedirectAttributes redirect) {
        try {
            articuloService.activarArticulo(id);
            toast.exito(redirect, "Artículo activado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
        return REDIRECT;
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public ArticuloModel buscar(@PathVariable Integer id) {
        return articuloService.buscarPorId(id);
    }
}