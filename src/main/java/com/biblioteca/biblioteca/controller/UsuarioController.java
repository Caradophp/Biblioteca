package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.service.UsuarioService;
import com.biblioteca.biblioteca.config.JwtUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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
    public ResponseEntity<?> login(@RequestParam String nome, @RequestParam String senha) {

        if (nome.isEmpty() || senha.isEmpty()) {
            return ResponseEntity.status(403).body("Nome e Senha não podem ser nulos");
        }
        Optional<Usuario> user = usuarioService.buscarPorNome(nome);
        System.out.print(user);

        if (user.isPresent() && user.get().getSenha().equals(senha)) {
            String token = JwtUtil.generateToken(user.get().getNome(), user.get().getTipo_usuario());
            return ResponseEntity.ok(new TokenResponse(token));
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas!");
        }
    }
}
