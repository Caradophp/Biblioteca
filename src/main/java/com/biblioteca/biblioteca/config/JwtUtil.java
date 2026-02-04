package com.biblioteca.biblioteca.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "12345678123456781234567812345678";
    private static final long EXPIRATION_TIME = 86400000;

    // Gera uma chave secreta válida para assinatura
    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Método para gerar um token JWT
    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    // Método para extrair informações do token
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
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
