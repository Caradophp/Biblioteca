package com.biblioteca.biblioteca.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidacao(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("aviso", msg));
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<?> handleRegraNegocio(RegraNegocioException ex) {
        return ResponseEntity.badRequest().body(Map.of("aviso", ex.getMessage()));
    }

    @ExceptionHandler(AvisosException.class)
    public ResponseEntity<?> handleAvisos(AvisosException ex) {
        return ResponseEntity.badRequest().body(Map.of("aviso", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeral(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(Map.of("aviso", "Erro interno no servidor"));
    }
}

