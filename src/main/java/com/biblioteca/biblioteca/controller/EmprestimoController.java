package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.response.EmprestimoResponse;
import com.biblioteca.biblioteca.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService service;

    @GetMapping
    public List<EmprestimoResponse> buscarEmprestimos() {
        return service.buscarEmprestimos();
    }

    @GetMapping("{id}")
    public Emprestimo buscarEmprestimoPorId(@PathVariable long id) {
        return service.buscarEmprestimoPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody EmprestimoDTO dto) {

        if (dto.idLivro() <= 0 || dto.idUsuario() <= 0) {
            return ResponseEntity.badRequest().body(Map.of("message","Usuário e Livro devem ser informados"));
        }

        Emprestimo salvar = service.salvar(dto);

        if (salvar == null) {
            ResponseEntity.internalServerError().body(Map.of("message", "Erro interno não esperado. Contate o Suporte técnico"));
        }
        return ResponseEntity.ok().body(Map.of("message","Empréstimo registrado com sucesso"));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> alterar(@RequestBody EmprestimoDTO dto, @PathVariable long id) {
        Emprestimo salvar = service.atualizar(dto, id);

        if (salvar == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        boolean deletar = service.deletar(id);

        if (deletar) {
            return ResponseEntity.ok("Deletado com sucesso");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public List<EmprestimoResponse> buscar(@RequestParam String param) {
        List<Emprestimo> pesquisar = service.pesquisar(param);
        List<EmprestimoResponse> response = new ArrayList<>();

        pesquisar.forEach( e -> {
            response.add(new EmprestimoResponse(e.getId(), e.getUsuario().getNome(), e.getLivro().getTitulo(), Integer.parseInt(String.valueOf(e.getLivro().getAno()))));
        });

        return response;
    }

    @GetMapping("/devolver/{id}")
    public Emprestimo showDevolver(@PathVariable long id) {
        return service.carregarDadosDevolucao(id);
    }

    @PatchMapping("/devolver/{id}")
    public void devolverLivro(@PathVariable long id) {
        service.devolverLivro(id);
    }

}
