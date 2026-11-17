package com.example.automotriz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin(Authentication auth) {
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";   // si no es admin, el handler lo manda a su pestaña
        }
        return "fragments/login";
    }

    @GetMapping("/recuperar-password")
    public String mostrarRecuperacion(Model model) {
        model.addAttribute("mensaje", "Contacta con el administrador del sistema.");
        return "fragments/login";
    }
}