package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.NotificacaoPorUsuarioDTO;
import com.biblioteca.biblioteca.model.Notificacao;
import com.biblioteca.biblioteca.model.NotificacaoPorUsuario;
import com.biblioteca.biblioteca.request.NotificacaoIDsRequest;
import com.biblioteca.biblioteca.service.NotificacaoPorUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoPorUsuarioController {

    @Autowired
    private NotificacaoPorUsuarioService notificacaoPorUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<List<NotificacaoPorUsuarioDTO>> getByUser(@PathVariable long id) {
        return ResponseEntity.ok(notificacaoPorUsuarioService.findNotificationByUsuario(id));
    }

    @GetMapping("/pesquisar/{id}")
    public ResponseEntity<List<NotificacaoPorUsuarioDTO>> pequisar(@RequestParam String param, @PathVariable long id) {
        List<NotificacaoPorUsuarioDTO> pesquisar = notificacaoPorUsuarioService.pesquisar(param, id);
        return ResponseEntity.ok(pesquisar);
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<NotificacaoPorUsuario> register(@RequestBody NotificacaoPorUsuarioDTO dto, @PathVariable long id) {
        Notificacao notificacao = new Notificacao();
        notificacao.setTitulo(dto.titulo());
        notificacao.setDescricao(dto.descricao());
        NotificacaoPorUsuario register = notificacaoPorUsuarioService.register(notificacao, id);
        return ResponseEntity.ok(register);
    }

    @PutMapping("/vizualizar")
    public ResponseEntity<NotificacaoPorUsuarioDTO> vizualizar(@RequestBody NotificacaoIDsRequest request) {
        NotificacaoPorUsuarioDTO vizualizar = notificacaoPorUsuarioService.vizualizar(request.idNotificacao(), request.idUsuario());
        return ResponseEntity.ok(vizualizar);
    }


    @PutMapping("/deletar")
    public ResponseEntity<NotificacaoPorUsuarioDTO> deletar(@RequestBody NotificacaoIDsRequest request) {
        NotificacaoPorUsuarioDTO deletar = notificacaoPorUsuarioService.deletar(request.idNotificacao(), request.idUsuario());
        return ResponseEntity.ok(deletar);
    }

    @PutMapping("/reciclar")
    public ResponseEntity<NotificacaoPorUsuarioDTO> reciclar(@RequestBody NotificacaoIDsRequest request) {
        NotificacaoPorUsuarioDTO reciclar = notificacaoPorUsuarioService.reciclar(request.idNotificacao(), request.idUsuario());
        return ResponseEntity.ok(reciclar);
    }

    @PutMapping("/vizualizar/todas")
    public ResponseEntity<NotificacaoPorUsuarioDTO> vizualizarTodas(@RequestBody NotificacaoIDsRequest request) {
        notificacaoPorUsuarioService.vizualizarTodas(request.idUsuario());
        return ResponseEntity.ok().build();
    }


    @PutMapping("/deletar/todas")
    public ResponseEntity<NotificacaoPorUsuarioDTO> deletarTodas(@RequestBody NotificacaoIDsRequest request) {
        notificacaoPorUsuarioService.deletarTodas(request.idUsuario());
        return ResponseEntity.ok().build();
    }

}
