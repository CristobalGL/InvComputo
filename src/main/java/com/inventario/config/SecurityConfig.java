package com.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()   // Todo lo demás requiere login
            )
            .formLogin(form -> form
                .loginPage("/login")             // Página de login personalizada
                .defaultSuccessUrl("/", true)    // Después de login → va a la tabla
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
        UserDetails usuario1 = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN")
                .build();

        UserDetails usuario2 = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(usuario1, usuario2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}