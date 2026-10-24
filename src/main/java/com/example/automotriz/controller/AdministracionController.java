
package com.example.automotriz.controller;

import com.example.automotriz.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministracionController {

    private final ClienteService service;

    public AdministracionController(ClienteService service) {
        this.service = service;
    }

    @GetMapping("/administracion")
    public String administracion(
            @RequestParam(defaultValue = "clientes") String elemento,
            @RequestParam(defaultValue = "") String filtro,
            Model model) {

        if (!elemento.equals("clientes")
                && !elemento.equals("vehiculos")
                && !elemento.equals("empleados")
                && !elemento.equals("servicios")
                && !elemento.equals("articulos")
                && !elemento.equals("categorias")) {

            elemento = "clientes";
        }

        model.addAttribute("vista", "administracion");
        model.addAttribute("elemento", elemento);
        model.addAttribute("filtro", filtro);

        // Datos de clientes
        model.addAttribute("clientes", service.listarFiltrado(filtro));

        // Cantidades provisionales para las tarjetas
        model.addAttribute("cantidadClientes", 0);
        model.addAttribute("cantidadEmpleados", 0);
        model.addAttribute("cantidadVehiculos", 0);
        model.addAttribute("cantidadServicios", 0);
        model.addAttribute("cantidadArticulos", 0);
        model.addAttribute("cantidadCategorias", 0);

        return "view/Index";
    }
}
