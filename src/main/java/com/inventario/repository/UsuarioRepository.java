package com.inventario.repository;

import com.inventario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Este método busca por el campo "correo"
    Optional<Usuario> findByCorreo(String correo);
}