package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.config.EncoderConfig;
import com.biblioteca.biblioteca.response.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        String encode = encoderConfig.passwordEncoder().encode(usuario.getSenha());
        usuario.setSenha(encode);
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }
    
    public List<Usuario> buscarUsuario(String nome) {
        return usuarioRepository.buscarUsuario(nome);
    }

    public Usuario editarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<UsuarioResponse> pesquisa(String param) {
        return usuarioRepository.pesuisa(param);
    }
}

