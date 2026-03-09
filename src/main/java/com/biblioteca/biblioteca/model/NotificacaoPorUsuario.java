package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notificacao_por_usuario", schema = "controle", uniqueConstraints = {@UniqueConstraint(columnNames = {"aluno_id", "curso_id"})})
@Getter
@Setter
public class NotificacaoPorUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_notificacao", nullable = false)
    private Notificacao notificacao;

    @Column(name = "is_vizualizada")
    private Boolean isVizualizada = false;

    @Column(name = "id_deletada")
    private Boolean isDeletada = false;

}
