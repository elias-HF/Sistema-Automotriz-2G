package com.example.automotriz.adapter;

import com.example.automotriz.entity.ComprobanteEntity;
import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.model.ComprobanteModel;
import com.example.automotriz.model.VehiculoModel;
import com.example.automotriz.repository.OrdenRepository;

public class ComprobanteAdapter {
    private final OrdenRepository ordenRepository;
    public ComprobanteAdapter(OrdenRepository ordenRepository) {this.ordenRepository = ordenRepository;}

    public ComprobanteModel toModel(ComprobanteEntity entity){
        if(entity == null) {return null;}

        ComprobanteModel model = new ComprobanteModel();
        model.setIdComprobante(entity.getIdComprobante());
        model.setCodigo(entity.getCodigo());
        model.setTipoComprobante(entity.getTipoComprobante());
        model.setMetodoPago(entity.getMetodoPago());
        model.setEstadoPago(entity.getEstadoPago());
        model.setFechaHoraEmision(entity.getFechaHoraEmision());
        model.setPago(entity.getPago());
        model.setTotal(entity.getTotal());
        model.setIdOrden(entity.getOrden() != null ? entity.getOrden().getIdOrden() : null);

        return model;
    }

    public ComprobanteEntity toEntity(ComprobanteModel model){
        if(model == null) {return null;}

        ComprobanteEntity entity = new ComprobanteEntity();
        entity.setIdComprobante(model.getIdComprobante());
        entity.setCodigo(model.getCodigo());
        entity.setTipoComprobante(model.getTipoComprobante());
        entity.setMetodoPago(model.getMetodoPago());
        entity.setEstadoPago(model.getEstadoPago());
        entity.setFechaHoraEmision(model.getFechaHoraEmision());
        entity.setPago(model.getPago());
        entity.setTotal(model.getTotal());
        entity.setOrden(model.getIdOrden() != null ? ordenRepository.getReferenceById(model.getIdOrden()) : null);

        return entity;
    }

}
