package com.inventario.controller;

import com.inventario.model.Equipo;
import com.inventario.service.EquipoService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping("/equipos")
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.listarTodos());
        model.addAttribute("titulo", "Inventario de Cómputo");
        return "equipos";
    }

    @GetMapping("/equipos/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevoEquipo(Model model) {
        model.addAttribute("equipo", new Equipo());
        model.addAttribute("titulo", "Nuevo Equipo");
        return "formulario";
    }

    @PostMapping("/equipos/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarEquipo(@ModelAttribute Equipo equipo, RedirectAttributes flash) {
        equipoService.guardar(equipo);
        flash.addFlashAttribute("mensaje", 
            equipo.getId() != null ? "Equipo actualizado con éxito" : "Equipo agregado con éxito");
        
        return "redirect:/equipos";
    }

    @GetMapping("/equipos/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editarEquipo(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return equipoService.buscarPorId(id)
            .map(equipo -> {
                model.addAttribute("equipo", equipo);
                model.addAttribute("titulo", "Editar Equipo");
                return "formulario";
            })
            .orElseGet(() -> {
                flash.addFlashAttribute("error", "Equipo no encontrado");
                return "redirect:/equipos";
            });
    }

    @GetMapping("/equipos/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarEquipo(@PathVariable Long id, RedirectAttributes flash) {
        equipoService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Equipo eliminado correctamente");
        return "redirect:/equipos";
    }
}