package com.example.automotriz.controller;

import com.example.automotriz.model.ClienteModel;
import com.example.automotriz.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.automotriz.util.toast;
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
    public String registrarClientes(@Valid @ModelAttribute ClienteModel clienteModel,
                                    BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage()).toList());
            return "redirect:/administracion?elemento=clientes";
        }
        try {
            clienteService.registrarCliente(clienteModel);
            toast.exito(redirect, "Cliente registrado correctamente");
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, "El DNI o el correo ya están registrados");
        }
        return "redirect:/administracion?elemento=clientes";
    }

    @PostMapping("/modificar")
    public String modificarClientes(@Valid @ModelAttribute ClienteModel clienteModel,
                                    BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            toast.error(redirect, result.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage()).toList());
            return "redirect:/administracion?elemento=clientes";
        }
        try {
            clienteService.modificarCliente(clienteModel);
            toast.exito(redirect, "Cliente modificado correctamente");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            toast.error(redirect, "El DNI o el correo ya están registrados");
        }
        return "redirect:/administracion?elemento=clientes";
    }

    @PostMapping("/desactivar")
    public String desactivarClientes(@RequestParam Integer id, RedirectAttributes redirect) {
        try {
            clienteService.desactivarCliente(id);
            toast.exito(redirect, "Cliente desactivado");
        } catch (IllegalArgumentException e) {
            toast.error(redirect, e.getMessage());
        }
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
