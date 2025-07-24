package com.biblioteca.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Desativando CSRF na nova sintaxe
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/login").permitAll() // Permitir acesso ao login sem autenticação
                        .requestMatchers("/usuarios/**").hasRole("administrador")
                        .requestMatchers("/livros/**").hasAnyRole("professor","administrador")
                        .requestMatchers("/livros").hasAnyRole("aluno","professor", "administrador")// Apenas admins podem acessar /cadastrar
                        .requestMatchers("/**").authenticated() // Usuários autenticados podem acessar /user
                )
                .addFilterBefore(new JwtFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
