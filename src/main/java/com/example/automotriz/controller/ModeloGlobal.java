package com.example.automotriz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ModeloGlobal {

    @ModelAttribute("esAdmin")
    public boolean esAdmin(Authentication auth) {return tiene(auth, "ADMINISTRADOR");}

    @ModelAttribute("esRecepcion")
    public boolean esRecepcion(Authentication auth) {return tiene(auth, "RECEPCIONISTA");}

    @ModelAttribute("esMecanico")
    public boolean esMecanico(Authentication auth) {return tiene(auth, "MECANICO");}

    @ModelAttribute("esAlmacen")
    public boolean esAlmacen(Authentication auth) {return tiene(auth, "ALMACEN");}

    @ModelAttribute("usuarioActual")
    public String usuarioActual(Authentication auth) {return auth != null ? auth.getName() : null;}

    private static boolean tiene(Authentication auth, String rol) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + rol));
    }
}