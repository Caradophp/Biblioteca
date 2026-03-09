package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> listarLivros() {
        return livroService.listarLivros();
    }

    @GetMapping("/{id}")
    public Livro buscarLivroPorId(@PathVariable Long id) {
        return livroService.buscarLivroPorId(id);
    }

    @PostMapping
    public Livro adicionarLivro(@RequestBody Livro livro, @RequestHeader("id_usuario") long idUsuario) {
        return livroService.salvarLivro(livro, idUsuario);
    }

    @PutMapping("/{id}")
    public Livro atualizarUsuario(@RequestBody Livro livro, @PathVariable long id) {
        livro.setId(id);
        return livroService.editarLivro(livro);
    }

    @DeleteMapping("/{id}")
    public void deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
    }

    @GetMapping("/buscar")
    public List<Livro> buscar(@RequestParam String param) {
        return livroService.buscar(param);
    }
}
