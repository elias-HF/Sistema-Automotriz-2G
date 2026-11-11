package com.example.automotriz.controller;

import com.example.automotriz.service.DashboardService;
import com.example.automotriz.service.DashboardService.Dashboard;
import com.example.automotriz.service.DashboardService.Reporte;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        LocalDate[] rango = normalizar(desde, hasta);
        Dashboard d = dashboardService.resumen(rango[0], rango[1]);

        model.addAttribute("vista", "dashboard");
        model.addAttribute("desde", rango[0]);
        model.addAttribute("hasta", rango[1]);

        // Indicadores
        model.addAttribute("ingresosTexto", DashboardService.formatoMoneda(d.ingresos()));
        model.addAttribute("ordenesAtendidas", d.ordenesAtendidas());
        model.addAttribute("citasPendientes", d.citasPendientes());

        // Gráficos (los lee el script de Index.html)
        model.addAttribute("categorias", d.categorias());
        model.addAttribute("ingresosPorCategoria", d.ingresosPorCategoria());
        model.addAttribute("estadosOrden", d.estadosOrden());
        model.addAttribute("cantidadesOrden", d.cantidadesOrden());
        model.addAttribute("diasSemana", d.dias());
        model.addAttribute("citasPorDia", d.citasPorDia());
        model.addAttribute("ordenesPorDia", d.ordenesPorDia());

        model.addAttribute("destacados", d.destacados());
        return "view/Index";
    }

    @GetMapping("/dashboard/exportar-excel")
    public ResponseEntity<byte[]> exportarExcel(
            @RequestParam(defaultValue = "ventas") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta)
            throws IOException {

        LocalDate[] rango = normalizar(desde, hasta);
        Reporte reporte = dashboardService.reporte(tipo, rango[0], rango[1]);

        byte[] archivo;

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            Sheet hoja = workbook.createSheet(reporte.hoja());

            Row titulo = hoja.createRow(0);
            titulo.createCell(0).setCellValue("2G AUTOMOTRIZ");
            titulo.createCell(1).setCellValue(reporte.titulo());

            Row cabecera = hoja.createRow(2);
            for (int i = 0; i < reporte.encabezados().length; i++) {
                cabecera.createCell(i).setCellValue(reporte.encabezados()[i]);
            }

            int numeroFila = 3;
            for (Object[] datos : reporte.filas()) {
                Row fila = hoja.createRow(numeroFila++);
                for (int i = 0; i < datos.length; i++) {
                    Object valor = datos[i];
                    if (valor instanceof Number numero) {
                        fila.createCell(i).setCellValue(numero.doubleValue());
                    } else {
                        fila.createCell(i).setCellValue(String.valueOf(valor));
                    }
                }
            }

            for (int i = 0; i < reporte.encabezados().length; i++) {
                hoja.autoSizeColumn(i);
            }

            workbook.write(salida);
            archivo = salida.toByteArray();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reporte.archivo() + "\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(archivo);
    }

    // Por defecto: del 1 del mes hasta hoy. Si vienen al revés, se intercambian.
    private static LocalDate[] normalizar(LocalDate desde, LocalDate hasta) {
        LocalDate hoy = LocalDate.now();
        LocalDate d = desde != null ? desde : YearMonth.from(hoy).atDay(1);
        LocalDate h = hasta != null ? hasta : hoy;
        return d.isAfter(h) ? new LocalDate[]{h, d} : new LocalDate[]{d, h};
    }
}
