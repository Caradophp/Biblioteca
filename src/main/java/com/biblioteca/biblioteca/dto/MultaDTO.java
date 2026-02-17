package com.biblioteca.biblioteca.dto;

public record MultaDTO(
        long idEmprestimo,
        float multaValor,
        String tipoPagamento
) {
}
