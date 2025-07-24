package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name="livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "numero_livro", unique=true, nullable = false)
    private long numero_livro;

    @Column(name = "quantidade_paginas", nullable = false)
    private int quantidade_paginas;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "quantidade_livros", nullable = false)
    private int quantidade_livros;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public long getNumero_livro() {
        return numero_livro;
    }

    public void setNumero_livro(long numero_livro) {
        this.numero_livro = numero_livro;
    }

    public int getQuantidade_paginas() {
        return quantidade_paginas;
    }

    public void setQuantidade_paginas(int quantidade_paginas) {
        this.quantidade_paginas = quantidade_paginas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantidade_livros() {
        return quantidade_livros;
    }

    public void setQuantidade_livros(int quantidade_livros) {
        this.quantidade_livros = quantidade_livros;
    }
}