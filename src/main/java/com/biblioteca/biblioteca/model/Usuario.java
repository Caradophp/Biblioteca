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
    private String numero_matricula;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ManyToOne
    private Escola escolaEntiy;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumero_matricula() {
        return numero_matricula;
    }

    public void setNumero_matricula(String numero_matricula) {
        this.numero_matricula = numero_matricula;
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
