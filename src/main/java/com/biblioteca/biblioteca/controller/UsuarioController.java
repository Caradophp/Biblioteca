package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.config.EncoderConfig;
import com.biblioteca.biblioteca.model.TokenResponse;
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

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.editarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        System.out.println("Iniciando login");

        if (request.getNome() == null || request.getSenha() == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Nome e senha não podem ser nulos");
        }

        Optional<Usuario> user = usuarioService.buscarPorNome(request.getNome());

        System.out.println("ROLE DO USUARIO = " + user.get().getTipo_usuario());
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
}
