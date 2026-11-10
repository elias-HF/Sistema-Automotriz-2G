package com.example.automotriz.service;

import com.example.automotriz.entity.*;
import com.example.automotriz.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrdenService {
    public static final String PENDIENTE = "PENDIENTE";
    public static final String FINALIZADA = "FINALIZADA";
    public static final String CANCELADA = "CANCELADA";

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // ---------- Datos que consume la pantalla ----------
    public record ItemOrden(Integer id, String nombre, BigDecimal cantidad, BigDecimal precio, String unidad) {}
    public record OrdenResumen(Integer idOrden, String codigo, String estado, String placa, String cliente) {}
    public record CitaPendiente(Integer idCita, String codigo, String fechaHora, String motivo,
                                String cliente, String placa, String marcaModelo,
                                Integer anio, BigDecimal kilometros) {}
    public record OrdenVista(Integer idOrden, String codigo, String estado, String codigoCita,
                             String diagnostico, String marcaModelo, String placa, String cliente,
                             Integer anio, BigDecimal kilometros,
                             List<ItemOrden> servicios, List<ItemOrden> articulos) {}
    public record OrdenGuardada(Integer idOrden, String codigo, boolean nueva) {}

    private final OrdenRepository ordenRepository;
    private final DetalleOrdenRepository detalleRepository;
    private final OrdenEmpleadoRepository ordenEmpleadoRepository;
    private final CitaRepository citaRepository;
    private final ServicioRepository servicioRepository;
    private final ArticuloRepository articuloRepository;
    private final EmpleadoRepository empleadoRepository;
    private final CitaService citaService;

    public OrdenService(OrdenRepository ordenRepository,
                        DetalleOrdenRepository detalleRepository,
                        OrdenEmpleadoRepository ordenEmpleadoRepository,
                        CitaRepository citaRepository,
                        ServicioRepository servicioRepository,
                        ArticuloRepository articuloRepository,
                        EmpleadoRepository empleadoRepository,
                        CitaService citaService) {
        this.ordenRepository = ordenRepository;
        this.detalleRepository = detalleRepository;
        this.ordenEmpleadoRepository = ordenEmpleadoRepository;
        this.citaRepository = citaRepository;
        this.servicioRepository = servicioRepository;
        this.articuloRepository = articuloRepository;
        this.empleadoRepository = empleadoRepository;
        this.citaService = citaService;
    }

    // ---------- Consultas ----------

    @Transactional(readOnly = true)
    public List<OrdenResumen> listarDisponibles() {
        return ordenRepository.listarPorEstadoDistinto(CANCELADA).stream()
                .map(o -> new OrdenResumen(o.getIdOrden(), o.getCodigo(), o.getEstado(),
                        o.getCita().getVehiculo().getPlaca(),
                        o.getCita().getCliente().getNombre()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CitaPendiente> listarCitasPendientes() {
        return citaRepository.listarPendientes().stream()
                .map(c -> new CitaPendiente(c.getIdCita(), c.getCodigo(),
                        LocalDateTime.of(c.getFecha(), c.getHora()).format(FORMATO),
                        c.getMotivo(), c.getCliente().getNombre(),
                        c.getVehiculo().getPlaca(),
                        c.getVehiculo().getMarca() + " " + c.getVehiculo().getModelo(),
                        c.getVehiculo().getAnioFabricacion(),
                        c.getVehiculo().getKilometros()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ServicioEntity> listarServiciosActivos() {
        return servicioRepository.findByActivoTrueOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public List<ArticuloEntity> listarArticulosActivos() {
        return articuloRepository.findByActivoTrueOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public OrdenVista obtener(Integer id) {
        OrdenEntity o = ordenRepository.buscarConCita(id)
                .orElseThrow(() -> new IllegalArgumentException("La orden seleccionada no existe"));

        if (CANCELADA.equals(o.getEstado())) {
            throw new IllegalArgumentException("La orden seleccionada está cancelada");
        }

        CitaEntity c = o.getCita();
        VehiculoEntity v = c.getVehiculo();
        List<ItemOrden> servicios = new ArrayList<>();
        List<ItemOrden> articulos = new ArrayList<>();

        for (DetalleOrdenEntity d : detalleRepository.listarPorOrden(id)) {
            if (d.getServicio() != null) {
                servicios.add(new ItemOrden(d.getServicio().getIdServicio(), d.getNombre(),
                        d.getCantidad(), d.getPrecioAplicado(), null));
            } else if (d.getArticulo() != null) {
                articulos.add(new ItemOrden(d.getArticulo().getIdArticulo(), d.getNombre(),
                        d.getCantidad(), d.getPrecioAplicado(), d.getArticulo().getUnidadMedida()));
            }
        }

        return new OrdenVista(o.getIdOrden(), o.getCodigo(), o.getEstado(), c.getCodigo(),
                o.getDiagnostico(), v.getMarca() + " " + v.getModelo(), v.getPlaca(),
                c.getCliente().getNombre(), v.getAnioFabricacion(), v.getKilometros(),
                servicios, articulos);
    }

    // ---------- Guardar (nueva o modificación) ----------

    @Transactional
    public OrdenGuardada guardar(Integer idOrden, Integer idCita, String diagnostico,
                                 List<Integer> servicioIds, List<Integer> articuloIds,
                                 List<BigDecimal> cantidades, String usuario) {

        String diag = diagnostico == null ? "" : diagnostico.trim();
        if (diag.isEmpty()) {
            throw new IllegalArgumentException("El diagnóstico es obligatorio");
        }
        if (diag.length() > 200) {
            throw new IllegalArgumentException("El diagnóstico admite máximo 200 caracteres");
        }

        List<Integer> idsServicio = servicioIds == null ? List.of() : servicioIds.stream().distinct().toList();
        Map<Integer, BigDecimal> cantidadesArticulo = agruparArticulos(articuloIds, cantidades);

        if (idsServicio.isEmpty() && cantidadesArticulo.isEmpty()) {
            throw new IllegalArgumentException("Agrega al menos un servicio o un artículo");
        }

        EmpleadoEntity mecanico = empleadoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró al mecánico de la sesión"));

        boolean nueva = idOrden == null;
        OrdenEntity orden;
        Map<Integer, BigDecimal> preciosServicio = new HashMap<>();
        Map<Integer, BigDecimal> preciosArticulo = new HashMap<>();

        if (nueva) {
            if (idCita == null) {
                throw new IllegalArgumentException("Selecciona la cita que vas a atender");
            }
            CitaEntity cita = citaRepository.findById(idCita)
                    .orElseThrow(() -> new IllegalArgumentException("La cita seleccionada no existe"));
            if (!CitaService.PENDIENTE.equals(cita.getEstado())) {
                throw new IllegalArgumentException("La cita seleccionada ya no está pendiente");
            }

            orden = new OrdenEntity();
            orden.setCita(cita);
            orden.setCodigo("TEMPORAL");
        } else {
            orden = ordenRepository.findById(idOrden)
                    .orElseThrow(() -> new IllegalArgumentException("La orden seleccionada no existe"));
            if (CANCELADA.equals(orden.getEstado())) {
                throw new IllegalArgumentException("No se puede modificar una orden cancelada");
            }

            // Se devuelve al stock lo de la versión anterior y se recuerdan los precios aplicados
            List<DetalleOrdenEntity> anteriores = detalleRepository.listarPorOrden(idOrden);
            for (DetalleOrdenEntity d : anteriores) {
                if (d.getArticulo() != null) {
                    ArticuloEntity a = d.getArticulo();
                    a.setStock(a.getStock().add(d.getCantidad()));
                    preciosArticulo.put(a.getIdArticulo(), d.getPrecioAplicado());
                } else if (d.getServicio() != null) {
                    preciosServicio.put(d.getServicio().getIdServicio(), d.getPrecioAplicado());
                }
            }
            detalleRepository.deleteAll(anteriores);
            detalleRepository.flush();
        }

        List<DetalleOrdenEntity> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Integer id : idsServicio) {
            ServicioEntity s = servicioRepository.findById(id)
                    .filter(x -> x.isActivo() || preciosServicio.containsKey(id))
                    .orElseThrow(() -> new IllegalArgumentException("Uno de los servicios ya no está disponible"));

            BigDecimal precio = preciosServicio.getOrDefault(id, s.getPrecioPorDefecto());

            DetalleOrdenEntity d = new DetalleOrdenEntity();
            d.setTipo("SERVICIO");
            d.setNombre(s.getNombre());
            d.setCantidad(BigDecimal.ONE.setScale(2));
            d.setPrecioAplicado(precio);
            d.setSubtotal(precio);
            d.setServicio(s);
            detalles.add(d);

            total = total.add(precio);
        }

        for (Map.Entry<Integer, BigDecimal> e : cantidadesArticulo.entrySet()) {
            Integer id = e.getKey();
            BigDecimal cantidad = e.getValue();

            ArticuloEntity a = articuloRepository.findById(id)
                    .filter(x -> x.isActivo() || preciosArticulo.containsKey(id))
                    .orElseThrow(() -> new IllegalArgumentException("Uno de los artículos ya no está disponible"));

            if (a.getStock().compareTo(cantidad) < 0) {
                throw new IllegalArgumentException("Stock insuficiente de " + a.getNombre()
                        + " (disponible: " + a.getStock().stripTrailingZeros().toPlainString()
                        + " " + a.getUnidadMedida() + ")");
            }
            a.setStock(a.getStock().subtract(cantidad));

            BigDecimal precio = preciosArticulo.getOrDefault(id, a.getPrecioPorDefecto());
            BigDecimal subtotal = precio.multiply(cantidad).setScale(2, RoundingMode.HALF_UP);

            DetalleOrdenEntity d = new DetalleOrdenEntity();
            d.setTipo("ARTICULO");
            d.setNombre(a.getNombre());
            d.setCantidad(cantidad);
            d.setPrecioAplicado(precio);
            d.setSubtotal(subtotal);
            d.setArticulo(a);
            detalles.add(d);

            total = total.add(subtotal);
        }

        orden.setDiagnostico(diag);
        orden.setMontoTotal(total.setScale(2, RoundingMode.HALF_UP));

        if (nueva) {
            CitaEntity cita = orden.getCita();
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime horaCita = LocalDateTime.of(cita.getFecha(), cita.getHora());

            orden.setEstado(FINALIZADA);
            orden.setFechaHoraIngreso(horaCita.isBefore(ahora) ? horaCita : ahora);
            orden.setFechaHoraSalida(ahora);
        }

        OrdenEntity guardada = ordenRepository.saveAndFlush(orden);

        if (nueva) {
            guardada.setCodigo(String.format("ORD-%03d", guardada.getIdOrden()));
            ordenRepository.save(guardada);
            citaService.marcarAtendida(guardada.getCita().getIdCita());
        }

        for (DetalleOrdenEntity d : detalles) {
            d.setOrden(guardada);
        }
        detalleRepository.saveAll(detalles);

        OrdenEmpleadoId idRelacion = new OrdenEmpleadoId(guardada.getIdOrden(), mecanico.getIdEmpleado());
        if (!ordenEmpleadoRepository.existsById(idRelacion)) {
            ordenEmpleadoRepository.save(new OrdenEmpleadoEntity(guardada, mecanico));
        }

        return new OrdenGuardada(guardada.getIdOrden(), guardada.getCodigo(), nueva);
    }

    private Map<Integer, BigDecimal> agruparArticulos(List<Integer> ids, List<BigDecimal> cantidades) {
        Map<Integer, BigDecimal> mapa = new LinkedHashMap<>();
        if (ids == null || ids.isEmpty()) {
            return mapa;
        }
        if (cantidades == null || cantidades.size() != ids.size()) {
            throw new IllegalArgumentException("Revisa las cantidades de los artículos");
        }
        for (int i = 0; i < ids.size(); i++) {
            BigDecimal cantidad = cantidades.get(i) == null ? null
                    : cantidades.get(i).setScale(2, RoundingMode.HALF_UP);
            if (ids.get(i) == null || cantidad == null || cantidad.signum() <= 0) {
                throw new IllegalArgumentException("Las cantidades deben ser mayores a 0");
            }
            mapa.merge(ids.get(i), cantidad, BigDecimal::add);
        }
        return mapa;
    }
}