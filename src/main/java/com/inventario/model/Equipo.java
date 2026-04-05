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

    @Column(length = 50)
    private String serie;

    @Column(length = 50)
    private String estatus;

    @Column(length = 50)
    private String garantia;

    @Column(name = "no_empleado", length = 50)
    private String noEmpleado;

    @Column(length = 50)
    private String responsable;

    @Column(length = 50)
    private String puesto;

    @Transient
    private Long inventarioId;

    // RELACIÓN REAL
    @ManyToOne
    @JoinColumn(name = "inventario_id")
    private Inventario inventario;

    // opcional (puedes eliminarlo después)
    @Column(length = 50)
    private String localidad;
}
