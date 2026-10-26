
package com.example.automotriz.controller;

import com.example.automotriz.entity.ArticuloEntity;
import com.example.automotriz.service.ArticuloService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AlmacenController {

    private final ArticuloService articuloService;

    public AlmacenController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping("/almacen")
    public String almacen(
            @RequestParam(defaultValue = "") String buscar,
            Model model) {

        model.addAttribute("vista", "almacen");
        model.addAttribute("buscar", buscar);

        model.addAttribute("articulos",
                articuloService.buscarArticulos(buscar));

        model.addAttribute("cantidadArticulos",
                articuloService.contarArticulos());

        model.addAttribute("stockSuficiente",
                articuloService.contarStockSuficiente());

        model.addAttribute("stockCritico",
                articuloService.contarStockCritico());

        model.addAttribute("unidades",
                articuloService.listarUnidades());

        model.addAttribute("articulo", new ArticuloEntity());

        return "view/Index";
    }

    @GetMapping("/almacen/nuevo")
    public String nuevoArticulo(Model model) {
        model.addAttribute("vista", "almacen-formulario");
        model.addAttribute("articulo", new ArticuloEntity());
        model.addAttribute("unidades", articuloService.listarUnidades());
        model.addAttribute("modoEdicion", false);

        return "view/Index";
    }

    @PostMapping("/almacen/guardar")
    public String guardarArticulo(
            @ModelAttribute("articulo") ArticuloEntity articulo,
            @RequestParam("idUnidad") int idUnidad,
            RedirectAttributes redirectAttributes) {

        try {
            articuloService.guardar(articulo, idUnidad);
            redirectAttributes.addFlashAttribute(
                    "mensajeAlmacen", "Artículo registrado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorAlmacen", e.getMessage());
            return "redirect:/almacen/nuevo";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(
                    "errorAlmacen",
                    "El código o nombre ya está registrado.");
            return "redirect:/almacen/nuevo";
        }

        return "redirect:/almacen";
    }

    @GetMapping("/almacen/editar/{id}")
    public String editarArticulo(
            @PathVariable int id,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            model.addAttribute("vista", "almacen-formulario");
            model.addAttribute("articulo", articuloService.buscarPorId(id));
            model.addAttribute("unidades", articuloService.listarUnidades());
            model.addAttribute("modoEdicion", true);

            return "view/Index";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorAlmacen", e.getMessage());
            return "redirect:/almacen";
        }
    }

    @PostMapping("/almacen/actualizar/{id}")
    public String actualizarArticulo(
            @PathVariable int id,
            @ModelAttribute("articulo") ArticuloEntity articulo,
            @RequestParam("idUnidad") int idUnidad,
            RedirectAttributes redirectAttributes) {

        try {
            articuloService.actualizar(id, articulo, idUnidad);
            redirectAttributes.addFlashAttribute(
                    "mensajeAlmacen", "Artículo actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorAlmacen", e.getMessage());
            return "redirect:/almacen/editar/" + id;
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(
                    "errorAlmacen",
                    "El código o nombre ya está registrado.");
            return "redirect:/almacen/editar/" + id;
        }

        return "redirect:/almacen";
    }
}
