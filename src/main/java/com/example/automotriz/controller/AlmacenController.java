package com.example.automotriz.controller;

import com.example.automotriz.service.ArticuloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlmacenController {
    private final ArticuloService articuloService;
    public AlmacenController(ArticuloService articuloService) {this.articuloService = articuloService;}

    @GetMapping("/almacen")
    public String almacen(Model model) {
        model.addAttribute("vista", "almacen");
        model.addAttribute("articulos", articuloService.listarArticulos());
        model.addAttribute("resumen", articuloService.resumen());
        return "view/Index";
    }
}