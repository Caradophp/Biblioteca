package com.biblioteca.biblioteca.request;

import jakarta.validation.constraints.NotBlank;

public record RecuperacaoRequest(
        @NotBlank(message = "A nova senha deve ser informada")
        String senha,
        @NotBlank(message = "A senha deve ser confirmada")
        String confirmeSenha,
        @NotBlank(message = "O E-mail do usuário deve ser informado")
        String email
) {
}
