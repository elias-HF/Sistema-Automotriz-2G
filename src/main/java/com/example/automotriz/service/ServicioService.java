package com.example.automotriz.service;

import com.example.automotriz.adapter.ServicioAdapter;
import com.example.automotriz.entity.ServicioEntity;
import com.example.automotriz.model.ServicioModel;
import com.example.automotriz.repository.ServicioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioService {
    private final ServicioRepository servicioRepository;
    private final ServicioAdapter servicioAdapter;

    public ServicioService(ServicioRepository servicioRepository, ServicioAdapter servicioAdapter) {
        this.servicioRepository = servicioRepository;
        this.servicioAdapter = servicioAdapter;
    }

    public List<ServicioModel> listarServicios() {
        return servicioRepository.findAll(Sort.by("idServicio"))
                .stream().map(servicioAdapter::toModel).toList();
    }

    public long contarServicios() {
        return servicioRepository.count();
    }

    @Transactional
    public void registrarServicio(ServicioModel model) {
        ServicioEntity entity = servicioAdapter.toEntity(model);

        entity.setIdServicio(null);
        entity.setActivo(true);
        entity.setCodigo("TEMPORAL");

        ServicioEntity guardado = servicioRepository.saveAndFlush(entity);
        guardado.setCodigo(String.format("SER-%03d", guardado.getIdServicio()));
        servicioRepository.save(guardado);
    }

    @Transactional
    public void modificarServicio(ServicioModel model) {
        if (model.getIdServicio() == null) {
            throw new IllegalArgumentException("Servicio no encontrado");
        }
        ServicioEntity entity = servicioRepository.findById(model.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));

        if (!entity.isActivo()) {
            throw new IllegalArgumentException("No se puede modificar un servicio desactivado");
        }

        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setPrecioPorDefecto(model.getPrecioPorDefecto());
        entity.setCategoria(model.getCategoria());

        servicioRepository.saveAndFlush(entity);
    }

    @Transactional
    public void desactivarServicio(Integer id) {
        ServicioEntity entity = servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        entity.setActivo(false);
        servicioRepository.save(entity);
    }

    @Transactional
    public void activarServicio(Integer id) {
        ServicioEntity entity = servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        entity.setActivo(true);
        servicioRepository.save(entity);
    }

    public ServicioModel buscarPorId(Integer id) {
        ServicioEntity entity = servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        return servicioAdapter.toModel(entity);
    }
}