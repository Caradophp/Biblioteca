package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.model.Notificacao;
import com.biblioteca.biblioteca.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Notificacao save(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    public Notificacao findById(long id) {
        return notificacaoRepository.findById(id).orElseThrow();
    }

}
