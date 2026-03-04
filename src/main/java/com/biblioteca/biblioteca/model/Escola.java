package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "escolas", schema = "cadastros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escola")
    private Long id;

    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

}
