package com.example.automotriz.controller;

import com.example.automotriz.service.ComprobanteService;
import com.example.automotriz.service.ComprobanteService.ComprobanteVista;
import com.example.automotriz.util.toast;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comprobante")
public class ComprobanteController {
    private static final String REDIRECT = "redirect:/comprobante";

    private final ComprobanteService comprobanteService;

    public ComprobanteController(ComprobanteService comprobanteService) {
        this.comprobanteService = comprobanteService;
    }

    @GetMapping({"", "/"})
    public String comprobante(@RequestParam(required = false) Integer ordenId,
                              Model model, RedirectAttributes redirect) {
        ComprobanteVista comp = null;

        if (ordenId != null) {
            try {
                comp = comprobanteService.obtener(ordenId);
            } catch (IllegalArgumentException e) {
                toast.error(redirect, e.getMessage());
                return REDIRECT;
            }
        }

        model.addAttribute("vista", "comprobante");
        model.addAttribute("comp", comp);
        model.addAttribute("ordenes", comprobanteService.listarOrdenes());
        return "view/Index";
    }

    @PostMapping("/emitir")
    public String emitir(@RequestParam(required = false) Integer ordenId,
                         @RequestParam(required = false) String tipoComprobante,
                         @RequestParam(required = false) String metodoPago,
                         @RequestParam(required = false) String estadoPago,
                         RedirectAttributes redirect) {
        if (ordenId == null) {
            toast.error(redirect, "Selecciona primero una orden finalizada");
            return REDIRECT;
        }
        try {
            String codigo = comprobanteService.emitir(ordenId, tipoComprobante, metodoPago, estadoPago);
            toast.exito(redirect, "Comprobante " + codigo + " emitido correctamente");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, "No se pudo emitir el comprobante. Revisa los datos");
        }
        return REDIRECT + "?ordenId=" + ordenId;
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam(required = false) Integer ordenId,
                            @RequestParam(required = false) String tipoComprobante,
                            @RequestParam(required = false) String metodoPago,
                            @RequestParam(required = false) String estadoPago,
                            RedirectAttributes redirect) {
        if (ordenId == null) {
            toast.error(redirect, "Selecciona primero una orden finalizada");
            return REDIRECT;
        }
        try {
            String codigo = comprobanteService.modificar(ordenId, tipoComprobante, metodoPago, estadoPago);
            toast.exito(redirect, "Comprobante " + codigo + " modificado correctamente");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, "No se pudo modificar el comprobante. Revisa los datos");
        }
        return REDIRECT + "?ordenId=" + ordenId;
    }
}