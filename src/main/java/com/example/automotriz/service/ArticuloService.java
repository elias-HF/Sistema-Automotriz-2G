
package com.example.automotriz.service;

import com.example.automotriz.entity.ArticuloEntity;
import com.example.automotriz.entity.UnidadMedidaEntity;
import com.example.automotriz.repository.ArticuloRepository;
import com.example.automotriz.repository.UnidadMedidaRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final UnidadMedidaRepository unidadRepository;

    public ArticuloService(
            ArticuloRepository articuloRepository,
            UnidadMedidaRepository unidadRepository) {
        this.articuloRepository = articuloRepository;
        this.unidadRepository = unidadRepository;
    }

    // LISTAR Y BUSCAR ARTICULOS ACTIVOS
    @Transactional(readOnly = true)
    public List<ArticuloEntity> buscarArticulos(String buscar) {
        return articuloRepository.buscarActivos(
                buscar == null ? "" : buscar.trim());
    }

    // CONTADORES DEL ALMACEN
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

    // UNIDADES DE MEDIDA
    @Transactional(readOnly = true)
    public List<UnidadMedidaEntity> listarUnidades() {
        return unidadRepository.findAll();
    }

    private UnidadMedidaEntity buscarUnidad(int idUnidad) {
        return unidadRepository.findById(idUnidad)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La unidad de medida seleccionada no existe."));
    }

    // CONSULTAR POR ID
    @Transactional(readOnly = true)
    public ArticuloEntity buscarPorId(int id) {
        ArticuloEntity articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El artículo no existe."));

        if (!articulo.isActivo()) {
            throw new IllegalArgumentException(
                    "El artículo está desactivado.");
        }

        return articulo;
    }

    // REGISTRAR
    public ArticuloEntity guardar(
            ArticuloEntity articulo, int idUnidad) {

        validarArticulo(articulo);

        articulo.setCod(articulo.getCod().trim());
        articulo.setNombre(articulo.getNombre().trim());
        articulo.setDescripcion(articulo.getDescripcion().trim());
        articulo.setUnidad(buscarUnidad(idUnidad));
        articulo.setActivo(true);

        return articuloRepository.save(articulo);
    }

    // ACTUALIZAR
    public ArticuloEntity actualizar(
            int id,
            ArticuloEntity datos,
            int idUnidad) {

        validarArticulo(datos);

        ArticuloEntity actual = buscarPorId(id);

        actual.setCod(datos.getCod().trim());
        actual.setNombre(datos.getNombre().trim());
        actual.setDescripcion(datos.getDescripcion().trim());
        actual.setPrecio(datos.getPrecio());
        actual.setStock(datos.getStock());
        actual.setStock_min(datos.getStock_min());
        actual.setUnidad(buscarUnidad(idUnidad));

        return articuloRepository.save(actual);
    }

    // DESACTIVAR SIN ELIMINAR DE LA BASE DE DATOS
    public void desactivar(int id) {
        ArticuloEntity articulo = buscarPorId(id);
        articulo.setActivo(false);
        articuloRepository.save(articulo);
    }

    // VALIDAR DATOS
    private void validarArticulo(ArticuloEntity articulo) {

        if (articulo.getCod() == null
                || articulo.getCod().isBlank()) {
            throw new IllegalArgumentException(
                    "El código es obligatorio.");
        }

        if (articulo.getCod().trim().length() > 15) {
            throw new IllegalArgumentException(
                    "El código no puede superar 15 caracteres.");
        }

        if (articulo.getNombre() == null
                || articulo.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio.");
        }

        if (articulo.getNombre().trim().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar 100 caracteres.");
        }

        if (articulo.getDescripcion() == null
                || articulo.getDescripcion().isBlank()) {
            throw new IllegalArgumentException(
                    "La descripción es obligatoria.");
        }

        if (articulo.getDescripcion().trim().length() > 200) {
            throw new IllegalArgumentException(
                    "La descripción no puede superar 200 caracteres.");
        }

        if (articulo.getPrecio() == null
                || articulo.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El precio no puede ser negativo.");
        }

        if (articulo.getStock() == null
                || articulo.getStock().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo.");
        }

        if (articulo.getStock_min() == null
                || articulo.getStock_min().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El stock mínimo no puede ser negativo.");
        }
    }
}
