package com.inventario.controller;

import com.inventario.service.EquipoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EquipoController {

    private final EquipoService equipoService;

    // Inyección por constructor (la forma moderna y recomendada)
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping("/")
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.listarTodos());
        model.addAttribute("titulo", "Inventario de Cómputo");
        return "index";  // → templates/index.html
    }
}