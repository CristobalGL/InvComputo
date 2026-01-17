package com.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;  // o UsuarioService si es el mismo

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                // Rutas que SOLO el ADMIN puede usar
                .requestMatchers("/equipos/nuevo", "/equipos/guardar", "/equipos/editar/**", "/equipos/eliminar/**").hasRole("ADMIN")
                // Todo lo demás: cualquiera autenticado (USER o ADMIN)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/")                // <-- tu vista login.html
                .loginProcessingUrl("/login")  // <-- recibe tu formulario
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }

    // Si usas custom provider, puedes inyectarlo o dejar que Spring lo use automáticamente
}