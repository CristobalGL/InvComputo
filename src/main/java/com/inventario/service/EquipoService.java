package com.inventario.service;

import com.inventario.model.Equipo;
import com.inventario.repository.EquipoRepository;
import org.springframework.stereotype.Service;

@Service
public class EquipoService {

    private final EquipoRepository repository;

    public EquipoService(EquipoRepository repository) {
        this.repository = repository;
    }

    public Equipo buscarPorCodigo(String codigo) {
        return repository.findById(codigo).orElse(null);
    }
}
