package com.inventario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Equipo {

    @Id
    private String codigo;

    private String tipo;
    private String marca;
    private String modelo;
    private String responsable;

    public Equipo() {}

    // Getters & Setters

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
}
