package com.example.automotriz.service;

import com.example.automotriz.entity.ComprobanteEntity;
import com.example.automotriz.entity.DetalleOrdenEntity;
import com.example.automotriz.entity.OrdenEntity;
import com.example.automotriz.entity.VehiculoEntity;
import com.example.automotriz.repository.ComprobanteRepository;
import com.example.automotriz.repository.DetalleOrdenRepository;
import com.example.automotriz.repository.OrdenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ComprobanteService {
    public static final String PENDIENTE = "PENDIENTE";
    public static final String PAGADO = "PAGADO";
    public static final String CANCELADO = "CANCELADO";

    private static final BigDecimal IGV = new BigDecimal("0.18");
    private static final Set<String> TIPOS = Set.of("BOLETA", "FACTURA");
    private static final Set<String> METODOS = Set.of(
            "EFECTIVO", "YAPE", "PLIN", "TARJETA_DE_CREDITO", "TARJETA_DE_DEBITO");
    private static final Set<String> ESTADOS_EMITIR = Set.of(PENDIENTE, PAGADO);
    private static final Set<String> ESTADOS_MODIFICAR = Set.of(PENDIENTE, PAGADO, CANCELADO);

    // ---------- Datos que consume la pantalla ----------
    public record OrdenOpcion(Integer idOrden, String codigo, String placa,
                              String cliente, boolean conComprobante) {}

    public record ComprobanteVista(Integer idOrden, String codigoOrden, String cliente,
                                   String placa, String marca, String modelo,
                                   BigDecimal costoServicios, BigDecimal costoArticulos,
                                   BigDecimal subtotal, BigDecimal igv, BigDecimal total,
                                   Integer idComprobante, String codigo, String tipoComprobante,
                                   String metodoPago, String estadoPago, LocalDateTime fechaHora) {
        public boolean emitido() {return idComprobante != null;}
        public boolean editable() {return !CANCELADO.equals(estadoPago);}
    }

    private record Montos(BigDecimal servicios, BigDecimal articulos,
                          BigDecimal subtotal, BigDecimal igv, BigDecimal total) {}

    private final ComprobanteRepository comprobanteRepository;
    private final OrdenRepository ordenRepository;
    private final DetalleOrdenRepository detalleRepository;

    public ComprobanteService(ComprobanteRepository comprobanteRepository,
                              OrdenRepository ordenRepository,
                              DetalleOrdenRepository detalleRepository) {
        this.comprobanteRepository = comprobanteRepository;
        this.ordenRepository = ordenRepository;
        this.detalleRepository = detalleRepository;
    }

    // ---------- Consultas ----------

    @Transactional(readOnly = true)
    public List<OrdenOpcion> listarOrdenes() {
        Set<Integer> conComprobante = new HashSet<>(comprobanteRepository.listarIdsOrden());

        return ordenRepository.listarPorEstado(OrdenService.FINALIZADA).stream()
                .map(o -> new OrdenOpcion(o.getIdOrden(), o.getCodigo(),
                        o.getCita().getVehiculo().getPlaca(),
                        o.getCita().getCliente().getNombre(),
                        conComprobante.contains(o.getIdOrden())))
                .toList();
    }

    @Transactional(readOnly = true)
    public ComprobanteVista obtener(Integer idOrden) {
        OrdenEntity o = ordenRepository.buscarConCita(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("La orden seleccionada no existe"));

        if (!OrdenService.FINALIZADA.equals(o.getEstado())) {
            throw new IllegalArgumentException("Solo se emiten comprobantes de órdenes finalizadas");
        }

        Montos m = calcular(idOrden);
        VehiculoEntity v = o.getCita().getVehiculo();
        ComprobanteEntity c = comprobanteRepository.findByOrden_IdOrden(idOrden).orElse(null);

        return new ComprobanteVista(o.getIdOrden(), o.getCodigo(), o.getCita().getCliente().getNombre(),
                v.getPlaca(), v.getMarca(), v.getModelo(),
                m.servicios(), m.articulos(), m.subtotal(), m.igv(), m.total(),
                c == null ? null : c.getIdComprobante(),
                c == null ? null : c.getCodigo(),
                c == null ? null : c.getTipoComprobante(),
                c == null ? null : c.getMetodoPago(),
                c == null ? null : c.getEstadoPago(),
                c == null ? null : c.getFechaHoraEmision());
    }

    // ---------- Emitir ----------

    @Transactional
    public String emitir(Integer idOrden, String tipo, String metodo, String estado) {
        validar(tipo, metodo, estado, ESTADOS_EMITIR);

        OrdenEntity orden = ordenRepository.findById(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("La orden seleccionada no existe"));
        if (!OrdenService.FINALIZADA.equals(orden.getEstado())) {
            throw new IllegalArgumentException("Solo se emiten comprobantes de órdenes finalizadas");
        }
        if (comprobanteRepository.existsByOrden_IdOrden(idOrden)) {
            throw new IllegalArgumentException("Esta orden ya tiene un comprobante");
        }

        Montos m = calcular(idOrden);

        ComprobanteEntity c = new ComprobanteEntity();
        c.setCodigo("TEMPORAL");
        c.setTipoComprobante(tipo);
        c.setMetodoPago(metodo);
        c.setEstadoPago(estado);
        c.setFechaHoraEmision(LocalDateTime.now());
        c.setTotal(m.total());
        c.setPago(PAGADO.equals(estado) ? m.total() : BigDecimal.ZERO);
        c.setOrden(orden);

        ComprobanteEntity guardado = comprobanteRepository.saveAndFlush(c);
        guardado.setCodigo(String.format("CMP-%03d", guardado.getIdComprobante()));
        comprobanteRepository.save(guardado);
        return guardado.getCodigo();
    }

    // ---------- Modificar ----------

    @Transactional
    public String modificar(Integer idOrden, String tipo, String metodo, String estado) {
        validar(tipo, metodo, estado, ESTADOS_MODIFICAR);

        ComprobanteEntity c = comprobanteRepository.findByOrden_IdOrden(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("Esta orden aún no tiene comprobante"));

        if (CANCELADO.equals(c.getEstadoPago())) {
            throw new IllegalArgumentException("Un comprobante cancelado no se puede modificar");
        }

        // Se recalcula por si la orden cambió después de emitir
        Montos m = calcular(idOrden);

        c.setTipoComprobante(tipo);
        c.setMetodoPago(metodo);
        c.setEstadoPago(estado);
        c.setTotal(m.total());
        c.setPago(PAGADO.equals(estado) ? m.total() : BigDecimal.ZERO);

        comprobanteRepository.save(c);
        return c.getCodigo();
    }

    // ---------- Internos ----------

    private void validar(String tipo, String metodo, String estado, Set<String> estadosPermitidos) {
        if (tipo == null || !TIPOS.contains(tipo)) {
            throw new IllegalArgumentException("Tipo de comprobante no válido");
        }
        if (metodo == null || !METODOS.contains(metodo)) {
            throw new IllegalArgumentException("Medio de pago no válido");
        }
        if (estado == null || !estadosPermitidos.contains(estado)) {
            throw new IllegalArgumentException("Estado de pago no válido");
        }
    }

    private Montos calcular(Integer idOrden) {
        BigDecimal servicios = BigDecimal.ZERO;
        BigDecimal articulos = BigDecimal.ZERO;

        for (DetalleOrdenEntity d : detalleRepository.listarPorOrden(idOrden)) {
            if ("SERVICIO".equals(d.getTipo())) {
                servicios = servicios.add(d.getSubtotal());
            } else {
                articulos = articulos.add(d.getSubtotal());
            }
        }

        BigDecimal subtotal = servicios.add(articulos);
        BigDecimal igv = subtotal.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
        return new Montos(servicios, articulos, subtotal, igv, subtotal.add(igv));
    }
}