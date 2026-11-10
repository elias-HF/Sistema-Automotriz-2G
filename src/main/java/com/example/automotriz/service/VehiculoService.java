package com.example.automotriz.service;

import com.example.automotriz.adapter.VehiculoAdapter;
import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.model.VehiculoModel;
import com.example.automotriz.repository.ClienteRepository;
import com.example.automotriz.repository.VehiculoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoAdapter vehiculoAdapter;

    public VehiculoService(VehiculoRepository vehiculoRepository,
                           ClienteRepository clienteRepository,
                           VehiculoAdapter vehiculoAdapter) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoAdapter = vehiculoAdapter;
    }

    // readOnly mantiene la sesión abierta para leer el nombre del cliente (carga perezosa)
    @Transactional(readOnly = true)
    public List<VehiculoModel> listarVehiculos() {
        return vehiculoRepository.findAll(Sort.by("idVehiculo"))
                .stream().map(vehiculoAdapter::toModel).toList();
    }

    public long contarVehiculos() {
        return vehiculoRepository.count();
    }

    @Transactional
    public void registrarVehiculo(VehiculoModel model) {
        VehiculoEntity entity = vehiculoAdapter.toEntity(model);

        entity.setIdVehiculo(null);
        entity.setActivo(true);
        entity.setCodigo("TEMPORAL");
        entity.setCliente(buscarClienteActivo(model.getIdCliente()));

        VehiculoEntity guardado = vehiculoRepository.saveAndFlush(entity);
        guardado.setCodigo(String.format("VEH-%03d", guardado.getIdVehiculo()));
        vehiculoRepository.save(guardado);
    }

    @Transactional
    public void modificarVehiculo(VehiculoModel model) {
        if (model.getIdVehiculo() == null) {
            throw new IllegalArgumentException("Vehículo no encontrado");
        }
        VehiculoEntity entity = vehiculoRepository.findById(model.getIdVehiculo())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

        if (!entity.isActivo()) {
            throw new IllegalArgumentException("No se puede modificar un vehículo desactivado");
        }

        entity.setMarca(model.getMarca());
        entity.setModelo(model.getModelo());
        entity.setPlaca(model.getPlaca());
        entity.setAnioFabricacion(model.getAnioFabricacion());
        entity.setKilometros(model.getKilometros());
        entity.setCliente(buscarClienteActivo(model.getIdCliente()));

        vehiculoRepository.saveAndFlush(entity);
    }

    @Transactional
    public void desactivarVehiculo(Integer id) {
        VehiculoEntity entity = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        entity.setActivo(false);
        vehiculoRepository.save(entity);
    }

    @Transactional
    public void activarVehiculo(Integer id) {
        VehiculoEntity entity = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        entity.setActivo(true);
        vehiculoRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public VehiculoModel buscarPorId(Integer id) {
        VehiculoEntity entity = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        return vehiculoAdapter.toModel(entity);
    }

    private ClienteEntity buscarClienteActivo(Integer idCliente) {
        return clienteRepository.findById(idCliente)
                .filter(ClienteEntity::isActivo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El propietario seleccionado no existe o está desactivado"));
    }

    @Transactional(readOnly = true)
    public List<VehiculoModel> listarActivosPorCliente(Integer idCliente) {
        return vehiculoRepository.findByCliente_IdClienteAndActivoTrue(idCliente)
                .stream().map(vehiculoAdapter::toModel).toList();
    }
}