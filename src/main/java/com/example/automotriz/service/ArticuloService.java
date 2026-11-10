package com.example.automotriz.service;

import com.example.automotriz.adapter.ArticuloAdapter;
import com.example.automotriz.entity.ArticuloEntity;
import com.example.automotriz.model.ArticuloModel;
import com.example.automotriz.repository.ArticuloRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticuloService {
    public record ResumenStock(long total, long suficiente, long critico) {}

    private final ArticuloRepository articuloRepository;
    private final ArticuloAdapter articuloAdapter;

    public ArticuloService(ArticuloRepository articuloRepository, ArticuloAdapter articuloAdapter) {
        this.articuloRepository = articuloRepository;
        this.articuloAdapter = articuloAdapter;
    }

    // Todos (activos e inactivos) para ver el estado en la tabla
    @Transactional(readOnly = true)
    public List<ArticuloModel> listarArticulos() {
        return articuloRepository.findAll(Sort.by("idArticulo"))
                .stream().map(articuloAdapter::toModel).toList();
    }

    // Las tarjetas cuentan solo artículos activos
    @Transactional(readOnly = true)
    public ResumenStock resumen() {
        List<ArticuloEntity> activos = articuloRepository.findByActivoTrueOrderByNombreAsc();
        long critico = activos.stream()
                .filter(a -> a.getStock().compareTo(a.getStockMinimo()) <= 0).count();
        return new ResumenStock(activos.size(), activos.size() - critico, critico);
    }

    @Transactional
    public void registrarArticulo(ArticuloModel model) {
        ArticuloEntity entity = articuloAdapter.toEntity(model);

        entity.setIdArticulo(null);
        entity.setActivo(true);
        entity.setCodigo("TEMPORAL");

        ArticuloEntity guardado = articuloRepository.saveAndFlush(entity);
        guardado.setCodigo(String.format("ART-%03d", guardado.getIdArticulo()));
        articuloRepository.save(guardado);
    }

    @Transactional
    public void modificarArticulo(ArticuloModel model) {
        if (model.getIdArticulo() == null) {
            throw new IllegalArgumentException("Artículo no encontrado");
        }
        ArticuloEntity entity = articuloRepository.findById(model.getIdArticulo())
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado"));

        if (!entity.isActivo()) {
            throw new IllegalArgumentException("No se puede modificar un artículo desactivado");
        }

        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setPrecioPorDefecto(model.getPrecioPorDefecto());
        entity.setStock(model.getStock());
        entity.setStockMinimo(model.getStockMinimo());
        entity.setUnidadMedida(model.getUnidadMedida());

        articuloRepository.saveAndFlush(entity);
    }

    @Transactional
    public void desactivarArticulo(Integer id) {
        ArticuloEntity entity = articuloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado"));
        entity.setActivo(false);
        articuloRepository.save(entity);
    }

    @Transactional
    public void activarArticulo(Integer id) {
        ArticuloEntity entity = articuloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado"));
        entity.setActivo(true);
        articuloRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public ArticuloModel buscarPorId(Integer id) {
        ArticuloEntity entity = articuloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado"));
        return articuloAdapter.toModel(entity);
    }
}