package com.biblioteca.biblioteca.model;

import com.biblioteca.biblioteca.utils.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "multas")
@Data
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "id_emprestimo", nullable = false)
    private Emprestimo emprestimo;

    private float multaValor;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(columnDefinition = "boolean default false")
    private boolean erroHumano;
}
