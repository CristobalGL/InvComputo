package com.inventario.service;

import com.inventario.model.Equipo;
import com.inventario.model.Usuario;
import com.inventario.repository.EquipoRepository;
import com.inventario.repository.UsuarioRepository;


import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;


@Service
public class EquipoService {

    private final EquipoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public EquipoService(EquipoRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
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

    public List<Equipo> obtenerEquiposSegunUsuario(String correoUsuario) {

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Si NO tiene inventario asignado → ve TODO
        if (usuario.getInventarioAsignado() == null) {
            return repository.findAll();
        }

        // Si SÍ tiene asignado → solo ese inventario
        return repository.findByInventario_Id(usuario.getInventarioAsignado());
    }

    public List<Equipo> obtenerEquiposSinGarantia(String correoUsuario) {

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Si NO tiene inventario asignado → puede ver TODOS los equipos sin garantía
        if (usuario.getInventarioAsignado() == null) {
            return repository.findByGarantia("No");
        }

        // Si SÍ tiene inventario asignado → solo ese inventario y sin garantía
        return repository.findByInventario_IdAndGarantia(
                usuario.getInventarioAsignado(), "No");
    }
}