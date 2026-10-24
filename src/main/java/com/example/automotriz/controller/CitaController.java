package com.example.automotriz.controller;

import com.example.automotriz.entity.CitaEntity;
import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.entity.MedioContactoEntity;
import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.repository.ClienteRepository;
import com.example.automotriz.repository.MedioContactoRepository;
import com.example.automotriz.repository.VehiculoRepository;
import com.example.automotriz.service.CitaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/citas")
public class CitaController {
    private final CitaService citaService;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final MedioContactoRepository medioContactoRepository;

    public CitaController(CitaService citaService, ClienteRepository clienteRepository, VehiculoRepository vehiculoRepository, MedioContactoRepository medioContactoRepository) {
        this.citaService = citaService;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.medioContactoRepository = medioContactoRepository;
    }

    @GetMapping
    public String mostrarGestionCitas(Model model) {
        model.addAttribute("vista", "citas");
        model.addAttribute("totalCitas", citaService.listarTodas().size());
        model.addAttribute("clientes", clienteRepository.findByActivoTrue());
        model.addAttribute("mediosContacto", medioContactoRepository.findAll());

        return "view/Index";
    }

    @GetMapping("/api/eventos")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerEventos() {
        List<Map<String, Object>> eventos = new ArrayList<>();

        for (CitaEntity cita : citaService.listarTodas()) {
            Map<String, Object> evento = new HashMap<>();

            String nombreCliente = cita.getCliente().getNombre();
            String modelo = cita.getVehiculo().getModelo().getNombre();
            String placa = cita.getVehiculo().getPlaca();

            LocalDateTime fechaHora = LocalDateTime.of(cita.getFecha(), cita.getHora());

            evento.put("id", cita.getId());
            evento.put("title", modelo + " - " + nombreCliente);
            evento.put("start", fechaHora.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            evento.put("color", colorPorEstado(cita.getEstado()));

            Map<String, Object> datos = new HashMap<>();
            datos.put("idCita", cita.getId());
            datos.put("codigo", cita.getCod());
            datos.put("hora", cita.getHora().toString());
            datos.put("marcaModelo", modelo);
            datos.put("placa", placa);
            datos.put("cliente", nombreCliente);
            datos.put("estado", cita.getEstado());
            datos.put("motivo", cita.getMotivo());

            evento.put("extendedProps", datos);
            eventos.add(evento);
        }

        return eventos;
    }

    @GetMapping("/api/vehiculos")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerVehiculos(@RequestParam int clienteId) {
        List<VehiculoEntity> vehiculos = vehiculoRepository.findByCliente_IdClienteAndActivoTrue(clienteId);
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (VehiculoEntity vehiculo : vehiculos) {
            Map<String, Object> datos = new HashMap<>();

            datos.put("id", vehiculo.getId_vehiculo());
            datos.put("placa", vehiculo.getPlaca());
            datos.put("modelo", vehiculo.getModelo().getNombre());

            resultado.add(datos);
        }

        return resultado;
    }

    @PostMapping("/guardar")
    public String guardarCita(@RequestParam java.time.LocalDate fecha, @RequestParam java.time.LocalTime hora, @RequestParam String motivo, @RequestParam int idCliente, @RequestParam int idContacto, @RequestParam int idVehiculo, RedirectAttributes redirectAttributes) {
        try {
            citaService.registrar(fecha, hora, motivo, idCliente, idContacto, idVehiculo);
            redirectAttributes.addFlashAttribute("mensajeExito", "La cita se registró correctamente.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("mensajeError", ex.getMessage());
        }

        return "redirect:/citas";
    }

    @PostMapping("/{id}/cancelar")
    public String cancelarCita(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            citaService.cancelar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "La cita se canceló correctamente.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("mensajeError", ex.getMessage());
        }

        return "redirect:/citas";
    }

    private String colorPorEstado(String estado) {
        return switch (estado) {
            case "PENDIENTE" -> "#f5a623";
            case "CONFIRMADA" -> "#1769aa";
            case "CANCELADA" -> "#dc3545";
            case "ATENDIDA" -> "#198754";
            default -> "#6c757d";
        };
    }
}