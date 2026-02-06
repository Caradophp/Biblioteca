package com.biblioteca.biblioteca.response;

import java.time.Year;

public record EmprestimoResponse(
        long id,
        String nomeUsuario,
        String tituloLivro,
        Year ano
) {
}
