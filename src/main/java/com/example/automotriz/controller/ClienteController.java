package com.example.automotriz.controller;

import com.example.automotriz.model.ClienteModel;
import com.example.automotriz.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {this.clienteService = clienteService;}

    @GetMapping({"", "/"})
    public String listarClientes(Model model) {
        List<ClienteModel> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "view/Index";
    }

    /*@GetMapping()
    public String listarClientesFiltrados(Model model) {
        return "view/Index";
    }*/

    @PostMapping("/registrar")
    public String registrarClientes (@Valid @ModelAttribute ClienteModel clienteModel, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarClientes());
            model.addAttribute("clienteForm", clienteModel);
            return "view/Index";
        }

        clienteService.registrarCliente(clienteModel);
        return "redirect:/administracion?elemento=clientes";
    }

    @PostMapping("/modificar")
    public String modificarClientes(@Valid @ModelAttribute ClienteModel clienteModel, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarClientes());
            model.addAttribute("clienteForm", clienteModel);
            return "view/Index";
        }

        clienteService.modificarCliente(clienteModel);
        return "redirect:/administracion?elemento=clientes";
    }

    @PostMapping("/desactivar")
    public String desactivarClientes(@RequestParam Integer id){
        clienteService.desactivarCliente(id);
        return "redirect:/administracion?elemento=clientes";
    }

    @PostMapping("/activar")
    public String activarClientes(@RequestParam Integer id){
        clienteService.activarCliente(id);
        return "redirect:/administracion?elemento=clientes";
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public ClienteModel buscarCliente(@PathVariable Integer id) {
        return clienteService.buscarPorId(id);
    }
}
