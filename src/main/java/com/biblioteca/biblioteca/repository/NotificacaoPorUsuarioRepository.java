package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Notificacao;
import com.biblioteca.biblioteca.model.NotificacaoPorUsuario;
import com.biblioteca.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificacaoPorUsuarioRepository extends CrudRepository<NotificacaoPorUsuario, Long> {

    List<NotificacaoPorUsuario> findNotificacaoByUsuarioId(long id);

    List<NotificacaoPorUsuario>
    findByNotificacaoTituloContainingIgnoreCaseOrNotificacaoDescricaoContainingIgnoreCase(
            String titulo,
            String descricao
    );

    NotificacaoPorUsuario findByUsuarioAndNotificacao(Usuario usuario, Notificacao notificacao);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO controle.notificacao_por_usuario (id_usuario, id_notificacao)\n" +
            "SELECT id, :idNotificacao\n" +
            "FROM cadastros.usuarios ON CONFLICT DO NOTHING;", nativeQuery = true)
    void registerNotificationForAllUsers(@Param("idNotificacao") long idNotificacao);

}
