package com.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(length = 100)
    private String responsable;

    @Column(name = "inventario_id")
    private Long inventarioId;
}
