package com.biblioteca.biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/usuarios").permitAll()
                        .requestMatchers("/usuarios/recuperar-senha").permitAll()
                        .requestMatchers("/usuarios/alterar-senha").permitAll()
                        .requestMatchers("/codigo/**").permitAll()
                        .requestMatchers("/email/**").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("administrador")
                        .requestMatchers("/livros/**").hasAnyRole("professor","administrador")
                        .requestMatchers("/livros").hasAnyRole("aluno","professor", "administrador")
                        .requestMatchers("/**").authenticated()
                )
                .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
