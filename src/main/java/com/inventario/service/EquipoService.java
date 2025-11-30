package com.inventario.service;

import com.inventario.model.Equipo;
import com.inventario.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EquipoService {

    private final EquipoRepository repository;

    public EquipoService(EquipoRepository repository) {
        this.repository = repository;
    }

    // Buscar por ID (Long) → método estándar de JpaRepository
    public Optional<Equipo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Si necesitas buscar por el campo "codigo" (String), crea este método en el repository
    public Equipo buscarPorCodigo(String codigo) {
        return repository.findByCodigo(codigo)
                .orElse(null);
    }

    // Otros métodos útiles
    public Iterable<Equipo> listarTodos() {
        return repository.findAll();
    }

    public Equipo guardar(Equipo equipo) {
        return repository.save(equipo);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}