package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.biblioteca.response.EmprestimoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    public Emprestimo salvar(EmprestimoDTO dto) {

        if (dto.idLivro() <= 0 || dto.idUsuario() <= 0) {
            throw new RuntimeException("Usuaário ou Livro não infromado ao registrar emprestimo");
        }

        Usuario usuario = usuarioService.buscarUsuarioPorId(dto.idUsuario());
        Livro livro = livroService.buscarLivroPorId(dto.idLivro());

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);

        return repository.save(emprestimo);
    }

    public List<EmprestimoResponse> buscarEmprestimos() {
        return repository.buscarEmprestimos();
    }

}
