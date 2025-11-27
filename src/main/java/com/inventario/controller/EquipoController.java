package com.inventario.controller;

import com.inventario.model.Equipo;
import com.inventario.service.EquipoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService service;

    public EquipoController(EquipoService service) {
        this.service = service;
    }

    @GetMapping("/{codigo}")
    public Equipo obtenerEquipo(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }
}
