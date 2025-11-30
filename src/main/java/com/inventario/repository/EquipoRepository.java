package com.inventario.repository;

import com.inventario.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    // Este método busca por el campo "codigo"
    Optional<Equipo> findByCodigo(String codigo);
}