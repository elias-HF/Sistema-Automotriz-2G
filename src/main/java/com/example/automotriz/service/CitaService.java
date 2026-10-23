package com.example.automotriz.service;

import com.example.automotriz.entity.CitaEntity;
import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.entity.MedioContactoEntity;
import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.repository.CitaRepository;
import com.example.automotriz.repository.ClienteRepository;
import com.example.automotriz.repository.MedioContactoRepository;
import com.example.automotriz.repository.VehiculoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CitaService {
    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final MedioContactoRepository medioContactoRepository;

    public CitaService(CitaRepository citaRepository, ClienteRepository clienteRepository, VehiculoRepository vehiculoRepository, MedioContactoRepository medioContactoRepository) {
        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.medioContactoRepository = medioContactoRepository;
    }

    @Transactional(readOnly = true)
    public List<CitaEntity> listarTodas() {return citaRepository.findAll();}

    @Transactional(readOnly = true)
    public CitaEntity buscarPorId(int id) {return citaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontró la cita."));}

    // Registrar una cita nueva
    @Transactional
    public CitaEntity registrar(LocalDate fecha, LocalTime hora, String motivo, int idCliente, int idContacto, int idVehiculo) {
        if (fecha == null || hora == null) {throw new IllegalArgumentException("La fecha y la hora son obligatorias.");}
        if (motivo == null || motivo.isBlank()) {throw new IllegalArgumentException("Debes ingresar el motivo de la cita.");}
        if (fecha.isBefore(LocalDate.now())) {throw new IllegalArgumentException("No puedes agendar una cita en una fecha pasada.");}

        ClienteEntity cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new IllegalArgumentException("El cliente no existe."));
        if (!cliente.isActivo()) {throw new IllegalArgumentException("No se puede agendar una cita para un cliente inactivo.");}

        VehiculoEntity vehiculo = vehiculoRepository.findById(idVehiculo).orElseThrow(() -> new IllegalArgumentException("El vehículo no existe."));
        if (!vehiculo.isActivo()) {throw new IllegalArgumentException("No se puede agendar una cita para un vehículo inactivo.");}
        if (vehiculo.getCliente().getIdCliente() != idCliente) {throw new IllegalArgumentException("El vehículo no pertenece al cliente seleccionado.");}

        MedioContactoEntity contacto = medioContactoRepository.findById(idContacto).orElseThrow(() -> new IllegalArgumentException("El medio de contacto no existe."));

        CitaEntity cita = new CitaEntity();
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo(motivo.trim());
        cita.setEstado("PENDIENTE");
        cita.setCliente(cliente);
        cita.setContacto(contacto);
        cita.setVehiculo(vehiculo);
        cita.setCod(generarCodigo());

        return citaRepository.save(cita);
    }

    @Transactional
    public CitaEntity cancelar(int id) {
        CitaEntity cita = buscarPorId(id);

        if ("CANCELADA".equals(cita.getEstado())) {throw new IllegalArgumentException("Esta cita ya está cancelada.");}
        if ("ATENDIDA".equals(cita.getEstado())) {throw new IllegalArgumentException("No se puede cancelar una cita que ya fue atendida.");}

        cita.setEstado("CANCELADA");
        return citaRepository.save(cita);
    }

    private String generarCodigo() {
        String codigo;

        do {codigo = "CIT-" + String.format("%05d", System.currentTimeMillis() % 100000);}
        while (citaRepository.existsByCod(codigo));

        return codigo;
    }
}