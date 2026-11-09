package com.example.automotriz.adapter;

import com.example.automotriz.entity.OrdenEntity;
import com.example.automotriz.model.OrdenModel;
import com.example.automotriz.repository.CitaRepository;

public class OrdenAdapter {
    private final CitaRepository citaRepository;
    public OrdenAdapter(CitaRepository citaRepository) {this.citaRepository = citaRepository;}

    public OrdenModel toModel(OrdenEntity entity){
        if(entity == null) {return null;}

        OrdenModel model = new OrdenModel();
        model.setIdOrden(entity.getIdOrden());
        model.setFechaHoraIngreso(entity.getFechaHoraIngreso());
        model.setFechaHoraSalida(entity.getFechaHoraSalida());
        model.setDiagnostico(entity.getDiagnostico());
        model.setMontoTotal(entity.getMontoTotal());
        model.setCodigo(entity.getCodigo());
        model.setEstado(entity.getEstado());
        model.setIdCita(entity.getCita() != null ? entity.getCita().getIdCita() : null);

        return model;
    }

    public OrdenEntity toEntity(OrdenModel model){
        if(model == null) {return null;}

        OrdenEntity entity = new OrdenEntity();
        entity.setIdOrden(model.getIdOrden());
        entity.setFechaHoraIngreso(model.getFechaHoraIngreso());
        entity.setFechaHoraSalida(model.getFechaHoraSalida());
        entity.setDiagnostico(model.getDiagnostico());
        entity.setMontoTotal(model.getMontoTotal());
        entity.setCodigo(model.getCodigo());
        entity.setEstado(model.getEstado());
        entity.setCita(model.getIdCita() != null ? citaRepository.getReferenceById(model.getIdCita()) : null);

        return entity;
    }
}
