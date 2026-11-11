package com.example.automotriz.service;

import com.example.automotriz.entity.CitaEntity;
import com.example.automotriz.repository.CitaRepository;
import com.example.automotriz.repository.ComprobanteRepository;
import com.example.automotriz.repository.DetalleOrdenRepository;
import com.example.automotriz.repository.OrdenRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DashboardService {
    private static final List<String> CATEGORIAS = List.of(
            "MANTENIMIENTO", "MOTOR", "FRENOS Y SUSPENSION", "ELECTRICIDAD", "TRANSMISION", "DIAGNOSTICO Y OTROS");
    private static final Map<String, String> NOMBRE_CATEGORIA = Map.of(
            "MANTENIMIENTO", "Mantenimiento", "MOTOR", "Motor",
            "FRENOS Y SUSPENSION", "Frenos y suspensión", "ELECTRICIDAD", "Electricidad",
            "TRANSMISION", "Transmisión", "DIAGNOSTICO Y OTROS", "Diagnóstico y otros");

    private static final List<String> ESTADOS_ORDEN = List.of("FINALIZADA", "PENDIENTE", "CANCELADA");
    private static final Map<String, String> NOMBRE_ESTADO = Map.of(
            "FINALIZADA", "Finalizadas", "PENDIENTE", "Pendientes", "CANCELADA", "Canceladas");

    private static final List<String> DIAS = List.of("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom");
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // ---------- Datos que consume la pantalla ----------
    public record ServicioDestacado(String nombre, long veces) {}

    public record Dashboard(BigDecimal ingresos, long ordenesAtendidas, long citasPendientes,
                            List<String> categorias, List<BigDecimal> ingresosPorCategoria,
                            List<String> estadosOrden, List<Long> cantidadesOrden,
                            List<String> dias, List<Long> citasPorDia, List<Long> ordenesPorDia,
                            List<ServicioDestacado> destacados) {}

    public record Reporte(String hoja, String archivo, String titulo,
                          String[] encabezados, List<Object[]> filas) {}

    private final ComprobanteRepository comprobanteRepository;
    private final OrdenRepository ordenRepository;
    private final CitaRepository citaRepository;
    private final DetalleOrdenRepository detalleRepository;

    public DashboardService(ComprobanteRepository comprobanteRepository,
                            OrdenRepository ordenRepository,
                            CitaRepository citaRepository,
                            DetalleOrdenRepository detalleRepository) {
        this.comprobanteRepository = comprobanteRepository;
        this.ordenRepository = ordenRepository;
        this.citaRepository = citaRepository;
        this.detalleRepository = detalleRepository;
    }

    // ---------- Indicadores y gráficos ----------

    @Transactional(readOnly = true)
    public Dashboard resumen(LocalDate desde, LocalDate hasta) {
        LocalDateTime ini = desde.atStartOfDay();
        LocalDateTime fin = hasta.plusDays(1).atStartOfDay();

        BigDecimal ingresos = nvl(comprobanteRepository.sumarIngresos(ini, fin));
        long ordenesAtendidas = ordenRepository.contarFinalizadas(ini, fin);
        long citasPendientes = citaRepository.countByEstado(CitaService.PENDIENTE);

        // Ingresos por categoría (siempre salen todas, aunque estén en 0)
        Map<String, BigDecimal> porCategoria = new HashMap<>();
        for (Object[] fila : detalleRepository.ingresosServiciosPorCategoria(ini, fin)) {
            porCategoria.put((String) fila[0], (BigDecimal) fila[1]);
        }
        List<String> categorias = new ArrayList<>();
        List<BigDecimal> montos = new ArrayList<>();
        for (String c : CATEGORIAS) {
            categorias.add(NOMBRE_CATEGORIA.get(c));
            montos.add(porCategoria.getOrDefault(c, BigDecimal.ZERO));
        }
        categorias.add("Repuestos");
        montos.add(nvl(detalleRepository.ingresosArticulos(ini, fin)));

        // Estado de las órdenes
        Map<String, Long> porEstado = new HashMap<>();
        for (Object[] fila : ordenRepository.contarPorEstado(ini, fin)) {
            porEstado.put((String) fila[0], ((Number) fila[1]).longValue());
        }
        List<String> estados = new ArrayList<>();
        List<Long> cantidades = new ArrayList<>();
        for (String e : ESTADOS_ORDEN) {
            estados.add(NOMBRE_ESTADO.get(e));
            cantidades.add(porEstado.getOrDefault(e, 0L));
        }

        // Actividad por día de la semana
        long[] citas = new long[7];
        long[] ordenes = new long[7];
        for (LocalDate f : citaRepository.fechasEntre(desde, hasta)) {
            citas[f.getDayOfWeek().getValue() - 1]++;
        }
        for (LocalDateTime f : ordenRepository.fechasIngreso(ini, fin)) {
            ordenes[f.getDayOfWeek().getValue() - 1]++;
        }

        // Servicios destacados
        List<ServicioDestacado> destacados = detalleRepository.topServicios(ini, fin, PageRequest.of(0, 4))
                .stream()
                .map(f -> new ServicioDestacado((String) f[0], ((Number) f[1]).longValue()))
                .toList();

        return new Dashboard(ingresos, ordenesAtendidas, citasPendientes,
                categorias, montos, estados, cantidades,
                DIAS, Arrays.stream(citas).boxed().toList(), Arrays.stream(ordenes).boxed().toList(),
                destacados);
    }

    public static String formatoMoneda(BigDecimal monto) {
        return String.format(Locale.US, "S/ %,.2f", monto);
    }

    // ---------- Reportes para Excel ----------

    @Transactional(readOnly = true)
    public Reporte reporte(String tipo, LocalDate desde, LocalDate hasta) {
        LocalDateTime ini = desde.atStartOfDay();
        LocalDateTime fin = hasta.plusDays(1).atStartOfDay();
        String periodo = "Del " + desde.format(FECHA) + " al " + hasta.format(FECHA);

        switch (tipo) {
            case "ordenes" -> {
                List<Object[]> filas = ordenRepository.listarPorRango(ini, fin).stream()
                        .map(o -> new Object[]{
                                o.getCodigo(),
                                o.getCita().getCliente().getNombre(),
                                o.getCita().getVehiculo().getPlaca(),
                                o.getEstado(),
                                o.getFechaHoraIngreso().format(FECHA_HORA),
                                o.getFechaHoraSalida() == null ? "-" : o.getFechaHoraSalida().format(FECHA_HORA),
                                o.getMontoTotal()})
                        .toList();
                return new Reporte("Ordenes", "reporte_ordenes.xlsx", "Órdenes. " + periodo,
                        new String[]{"Código", "Cliente", "Placa", "Estado", "Ingreso", "Salida", "Monto (S/)"},
                        filas);
            }
            case "citas" -> {
                List<Object[]> filas = citaRepository.listarPendientes().stream()
                        .map((CitaEntity c) -> new Object[]{
                                c.getCodigo(),
                                c.getCliente().getNombre(),
                                c.getVehiculo().getPlaca(),
                                c.getFecha().format(FECHA),
                                c.getHora().toString(),
                                c.getMotivo()})
                        .toList();
                return new Reporte("Citas", "reporte_citas_pendientes.xlsx", "Citas pendientes",
                        new String[]{"Código", "Cliente", "Placa", "Fecha", "Hora", "Motivo"}, filas);
            }
            default -> {
                List<Object[]> filas = comprobanteRepository.listarPorRango(ini, fin).stream()
                        .map(c -> new Object[]{
                                c.getCodigo(),
                                c.getTipoComprobante(),
                                c.getOrden().getCita().getCliente().getNombre(),
                                c.getOrden().getCita().getVehiculo().getPlaca(),
                                c.getMetodoPago(),
                                c.getEstadoPago(),
                                c.getFechaHoraEmision().format(FECHA_HORA),
                                c.getTotal()})
                        .toList();
                return new Reporte("Ventas", "reporte_ventas.xlsx", "Ventas. " + periodo,
                        new String[]{"Código", "Tipo", "Cliente", "Placa", "Medio de pago",
                                "Estado de pago", "Fecha de emisión", "Total (S/)"}, filas);
            }
        }
    }

    private static BigDecimal nvl(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }
}