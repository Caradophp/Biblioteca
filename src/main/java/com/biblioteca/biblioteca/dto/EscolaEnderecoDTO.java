package com.biblioteca.biblioteca.dto;

import com.biblioteca.biblioteca.model.Endereco;
import com.biblioteca.biblioteca.model.Escola;

public record EscolaEnderecoDTO(
        Endereco endereco,
        Escola escola
) {
}
