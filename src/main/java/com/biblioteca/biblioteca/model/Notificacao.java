package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notificacoes", schema = "controle")
@Getter
@Setter
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacao")
    private Long id;

    @Column(nullable = false, length = 50)
    private String titulo;

    @Column(nullable = false, columnDefinition = "text")
    private String descricao;

}
