package com.example.automotriz.adapter;

import org.springframework.stereotype.Component;
import com.example.automotriz.model.ClienteModel;
import com.example.automotriz.entity.ClienteEntity;

@Component
public class ClienteAdapter {
    public ClienteModel toModel(ClienteEntity entity){
        if(entity == null) {return null;}

        ClienteModel cliente = new ClienteModel();
        cliente.setIdCliente(entity.getIdCliente());
        cliente.setNombre(entity.getNombre());
        cliente.setTelefono(entity.getTelefono());
        cliente.setCorreo(entity.getCorreo());
        cliente.setCodigo(entity.getCod());
        cliente.setDni(entity.getDni());
        cliente.setActivo(entity.isActivo());

        return cliente;
    }

    public ClienteEntity toEntity(ClienteModel model){
        if(model == null){return null;}

        ClienteEntity entity = new ClienteEntity();
        entity.setIdCliente(model.getIdCliente());
        entity.setNombre(model.getNombre());
        entity.setTelefono(model.getTelefono());
        entity.setCorreo(model.getCorreo());
        entity.setCod(model.getCodigo());
        entity.setDni(model.getDni());
        entity.setActivo(model.isActivo());

        return entity;
    }
}