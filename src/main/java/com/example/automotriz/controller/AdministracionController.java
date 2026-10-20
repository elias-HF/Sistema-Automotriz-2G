package com.example.automotriz.controller;

import com.example.automotriz.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministracionController {
    private final ClienteService service;
    public AdministracionController(ClienteService service) {this.service = service;}

    @GetMapping("/administracion")
    public String administracion(@RequestParam(defaultValue = "clientes") String elemento, @RequestParam(defaultValue = "") String filtro, Model model) {
        if (!elemento.equals("clientes")
                && !elemento.equals("vehiculos")
                && !elemento.equals("empleados")
                && !elemento.equals("servicios")
                && !elemento.equals("comprobantes")
                && !elemento.equals("categorias")) {
            elemento = "clientes";
        }

        model.addAttribute("elemento", elemento);
        if (elemento.equals("clientes")) {model.addAttribute("clientes", service.listarFiltrado(filtro));}

        model.addAttribute("filtro", filtro);
        return "view/AdministracionView";
    }
}