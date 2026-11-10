package com.example.automotriz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "fragments/login";
    }

    @GetMapping("/recuperar-password")
    public String mostrarRecuperacion(Model model) {
        model.addAttribute("mensaje", "Contacta con el administrador del sistema.");
        return "fragments/login";
    }
}