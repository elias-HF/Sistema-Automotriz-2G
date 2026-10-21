package com.example.automotriz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ordenes")
public class OrdenesController {

    @GetMapping
    public String mostrarOrdenes(Model model) {
        model.addAttribute("vista", "ordenes");

        return "view/Index";
    }
}
