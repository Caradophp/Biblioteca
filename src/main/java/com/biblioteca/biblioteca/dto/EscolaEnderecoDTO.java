package com.biblioteca.biblioteca.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record EscolaEnderecoDTO(
        @Valid
        @NotNull(message = "Endereço deve ser informado")
        EnderecoDTO endereco,
        @NotNull(message = "Escola deve ser informada")
        EscolaDTO escola
) {
}
