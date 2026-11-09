package com.example.automotriz.adapter;

import com.example.automotriz.entity.CitaEntity;
import com.example.automotriz.model.CitaModel;
import com.example.automotriz.repository.ClienteRepository;
import com.example.automotriz.repository.VehiculoRepository;

public class CitaAdapter {
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;

    public CitaAdapter(ClienteRepository clienteRepository, VehiculoRepository vehiculoRepository) {
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public CitaModel toModel(CitaEntity entity){
        if(entity == null) {return null;}

        CitaModel model = new CitaModel();
        model.setIdCita(entity.getIdCita());
        model.setFecha(entity.getFecha());
        model.setHora(entity.getHora());
        model.setMotivo(entity.getMotivo());
        model.setEstado(entity.getEstado());
        model.setCodigo(entity.getCodigo());
        model.setMedioContacto(entity.getMedioContacto());
        model.setIdCliente(entity.getCliente() != null ? entity.getCliente().getIdCliente() : null);
        model.setIdVehiculo(entity.getVehiculo() != null ? entity.getVehiculo().getIdVehiculo() : null);

        return model;
    }

    public CitaEntity toEntity(CitaModel model){
        if(model == null) {return null;}

        CitaEntity entity = new CitaEntity();
        entity.setIdCita(model.getIdCita());
        entity.setFecha(model.getFecha());
        entity.setHora(model.getHora());
        entity.setMotivo(model.getMotivo());
        entity.setEstado(model.getEstado());
        entity.setCodigo(model.getCodigo());
        entity.setMedioContacto(model.getMedioContacto());
        entity.setCliente(model.getIdCliente() != null ? clienteRepository.getReferenceById(model.getIdCliente()) : null);
        entity.setVehiculo(model.getIdVehiculo() != null ? vehiculoRepository.getReferenceById(model.getIdVehiculo()) : null);

        return entity;
    }
}
