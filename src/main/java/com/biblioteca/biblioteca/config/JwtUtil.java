package com.biblioteca.biblioteca.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "12345678123456781234567812345678"; // Chave secreta (deve ter 32 caracteres)
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    // Gera uma chave secreta válida para assinatura
    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Usa diretamente os bytes da chave
    }

    // Método para gerar um token JWT
    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username) // Define o usuário como dono do token
                .claim("role", role) // Define o papel do usuário (ex: ADMIN, USER)
                .issuedAt(new Date()) // Data de emissão
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiração do token
                .signWith(getSigningKey()) // Assina o token
                .compact();
    }

    // Método para extrair informações do token
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Define a chave secreta
                .build() // Constrói o parser
                .parseClaimsJws(token) // Faz o parse do token
                .getBody(); // Retorna o corpo (claims) do token
    }

    // Método para extrair o username do token
    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Método para extrair a role do usuário
    public static String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    // Método para verificar se o token ainda é válido
    public static boolean isTokenValid(String token) {
        try {
            return extractClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
