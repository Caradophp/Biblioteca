package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotNull;

public record EmprestimoDTO(
        @NotNull(message = "O Livro deve ser informado")
        long idLivro,
        @NotNull(message = "O Usuário deve ser informado")
        long idUsuario
) {
}
