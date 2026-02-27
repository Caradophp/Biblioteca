package com.biblioteca.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String senha,

        @NotBlank(message = "Tipo de usuário é obrigatório")
        String tipoUsuario,

        @NotBlank(message = "Numero de matricula deve ser informado")
        String matricula,

        @NotBlank(message = "A escola deve ser informada")
        long escolaId
) {}
