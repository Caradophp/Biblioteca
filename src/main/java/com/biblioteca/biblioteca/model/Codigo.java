package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "codigo_recuperacao", schema = "controle")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Codigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Usuario usuario;

    private int codigoRecuperacao;
}
