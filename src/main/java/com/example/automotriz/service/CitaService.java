package com.example.automotriz.service;

import com.example.automotriz.adapter.CitaAdapter;
import com.example.automotriz.entity.CitaEntity;
import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.model.CitaModel;
import com.example.automotriz.repository.CitaRepository;
import com.example.automotriz.repository.ClienteRepository;
import com.example.automotriz.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CitaService {
    public static final String PENDIENTE = "PENDIENTE";
    public static final String CANCELADA = "CANCELADA";
    public static final String ATENDIDA = "ATENDIDA";

    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final CitaAdapter citaAdapter;

    public CitaService(CitaRepository citaRepository,
                       ClienteRepository clienteRepository,
                       VehiculoRepository vehiculoRepository,
                       CitaAdapter citaAdapter) {
        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.citaAdapter = citaAdapter;
    }

    @Transactional(readOnly = true)
    public List<CitaModel> listarPorRango(LocalDate desde, LocalDate hasta) {
        return citaRepository.listarPorRango(desde, hasta)
                .stream().map(citaAdapter::toModel).toList();
    }

    @Transactional
    public CitaModel registrarCita(CitaModel model) {
        ClienteEntity cliente = obtenerClienteActivo(model.getIdCliente());
        VehiculoEntity vehiculo = obtenerVehiculoDelCliente(model.getIdVehiculo(), cliente);
        validarDisponibilidad(vehiculo.getIdVehiculo(), model.getFecha(), null);

        CitaEntity entity = new CitaEntity();
        entity.setFecha(model.getFecha());
        entity.setHora(model.getHora());
        entity.setMotivo(model.getMotivo().trim());
        entity.setMedioContacto(model.getMedioContacto());
        entity.setEstado(PENDIENTE);
        entity.setCodigo("TEMPORAL");
        entity.setCliente(cliente);
        entity.setVehiculo(vehiculo);

        CitaEntity guardada = citaRepository.saveAndFlush(entity);
        guardada.setCodigo(String.format("CIT-%03d", guardada.getIdCita()));
        citaRepository.save(guardada);

        return citaAdapter.toModel(guardada);
    }

    @Transactional
    public CitaModel modificarCita(CitaModel model) {
        CitaEntity entity = obtenerCita(model.getIdCita());

        if (!PENDIENTE.equals(entity.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden modificar las citas pendientes");
        }

        ClienteEntity cliente = obtenerClienteActivo(model.getIdCliente());
        VehiculoEntity vehiculo = obtenerVehiculoDelCliente(model.getIdVehiculo(), cliente);
        validarDisponibilidad(vehiculo.getIdVehiculo(), model.getFecha(), entity.getIdCita());

        entity.setFecha(model.getFecha());
        entity.setHora(model.getHora());
        entity.setMotivo(model.getMotivo().trim());
        entity.setMedioContacto(model.getMedioContacto());
        entity.setCliente(cliente);
        entity.setVehiculo(vehiculo);

        return citaAdapter.toModel(citaRepository.saveAndFlush(entity));
    }

    @Transactional
    public CitaModel cancelarCita(Integer id) {
        CitaEntity entity = obtenerCita(id);

        if (!PENDIENTE.equals(entity.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden cancelar las citas pendientes");
        }

        entity.setEstado(CANCELADA);
        return citaAdapter.toModel(citaRepository.save(entity));
    }

    // Lo usará el módulo de órdenes al convertir la cita
    @Transactional
    public void marcarAtendida(Integer id) {
        CitaEntity entity = obtenerCita(id);

        if (!PENDIENTE.equals(entity.getEstado())) {
            throw new IllegalArgumentException("La cita ya no está pendiente");
        }

        entity.setEstado(ATENDIDA);
        citaRepository.save(entity);
    }

    // ---------- Validaciones internas ----------

    private CitaEntity obtenerCita(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cita no encontrada");
        }
        return citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
    }

    private ClienteEntity obtenerClienteActivo(Integer idCliente) {
        return clienteRepository.findById(idCliente)
                .filter(ClienteEntity::isActivo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El cliente seleccionado no existe o está desactivado"));
    }

    private VehiculoEntity obtenerVehiculoDelCliente(Integer idVehiculo, ClienteEntity cliente) {
        VehiculoEntity vehiculo = vehiculoRepository.findById(idVehiculo)
                .filter(VehiculoEntity::isActivo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El vehículo seleccionado no existe o está desactivado"));

        if (!vehiculo.getCliente().getIdCliente().equals(cliente.getIdCliente())) {
            throw new IllegalArgumentException("El vehículo no pertenece al cliente seleccionado");
        }
        return vehiculo;
    }

    private void validarDisponibilidad(Integer idVehiculo, LocalDate fecha, Integer idCitaActual) {
        boolean ocupado = idCitaActual == null
                ? citaRepository.existsByVehiculo_IdVehiculoAndFechaAndEstado(idVehiculo, fecha, PENDIENTE)
                : citaRepository.existsByVehiculo_IdVehiculoAndFechaAndEstadoAndIdCitaNot(
                idVehiculo, fecha, PENDIENTE, idCitaActual);

        if (ocupado) {
            throw new IllegalArgumentException("Este vehículo ya tiene una cita pendiente en esa fecha");
        }
    }
}