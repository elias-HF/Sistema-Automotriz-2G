package com.example.automotriz.controller;

import com.example.automotriz.model.ClienteModel;
import com.example.automotriz.service.ClienteService;
import com.example.automotriz.service.EmpleadoService;
import com.example.automotriz.service.ServicioService;
import com.example.automotriz.service.VehiculoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdministracionController {

    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final VehiculoService vehiculoService;
    private final ServicioService servicioService;

    public AdministracionController(ClienteService clienteService,
                                    EmpleadoService empleadoService,
                                    VehiculoService vehiculoService,
                                    ServicioService servicioService) {
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.vehiculoService = vehiculoService;
        this.servicioService = servicioService;
    }

    @GetMapping("/administracion")
    public String administracion(
            @RequestParam(defaultValue = "clientes") String elemento,
            @RequestParam(defaultValue = "") String filtro,
            Model model, Authentication auth) {

        boolean admin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
        List<String> permitidos = admin
                ? List.of("clientes", "vehiculos", "empleados", "servicios")
                : List.of("clientes", "vehiculos");   // recepcionista

        if (!permitidos.contains(elemento)) {
            elemento = "clientes";
        }

        model.addAttribute("vista", "administracion");
        model.addAttribute("elemento", elemento);
        model.addAttribute("filtro", filtro);

        if (elemento.equals("clientes")) {
            List<ClienteModel> clientes = clienteService.listarClientes();
            model.addAttribute("clientes", clientes);
        }

        if (elemento.equals("empleados")) {
            model.addAttribute("empleados", empleadoService.listarEmpleados());
        }

        if (elemento.equals("vehiculos")) {
            model.addAttribute("vehiculos", vehiculoService.listarVehiculos());
            model.addAttribute("clientes", clienteService.listarClientes());
        }

        if (elemento.equals("servicios")) {
            model.addAttribute("servicios", servicioService.listarServicios());
        }

        model.addAttribute("cantidadClientes", clienteService.listarClientes().size());
        model.addAttribute("cantidadEmpleados", empleadoService.contarEmpleados());
        model.addAttribute("cantidadVehiculos", vehiculoService.contarVehiculos());
        model.addAttribute("cantidadServicios", servicioService.contarServicios());

        return "view/Index";
    }
}