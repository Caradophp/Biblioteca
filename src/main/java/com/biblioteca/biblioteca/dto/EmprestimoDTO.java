package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public record EmprestimoDTO(
        @NotBlank(message = "O Livro deve ser informado")
        long idLivro,
        @NotBlank(message = "O Usuário deve ser informado")
        long idUsuario
) {
}
