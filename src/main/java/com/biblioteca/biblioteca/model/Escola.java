package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "escolas", schema = "cadastros")
@Getter
@Setter
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escola")
    private long id;

    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @OneToOne
    private Endereco endereco;

}
