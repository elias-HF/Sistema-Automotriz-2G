
package com.example.automotriz.service;

import com.example.automotriz.entity.ArticuloEntity;
import com.example.automotriz.entity.UnidadMedidaEntity;
import com.example.automotriz.repository.ArticuloRepository;
import com.example.automotriz.repository.UnidadMedidaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;

    public ArticuloService(
            ArticuloRepository articuloRepository,
            UnidadMedidaRepository unidadMedidaRepository) {
        this.articuloRepository = articuloRepository;
        this.unidadMedidaRepository = unidadMedidaRepository;
    }

    @Transactional(readOnly = true)
    public List<ArticuloEntity> buscarArticulos(String buscar) {
        String termino = buscar == null ? "" : buscar.trim();
        return articuloRepository.buscarActivos(termino);
    }

    @Transactional(readOnly = true)
    public long contarArticulos() {
        return articuloRepository.countByActivoTrue();
    }

    @Transactional(readOnly = true)
    public long contarStockSuficiente() {
        return articuloRepository.contarStockSuficiente();
    }

    @Transactional(readOnly = true)
    public long contarStockCritico() {
        return articuloRepository.contarStockCritico();
    }

    @Transactional(readOnly = true)
    public List<UnidadMedidaEntity> listarUnidades() {
        return unidadMedidaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ArticuloEntity buscarPorId(int id) {
        return articuloRepository.findById(id)
                .filter(ArticuloEntity::isActivo)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El artículo no existe o está inactivo."));
    }

    @Transactional
    public void guardar(ArticuloEntity articulo, int idUnidad) {
        validarArticulo(articulo);

        UnidadMedidaEntity unidad = unidadMedidaRepository
                .findById(idUnidad)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La unidad de medida seleccionada no existe."));

        articulo.setUnidad(unidad);
        articulo.setActivo(true);

        articuloRepository.save(articulo);
    }

    @Transactional
    public void actualizar(int id, ArticuloEntity datos, int idUnidad) {
        ArticuloEntity actual = buscarPorId(id);
        validarArticulo(datos);

        UnidadMedidaEntity unidad = unidadMedidaRepository
                .findById(idUnidad)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La unidad de medida seleccionada no existe."));

        actual.setNombre(datos.getNombre().trim());
        actual.setDescripcion(datos.getDescripcion().trim());
        actual.setPrecio(datos.getPrecio());
        actual.setStock(datos.getStock());
        actual.setStock_min(datos.getStock_min());
        actual.setUnidad(unidad);

        articuloRepository.save(actual);
    }

    private void validarArticulo(ArticuloEntity articulo) {
        if (articulo.getNombre() == null
                || articulo.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del artículo es obligatorio.");
        }

        if (articulo.getDescripcion() == null
                || articulo.getDescripcion().isBlank()) {
            throw new IllegalArgumentException(
                    "La descripción es obligatoria.");
        }

        if (articulo.getCod() == null
                || articulo.getCod().isBlank()) {
            throw new IllegalArgumentException(
                    "El código del artículo es obligatorio.");
        }

        if (articulo.getPrecio() == null
                || articulo.getPrecio().signum() < 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor o igual a cero.");
        }

        if (articulo.getStock() == null
                || articulo.getStock().signum() < 0) {
            throw new IllegalArgumentException(
                    "El stock debe ser mayor o igual a cero.");
        }

        if (articulo.getStock_min() == null
                || articulo.getStock_min().signum() < 0) {
            throw new IllegalArgumentException(
                    "El stock mínimo debe ser mayor o igual a cero.");
        }
    }
}

