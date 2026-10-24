package com.example.automotriz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlmacenController {

    @GetMapping("/almacen")
    public String almacen(
            @RequestParam(defaultValue = "") String buscar,
            Model model) {

        model.addAttribute("vista", "almacen");
        model.addAttribute("buscar", buscar);

        model.addAttribute("cantidadArticulos", 0);
        model.addAttribute("stockSuficiente", 0);
        model.addAttribute("stockCritico", 0);

        return "view/Index";
    }
}