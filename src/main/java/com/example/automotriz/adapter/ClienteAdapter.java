package com.example.automotriz.adapter;

import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.model.ClienteModel;
import org.springframework.stereotype.Component;

@Component
public class ClienteAdapter {
    public ClienteModel toModel(ClienteEntity entity){
        if(entity == null) {return null;}

        ClienteModel model = new ClienteModel();
        model.setIdCliente(entity.getIdCliente());
        model.setNombre(entity.getNombre());
        model.setTelefono(entity.getTelefono());
        model.setCorreo(entity.getCorreo());
        model.setCodigo(entity.getCodigo());
        model.setDni(entity.getDni());
        model.setActivo(entity.isActivo());

        return model;
    }

    public ClienteEntity toEntity(ClienteModel model){
        if(model == null) {return null;}

        ClienteEntity entity = new ClienteEntity();
        entity.setIdCliente(model.getIdCliente());
        entity.setNombre(model.getNombre());
        entity.setTelefono(model.getTelefono());
        entity.setCorreo(model.getCorreo());
        entity.setCodigo(model.getCodigo());
        entity.setDni(model.getDni());
        entity.setActivo(model.isActivo());


        return entity;
    }
}
