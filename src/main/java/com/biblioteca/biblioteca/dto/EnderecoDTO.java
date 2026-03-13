package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
        @NotNull(message = "Estado deve ser informado")
        @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
        String estado,
        @Size(min = 2, max = 2, message = "Cidade deve ter 2 caracteres")
        String municipio,
        String nomeBairro,
        String nomeRua,
        String numero
) {}
