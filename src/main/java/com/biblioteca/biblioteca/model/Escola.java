package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "escolas")
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escola")
    private long id;

    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

}
