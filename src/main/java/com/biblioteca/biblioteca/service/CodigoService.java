package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.exception.RegraNegocioException;
import com.biblioteca.biblioteca.model.Codigo;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.CodigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodigoService {

    @Autowired
    private CodigoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    public Codigo register(Codigo codigo) {
        return repository.save(codigo);
    }

    public Codigo validCode(int codigo, String email) {
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
        boolean isValid = repository.existsByCodigoRecuperacaoAndUsuario(codigo, usuario);
        if (!isValid) {
            throw new RegraNegocioException("Código inválido");
        }

        return repository.findByCodigoRecuperacao(codigo);
    }
}
