package com.inventario.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    private Long id;

    private String nombre;

    private String correo;

    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "rol_id")
    private Integer rolId;

    @Column(name = "inventario_asignado")
    private Long inventarioAsignado;

    // Getters y setters (genera con Lombok o manualmente)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public Integer getRolId() { return rolId; }
    public void setRolId(Integer rolId) { this.rolId = rolId; }
    public Long getInventarioAsignado() { return inventarioAsignado; }
    public void setInventarioAsignado(Long inventarioAsignado) { this.inventarioAsignado = inventarioAsignado; }

    // ─── Métodos de UserDetails ───────────────────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rol = switch (rolId == null ? 0 : rolId) {
            case 1 -> "ADMIN";
            case 2 -> "COORDINADOR";
            case 3 -> "USER";
            default -> "USER";
        };
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override
    public String getPassword() {
        return contraseña;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}