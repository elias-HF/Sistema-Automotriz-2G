package com.example.automotriz.adapter;

import com.example.automotriz.entity.ArticuloEntity;
import com.example.automotriz.model.ArticuloModel;
import org.springframework.stereotype.Component;

@Component
public class ArticuloAdapter {
    public ArticuloModel toModel(ArticuloEntity entity){
        if(entity == null) {return null;}

        ArticuloModel model = new ArticuloModel();
        model.setIdArticulo(entity.getIdArticulo());
        model.setNombre(entity.getNombre());
        model.setDescripcion(entity.getDescripcion());
        model.setPrecioPorDefecto(entity.getPrecioPorDefecto());
        model.setStock(entity.getStock());
        model.setCodigo(entity.getCodigo());
        model.setStockMinimo(entity.getStockMinimo());
        model.setActivo(entity.isActivo());
        model.setUnidadMedida(entity.getUnidadMedida());

        return model;
    }

    public ArticuloEntity toEntity(ArticuloModel model){
        if(model == null) {return null;}

        ArticuloEntity entity = new ArticuloEntity();
        entity.setIdArticulo(model.getIdArticulo());
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setPrecioPorDefecto(model.getPrecioPorDefecto());
        entity.setStock(model.getStock());
        entity.setCodigo(model.getCodigo());
        entity.setStockMinimo(model.getStockMinimo());
        entity.setActivo(model.isActivo());
        entity.setUnidadMedida(model.getUnidadMedida());

        return entity;
    }
}
