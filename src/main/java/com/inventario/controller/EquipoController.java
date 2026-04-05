package com.inventario.controller;

import com.inventario.model.Equipo;
import com.inventario.model.Inventario;
import com.inventario.service.EquipoService;
import com.inventario.repository.InventarioRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import java.util.List;

@Controller
public class EquipoController {

    private final EquipoService equipoService;
    private final InventarioRepository inventarioRepo;

    public EquipoController(EquipoService equipoService, InventarioRepository inventarioRepo) {
        this.equipoService = equipoService;
        this.inventarioRepo = inventarioRepo;
    }

    @GetMapping("/equipos")
    public String listarEquipos(Model model, Authentication auth) {

        String correo = auth.getName(); // usuario logueado

        List<Equipo> equipos;

        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            // ADMIN ve TODO
            equipos = (List<Equipo>) equipoService.listarTodos();
        } else {
            // USUARIO ve solo SU inventario
            equipos = equipoService.obtenerEquiposSegunUsuario(correo);
        }

        model.addAttribute("equipos", equipos);
        model.addAttribute("titulo", "Inventario de Cómputo");

        return "equipos";
    }

    @GetMapping("/equipos/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevoEquipo(Model model) {

        Equipo equipo = new Equipo();

        model.addAttribute("equipo", equipo);
        model.addAttribute("inventarios", inventarioRepo.findAll());
        model.addAttribute("titulo", "Nuevo Equipo");

        return "formulario";
    }

    @PostMapping("/equipos/guardar")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR','USER')")
    public String guardarEquipo(@ModelAttribute Equipo equipo, RedirectAttributes flash) {

        if (equipo.getInventarioId() != null) {
            Inventario inv = inventarioRepo.findById(equipo.getInventarioId())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

            equipo.setInventario(inv);
        }

        equipoService.guardar(equipo);

        flash.addFlashAttribute("mensaje",
            equipo.getId() != null ? "Equipo actualizado con éxito" : "Equipo agregado con éxito");

        return "redirect:/equipos";
    }

    @GetMapping("/equipos/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR','USER')")
    public String editarEquipo(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return equipoService.buscarPorId(id)
            .map(equipo -> {

                //EVITAR NULL
                if (equipo.getInventario() != null) {
                    equipo.setInventarioId(equipo.getInventario().getId());
                }

                model.addAttribute("equipo", equipo);
                model.addAttribute("inventarios", inventarioRepo.findAll());
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