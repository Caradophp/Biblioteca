package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.NotificacaoPorUsuarioDTO;
import com.biblioteca.biblioteca.model.Notificacao;
import com.biblioteca.biblioteca.model.NotificacaoPorUsuario;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.NotificacaoPorUsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class NotificacaoPorUsuarioService {

    @Autowired
    private NotificacaoPorUsuarioRepository notificacaoPorUsuarioRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UsuarioService usuarioService;

    public NotificacaoPorUsuario register(Notificacao notificacao, long idUsuario) {

        NotificacaoPorUsuario notificacaoPorUsuario = new NotificacaoPorUsuario();
        notificacaoPorUsuario.setUsuario(usuarioService.buscarUsuarioPorId(idUsuario));

        Notificacao save = notificacaoService.save(notificacao);

        notificacaoPorUsuario.setNotificacao(save);

        notificacaoPorUsuarioRepository.registerNotificationForAllUsers(notificacao.getId());
        return notificacaoPorUsuarioRepository.save(notificacaoPorUsuario);
    }

    public List<NotificacaoPorUsuarioDTO> findNotificationByUsuario(long idUsuario) {
        List<NotificacaoPorUsuarioDTO> dtos = new LinkedList<>();
        List<NotificacaoPorUsuario> notificacaoByUsuario = notificacaoPorUsuarioRepository.findNotificacaoByUsuarioId(idUsuario);

        notificacaoByUsuario.forEach(f -> {

            String titulo;
            String descricao;
            String state;

            titulo = f.getNotificacao().getTitulo();
            descricao = f.getNotificacao().getDescricao();

            if (!f.getIsDeletada() && !f.getIsVizualizada()) {
                state = "new";
            } else if (f.getIsVizualizada() && !f.getIsDeletada()) {
                state = "old";
            } else {
                state = "del";
            }

            dtos.add(new NotificacaoPorUsuarioDTO(titulo, descricao, state, f.getNotificacao().getId()));
        });

        return dtos;
    }

    @Transactional
    public NotificacaoPorUsuarioDTO vizualizar(long idNotificacao, long idUsuario) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        Notificacao notificacao = notificacaoService.findById(idNotificacao);

        NotificacaoPorUsuario notificacaoPorUsuario = notificacaoPorUsuarioRepository.findByUsuarioAndNotificacao(usuario, notificacao);

        notificacaoPorUsuario.setIsVizualizada(true);

        return new NotificacaoPorUsuarioDTO(notificacaoPorUsuario.getNotificacao().getTitulo(), notificacaoPorUsuario.getNotificacao().getDescricao(), "old", notificacaoPorUsuario.getNotificacao().getId());

    }

    @Transactional
    public NotificacaoPorUsuarioDTO deletar(long idNotificacao, long idUsuario) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        Notificacao notificacao = notificacaoService.findById(idNotificacao);

        NotificacaoPorUsuario notificacaoPorUsuario = notificacaoPorUsuarioRepository.findByUsuarioAndNotificacao(usuario, notificacao);

        notificacaoPorUsuario.setIsDeletada(true);

        return new NotificacaoPorUsuarioDTO(notificacaoPorUsuario.getNotificacao().getTitulo(), notificacaoPorUsuario.getNotificacao().getDescricao(), "del", notificacaoPorUsuario.getNotificacao().getId());

    }

    @Transactional
    public NotificacaoPorUsuarioDTO reciclar(long idNotificacao, long idUsuario) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        Notificacao notificacao = notificacaoService.findById(idNotificacao);

        NotificacaoPorUsuario notificacaoPorUsuario = notificacaoPorUsuarioRepository.findByUsuarioAndNotificacao(usuario, notificacao);

        notificacaoPorUsuario.setIsDeletada(false);

        return new NotificacaoPorUsuarioDTO(notificacaoPorUsuario.getNotificacao().getTitulo(), notificacaoPorUsuario.getNotificacao().getDescricao(), "old", notificacaoPorUsuario.getNotificacao().getId());
    }

    public List<NotificacaoPorUsuarioDTO> pesquisar(String param, long idUsuario) {

        if (param.isBlank()) {
            return findNotificationByUsuario(idUsuario);
        }

        List<NotificacaoPorUsuarioDTO> dtos = new ArrayList<>();
        List<NotificacaoPorUsuario> result = notificacaoPorUsuarioRepository.findByNotificacaoTituloContainingIgnoreCaseOrNotificacaoDescricaoContainingIgnoreCase(param, param);

        List<NotificacaoPorUsuario> resultPerUser = result.stream()
                        .filter(notificacaoPorUsuario -> notificacaoPorUsuario.getUsuario().getId() == idUsuario)
                        .toList();

        resultPerUser.forEach(notificacaoPorUsuario -> {
            String state;
            if (!notificacaoPorUsuario.getIsVizualizada() && !notificacaoPorUsuario.getIsDeletada()) {
                state = "new";
            } else if (notificacaoPorUsuario.getIsVizualizada() && !notificacaoPorUsuario.getIsDeletada()) {
                state = "old";
            } else {
                state = "del";
            }

            dtos.add(new NotificacaoPorUsuarioDTO(notificacaoPorUsuario.getNotificacao().getTitulo(), notificacaoPorUsuario.getNotificacao().getDescricao(), state, notificacaoPorUsuario.getNotificacao().getId()));
        });

        return dtos;
    }

    @Transactional
    public boolean vizualizarTodas(long idUsuario) {
        try {
            for (NotificacaoPorUsuario notificacaoPorUsuario : notificacaoPorUsuarioRepository.findNotificacaoByUsuarioId(idUsuario)) {
                notificacaoPorUsuario.setIsVizualizada(true);
            }

            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    @Transactional
    public boolean deletarTodas(long idUsuario) {
        try {
            for (NotificacaoPorUsuario notificacaoPorUsuario : notificacaoPorUsuarioRepository.findNotificacaoByUsuarioId(idUsuario)) {
                notificacaoPorUsuario.setIsDeletada(true);
            }

            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
