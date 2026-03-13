package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name="usuarios", schema = "cadastros")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "escola", nullable = true)
    private String escola;

    @Column(name = "email", length = 60)
    private String email;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipo_usuario;

    @Column(name = "numero_matricula", unique=true, nullable = false)
    private String numeroMatricula;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "id_escola")
    private Escola escolaEntiy;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Escola getEscolaEntiy() {
        return escolaEntiy;
    }

    public void setEscolaEntiy(Escola escolaEntiy) {
        this.escolaEntiy = escolaEntiy;
    }
}
