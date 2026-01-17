package com.inventario.service;

import com.inventario.model.Usuario;
import com.inventario.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreo(username)
                .map(usuario -> {
                    // Si la contraseña NO está cifrada (no empieza con $2a$), la ciframos una sola vez
                    if (!usuario.getPassword().startsWith("$2a$")) {
                        String cifrada = passwordEncoder.encode(usuario.getPassword());
                        usuario.setContraseña(cifrada);
                        usuarioRepository.save(usuario);  // ← guarda la versión cifrada
                    }
                    return usuario;
                })
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario con correo: " + username));
    }
}