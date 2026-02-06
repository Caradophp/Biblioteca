package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;

import java.time.Year;

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
    private long numeroLivro;

    @Column(name = "quantidade_paginas", nullable = false)
    private int quantidadePaginas;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "quantidade_livros", nullable = false)
    private int quantidadeLivros;

    @Column(name = "ano", nullable = false)
    private Year ano;

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

    public long getNumeroLivro() {
        return numeroLivro;
    }

    public void setNumeroLivro(long numeroLivro) {
        this.numeroLivro = numeroLivro;
    }

    public int getQuantidadePaginas() {
        return quantidadePaginas;
    }

    public void setQuantidadePaginas(int quantidadePaginas) {
        this.quantidadePaginas = quantidadePaginas;
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

    public int getQuantidadeLivros() {
        return quantidadeLivros;
    }

    public void setQuantidadeLivros(int quantidadeLivros) {
        this.quantidadeLivros = quantidadeLivros;
    }

    public Year getAno() {
        return ano;
    }

    public void setAno(Year ano) {
        this.ano = ano;
    }

    @PrePersist
    private void setAnoPadrao() {
        this.estado = "novo";
        this.status = "disponivel";
    }
}