package com.example.automotriz.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "fragments/login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String username, @RequestParam String password, @RequestParam String perfil, HttpSession session) {
        // Usuarios ficticios para las pruebas

        System.out.println(">>> LOGIN INTENTO - Usuario: [" + username + "], Password: [" + password + "], Perfil: [" + perfil + "]");

        boolean accesoValido =
                (username.equals("admin") &&
                        password.equals("1234") &&
                        perfil.equals("ADMINISTRADOR"))
                        ||
                        (username.equals("mecanico") &&
                                password.equals("1234") &&
                                perfil.equals("MECANICO"))
                        ||
                        (username.equals("recepcion") &&
                                password.equals("1234") &&
                                perfil.equals("RECEPCION"))
                        ||
                        (username.equals("almacen") &&
                                password.equals("1234") &&
                                perfil.equals("ALMACEN"));

        if (!accesoValido) {return "redirect:/login?error";}
        session.setAttribute("usuario", username);
        session.setAttribute("perfil", perfil);

        return "redirect:/dashboard";
    }

    @GetMapping("/recuperar-password")
    public String mostrarRecuperacion(Model model) {
        model.addAttribute("mensaje", "Contacta con el administrador del sistema.");
        return "/login";
    }

    @GetMapping("/cerrar-sesion")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}