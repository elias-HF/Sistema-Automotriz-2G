package com.example.automotriz.adapter;

import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.model.VehiculoModel;
import com.example.automotriz.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehiculoAdapter {
    private final ClienteRepository clienteRepository;
    public VehiculoAdapter(ClienteRepository clienteRepository) {this.clienteRepository = clienteRepository;}

    public VehiculoModel toModel(VehiculoEntity entity){
        if(entity == null) {return null;}

        VehiculoModel model = new VehiculoModel();
        model.setIdVehiculo(entity.getIdVehiculo());
        model.setAnioFabricacion(entity.getAnioFabricacion());
        model.setPlaca(entity.getPlaca());
        model.setKilometros(entity.getKilometros());
        model.setCodigo(entity.getCodigo());
        model.setModelo(entity.getModelo());
        model.setMarca(entity.getMarca());
        model.setActivo(entity.isActivo());
        model.setIdCliente(entity.getCliente() != null ? entity.getCliente().getIdCliente() : null);

        return model;
    }

    public VehiculoEntity toEntity(VehiculoModel model){
        if(model == null) {return null;}

        VehiculoEntity entity = new VehiculoEntity();
        entity.setIdVehiculo(model.getIdVehiculo());
        entity.setAnioFabricacion(model.getAnioFabricacion());
        entity.setPlaca(model.getPlaca());
        entity.setKilometros(model.getKilometros());
        entity.setCodigo(model.getCodigo());
        entity.setModelo(model.getModelo());
        entity.setMarca(model.getMarca());
        entity.setActivo(model.isActivo());
        entity.setCliente(model.getIdCliente() != null ? clienteRepository.getReferenceById(model.getIdCliente()) : null);

        return entity;
    }
}
