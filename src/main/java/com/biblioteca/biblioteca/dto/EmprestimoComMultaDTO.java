package com.biblioteca.biblioteca.dto;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoComMultaDTO {

    private long id;
    private Usuario usuario;
    private Livro livro;
    private boolean devolvido;
    private float multaValor;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
}
