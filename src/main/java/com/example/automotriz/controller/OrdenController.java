package com.example.automotriz.controller;

import com.example.automotriz.service.OrdenService;
import com.example.automotriz.service.OrdenService.OrdenGuardada;
import com.example.automotriz.service.OrdenService.OrdenVista;
import com.example.automotriz.util.toast;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/ordenes")
public class OrdenController {
    private static final String REDIRECT = "redirect:/ordenes";

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping({"", "/"})
    public String ordenes(@RequestParam(required = false) Integer id,
                          Model model, RedirectAttributes redirect) {
        OrdenVista orden = null;

        if (id != null) {
            try {
                orden = ordenService.obtener(id);
            } catch (IllegalArgumentException e) {
                toast.error(redirect, e.getMessage());
                return REDIRECT;
            }
        }

        model.addAttribute("vista", "ordenes");
        model.addAttribute("orden", orden);
        model.addAttribute("ordenes", ordenService.listarDisponibles());
        model.addAttribute("citas", ordenService.listarCitasPendientes());
        model.addAttribute("servicios", ordenService.listarServiciosActivos());
        model.addAttribute("articulos", ordenService.listarArticulosActivos());
        return "view/Index";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Integer idOrden,
                          @RequestParam(required = false) Integer idCita,
                          @RequestParam(required = false) String diagnostico,
                          @RequestParam(required = false) List<Integer> servicioIds,
                          @RequestParam(required = false) List<Integer> articuloIds,
                          @RequestParam(required = false) List<BigDecimal> cantidades,
                          Principal principal, RedirectAttributes redirect) {
        try {
            OrdenGuardada g = ordenService.guardar(idOrden, idCita, diagnostico,
                    servicioIds, articuloIds, cantidades, principal.getName());

            if (g.nueva()) {
                toast.exito(redirect, "Orden " + g.codigo() + " finalizada y guardada correctamente");
                return REDIRECT;
            }
            toast.exito(redirect, "Orden " + g.codigo() + " actualizada correctamente");
            return REDIRECT + "?id=" + g.idOrden();

        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, "No se pudo guardar la orden. Revisa los datos ingresados");
        }
        return idOrden != null ? REDIRECT + "?id=" + idOrden : REDIRECT;
    }
}