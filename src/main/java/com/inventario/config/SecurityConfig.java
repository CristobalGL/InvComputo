package com.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // Rutas que SOLO el ADMIN puede usar
                .requestMatchers("/equipos/nuevo", "/equipos/guardar", "/equipos/editar/**", "/equipos/eliminar/**").hasRole("ADMIN")

                // Todo lo demás: cualquiera autenticado (USER o ADMIN)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")             // Página de login
                .defaultSuccessUrl("/dashboard", true)    // Después de login → va al menu
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            );

        return http.build();
    }

    // Usuarios en memoria (cambia por base de datos mas adelante)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")           // tiene rol ADMIN
                .build();

        UserDetails usuarioNormal = User.builder()
                .username("juan")
                .password(passwordEncoder().encode("juan2025"))
                .roles("USER")            // solo rol USER
                .build();

        UserDetails maria = User.builder()
                .username("maria")
                .password(passwordEncoder().encode("maria2025"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, usuarioNormal, maria);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static boolean isAdmin() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && 
            auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}