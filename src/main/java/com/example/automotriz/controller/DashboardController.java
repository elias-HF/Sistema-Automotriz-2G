package com.example.automotriz.controller;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    private final List<String> categorias = List.of("Mantenimiento", "Diagnóstico", "Frenos", "Electricidad", "Otros");
    private final List<Double> ingresos = List.of(8500.0, 4200.0, 2800.0, 1900.0, 1050.0);

    @GetMapping("/dashboard")
    public String mostrarDashboard(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta, Model model) {
        LocalDate hoy = LocalDate.now();
        if (desde == null) {desde = YearMonth.from(hoy).atDay(1);}
        if (hasta == null) {hasta = hoy;}

        model.addAttribute("titulo", "Dashboard - 2G Automotriz");
        model.addAttribute("usuario", "Administrador");

        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        // Datos de demostración
        model.addAttribute("ingresosTexto", "S/ 18,450.00");
        model.addAttribute("ordenesAtendidas", 42);
        model.addAttribute("citasPendientes", 5);

        model.addAttribute("categorias", categorias);
        model.addAttribute("ingresosPorCategoria", ingresos);

        model.addAttribute("vista", "dashboard");
        return "view/Index";
    }

    @GetMapping("/dashboard/exportar-excel")
    public ResponseEntity<byte[]> exportarExcel(@RequestParam(defaultValue = "ventas") String tipo) throws IOException {
        String nombreHoja;
        String nombreArchivo;
        String[] encabezados;
        List<Object[]> filas;

        switch (tipo) {
            case "ordenes" -> {
                nombreHoja = "Ordenes";
                nombreArchivo = "reporte_ordenes.xlsx";
                encabezados = new String[]{"Código", "Servicio", "Estado"};
                filas = List.of(
                        new Object[]{"ORD-001", "Mantenimiento preventivo", "Finalizada"},
                        new Object[]{"ORD-002", "Diagnóstico de motor", "En proceso"},
                        new Object[]{"ORD-003", "Revisión de frenos", "En espera"}
                );
            }

            case "citas" -> {
                nombreHoja = "Citas";
                nombreArchivo = "reporte_citas.xlsx";
                encabezados = new String[]{"Código", "Cliente", "Fecha", "Estado"};

                filas = List.of(
                        new Object[]{"CIT-001", "Cliente de prueba 1", "2026-10-20", "Pendiente"},
                        new Object[]{"CIT-002", "Cliente de prueba 2", "2026-10-20", "Confirmada"},
                        new Object[]{"CIT-003", "Cliente de prueba 3", "2026-10-21", "Pendiente"}
                );
            }

            default -> {
                nombreHoja = "Ventas";
                nombreArchivo = "reporte_ventas.xlsx";
                encabezados = new String[]{"Categoría", "Ingresos (S/)"};

                filas = List.of(
                        new Object[]{"Mantenimiento", 8500.0},
                        new Object[]{"Diagnóstico", 4200.0},
                        new Object[]{"Frenos", 2800.0},
                        new Object[]{"Electricidad", 1900.0},
                        new Object[]{"Otros", 1050.0}
                );
            }
        }

        byte[] archivo;

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            Sheet hoja = workbook.createSheet(nombreHoja);

            Row titulo = hoja.createRow(0);
            titulo.createCell(0).setCellValue("2G AUTOMOTRIZ");
            titulo.createCell(1).setCellValue("Reporte generado");

            Row cabecera = hoja.createRow(2);

            for (int i = 0; i < encabezados.length; i++) {cabecera.createCell(i).setCellValue(encabezados[i]);}

            int numeroFila = 3;

            for (Object[] datos : filas) {
                Row fila = hoja.createRow(numeroFila++);

                for (int i = 0; i < datos.length; i++) {
                    Object valor = datos[i];

                    if (valor instanceof Number numero) {fila.createCell(i).setCellValue(numero.doubleValue());}
                    else {fila.createCell(i).setCellValue(String.valueOf(valor));}
                }
            }

            for (int i = 0; i < encabezados.length; i++) {hoja.autoSizeColumn(i);}

            workbook.write(salida);
            archivo = salida.toByteArray();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(archivo);
    }
}