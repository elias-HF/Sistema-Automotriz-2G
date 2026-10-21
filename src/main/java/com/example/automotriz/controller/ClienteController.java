package com.example.automotriz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.automotriz.model.ClienteModel;
import com.example.automotriz.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "clientes/lista";
    }

    // Mostrar el formulario para el registro de clientes.
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new ClienteModel());
        return "clientes/formulario";
    }

    // Mostrar el formulario para la edición de clientes.
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        ClienteModel cliente = service.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    @PostMapping("/guardar")
    public String registrar(@ModelAttribute("cliente") ClienteModel cliente) {
        service.registrar(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/modificar")
    public String modificar(@ModelAttribute("cliente") ClienteModel cliente) {
        service.modificar(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/desactivar/{id}")
    public String desactivar(@PathVariable int id) {
        service.desactivar(id);
        return "redirect:/clientes";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(defaultValue = "") String filtro, Model model) {
        String texto = filtro == null ? "" : filtro.trim();
        model.addAttribute("clientes", service.listarFiltrado(texto));
        model.addAttribute("filtro", texto);
        return "clientes/lista";
    }
}