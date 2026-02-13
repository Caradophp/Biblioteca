package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.config.EncoderConfig;
import com.biblioteca.biblioteca.dto.UsuarioDTO;
import com.biblioteca.biblioteca.model.TokenResponse;
import com.biblioteca.biblioteca.request.EmailRequest;
import com.biblioteca.biblioteca.response.UsuarioResponse;
import com.biblioteca.biblioteca.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.model.LoginRequest;
import com.biblioteca.biblioteca.service.UsuarioService;
import com.biblioteca.biblioteca.config.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Log log = LogFactory.getLog(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EncoderConfig encoder;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }
    
    @PostMapping
    public Usuario adicionarUsuario(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.salvarUsuario(dto);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@Valid @RequestBody UsuarioDTO usuario) {
        return usuarioService.editarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request.getNome() == null || request.getSenha() == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Nome e senha não podem ser nulos");
        }

        Optional<Usuario> user = usuarioService.buscarPorNome(request.getNome());

        if (user.isPresent() && encoder.passwordEncoder().matches(request.getSenha(), user.get().getSenha())) {

            String token = JwtUtil.generateToken(
                    user.get().getNome(),
                    user.get().getTipo_usuario() // administrador, professor, aluno
            );

            return ResponseEntity.ok(new TokenResponse(token));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Credenciais inválidas");
    }

    @PostMapping("/validar")
    public ResponseEntity<?> isTokenValid(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        boolean tokenValid = JwtUtil.isTokenValid(token);

        if (tokenValid) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<?> recuperarSenha(@RequestBody EmailRequest email) {
        try {
            usuarioService.enviarEmailRecuperacao(email);
            return ResponseEntity.ok().build();
        } catch (MessagingException e) {
            log.error("Erro ao enviar E-mail de recuperação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar")
    public List<UsuarioResponse> buscar(@RequestParam String param) {
        return usuarioService.pesquisa(param);
    }
}
