package com.example.automotriz.util;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

public final class toast {
    private toast() {}

    public static void exito(RedirectAttributes r, String mensaje) {
        enviar(r, "success", List.of(mensaje));
    }
    public static void error(RedirectAttributes r, List<String> mensajes) {
        enviar(r, "danger", mensajes);
    }
    public static void error(RedirectAttributes r, String mensaje) {
        enviar(r, "danger", List.of(mensaje));
    }
    private static void enviar(RedirectAttributes r, String tipo, List<String> mensajes) {
        r.addFlashAttribute("toastTipo", tipo);
        r.addFlashAttribute("toastMensajes", mensajes);
    }
}