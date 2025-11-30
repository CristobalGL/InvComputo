package com.inventario.controller;

import com.inventario.model.Equipo;
import com.inventario.service.EquipoService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EquipoController {

    private final EquipoService equipoService;

    // Inyección por constructor (la forma moderna y recomendada)
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping("/equipos")
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.listarTodos());
        model.addAttribute("titulo", "Inventario de Cómputo");
        return "equipos";  // → templates/equipos.html
    }

    // Solo ADMIN
    @GetMapping("/equipos/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevoEquipo(Model model) {
        model.addAttribute("equipo", new Equipo());
        model.addAttribute("titulo", "Nuevo Equipo");
        return "formulario";  // crearás formulario.html
    }

    // Solo ADMIN
    @PostMapping("/equipos/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarEquipo(@ModelAttribute Equipo equipo) {
        equipoService.guardar(equipo);
        return "redirect:/equipos";
    }

    // Solo ADMIN
    @GetMapping("/equipos/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editarEquipo(@PathVariable Long id, Model model) {
        model.addAttribute("equipo", equipoService.buscarPorId(id).orElse(null));
        model.addAttribute("titulo", "Editar Equipo");
        return "formulario";
    }

    // Solo ADMIN
    @GetMapping("/equipos/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminar(id);
        return "redirect:/equipos";
    }
}