package com.example.automotriz.adapter;

import com.example.automotriz.entity.ServicioEntity;
import com.example.automotriz.model.ServicioModel;
import org.springframework.stereotype.Component;

@Component
public class ServicioAdapter {
    public ServicioModel toModel(ServicioEntity entity) {
        if(entity == null) {return null;}

        ServicioModel model = new ServicioModel();
        model.setIdServicio(entity.getIdServicio());
        model.setNombre(entity.getNombre());
        model.setDescripcion(entity.getDescripcion());
        model.setCodigo(entity.getCodigo());
        model.setPrecioPorDefecto(entity.getPrecioPorDefecto());
        model.setActivo(entity.isActivo());
        model.setCategoria(entity.getCategoria());

        return model;
    }

    public ServicioEntity toEntity(ServicioModel model){
        if(model == null) {return null;}

        ServicioEntity entity = new ServicioEntity();
        entity.setIdServicio(model.getIdServicio());
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setCodigo(model.getCodigo());
        entity.setPrecioPorDefecto(model.getPrecioPorDefecto());
        entity.setActivo(model.isActivo());
        entity.setCategoria(model.getCategoria());

        return entity;
    }
}
