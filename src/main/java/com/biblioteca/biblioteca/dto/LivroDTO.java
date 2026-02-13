package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public record LivroDTO(
        @NotBlank(message = "Campo [Autor] deve ser informado")
        String autor,

        @NotBlank(message = "Campo [Estado] deve ser informado")
        String estado,

        @NotBlank(message = "Campo [Genero] deve ser informado")
        String genero,

        @NotBlank(message = "Campo [Numero do Livro] deve ser informado")
        long numeroLivro,

        @NotBlank(message = "Campo [Quantidade de livros] deve ser informado")
        int quantidadeLivros,

        @NotBlank(message = "Campo [Quantidade de páginas] deve ser informado")
        int quantidadePaginas,

        @NotBlank(message = "Campo [Título] deve ser informado")
        String titulo
) {
}
