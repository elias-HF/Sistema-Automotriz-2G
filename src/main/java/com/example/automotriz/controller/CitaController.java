package com.example.automotriz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @GetMapping
    public String mostrarGestionCitas(Model model) {
        model.addAttribute("vista", "citas");
        return "view/Index";
    }

    // Endpoint JSON que consume FullCalendar
    @GetMapping("/api/eventos")
    @ResponseBody
    public List<Map<String, Object>> obtenerEventos() {
        List<Map<String, Object>> eventos = new ArrayList<>();

        Map<String, Object> e1 = new HashMap<>();
        e1.put("id", "101");
        e1.put("title", "Toyota Yaris - Carlos Manuel");
        e1.put("start", "2026-09-23T09:00:00");
        e1.put("color", "#f5a623");
        e1.put("extendedProps", Map.of(
                "hora", "09:00 AM",
                "marcaModelo", "Toyota Yaris",
                "placa", "MYT-089",
                "cliente", "Carlos Manuel"
        ));
        eventos.add(e1);

        Map<String, Object> e2 = new HashMap<>();
        e2.put("id", "102");
        e2.put("title", "Nissan Sentra - Ariana Lucia");
        e2.put("start", "2026-09-23T15:30:00");
        e2.put("color", "#164b78");
        e2.put("extendedProps", Map.of(
                "hora", "03:30 PM",
                "marcaModelo", "Nissan Sentra",
                "placa", "BXZ-451",
                "cliente", "Ariana Lucia"
        ));
        eventos.add(e2);

        return eventos;
    }
}