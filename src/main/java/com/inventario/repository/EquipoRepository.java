package com.inventario.repository;

import com.inventario.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    // Este método busca por el campo "codigo"
    Optional<Equipo> findByCodigo(String codigo);

    List<Equipo> findByInventario_Id(Long inventarioId);

    List<Equipo> findByResponsableAndGarantia(String responsable, String garantia);

    List<Equipo> findByInventario_IdAndGarantia(Long inventarioId, String garantia);

    List<Equipo> findByGarantia(String garantia);

}