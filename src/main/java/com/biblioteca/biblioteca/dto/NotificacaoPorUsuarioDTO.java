package com.biblioteca.biblioteca.dto;

public record NotificacaoPorUsuarioDTO(
        String titulo,
        String descricao,
        String state,
        Long idNotificacao
) {
}
