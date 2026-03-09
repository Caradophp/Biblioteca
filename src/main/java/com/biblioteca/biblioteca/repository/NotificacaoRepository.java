package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Notificacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends CrudRepository<Notificacao, Long> {
}
