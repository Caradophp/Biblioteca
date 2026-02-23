package com.biblioteca.biblioteca.request;

import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
        @NotBlank(message = "O e-mail deve ser informado")
        String email
) {
}
