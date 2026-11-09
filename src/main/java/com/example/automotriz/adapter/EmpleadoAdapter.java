package com.example.automotriz.adapter;

import com.example.automotriz.entity.EmpleadoEntity;
import com.example.automotriz.model.EmpleadoModel;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoAdapter {
    public EmpleadoModel toModel(EmpleadoEntity entity){
        if(entity == null) {return null;}

        EmpleadoModel model = new EmpleadoModel();
        model.setIdEmpleado(entity.getIdEmpleado());
        model.setNombre(entity.getNombre());
        model.setCorreo(entity.getCorreo());
        model.setCodigo(entity.getCodigo());
        model.setUsuario(entity.getUsuario());
        model.setContrasenia(entity.getContrasenia());
        model.setActivo(entity.isActivo());
        model.setRol(entity.getRol());

        return model;
    }

    public EmpleadoEntity toEntity(EmpleadoModel model){
        if(model == null) {return null;}

        EmpleadoEntity entity = new EmpleadoEntity();
        entity.setIdEmpleado(model.getIdEmpleado());
        entity.setNombre(model.getNombre());
        entity.setCorreo(model.getCorreo());
        entity.setCodigo(model.getCodigo());
        entity.setUsuario(model.getUsuario());
        entity.setContrasenia(model.getContrasenia());
        entity.setActivo(model.isActivo());
        entity.setRol(model.getRol());

        return entity;
    }
}
