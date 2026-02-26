package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.EmprestimoComMultaDTO;
import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.repository.MultaRepository;
import com.biblioteca.biblioteca.response.EmprestimoResponse;
import com.biblioteca.biblioteca.service.EmprestimoService;
import com.biblioteca.biblioteca.utils.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService service;

    @Autowired
    private MultaRepository multaRepository;

    @GetMapping
    public List<EmprestimoResponse> buscarEmprestimos() {
        return service.buscarEmprestimos();
    }

    @GetMapping("/usuario")
    public List<EmprestimoResponse> buscarEmprestimosPorUsuario(@RequestHeader("id_usuario") long idUsuario) {
        return service.buscarEmprestimosPorUsuario(idUsuario);
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
    public EmprestimoComMultaDTO showDevolver(@PathVariable long id) {
        Emprestimo emprestimo = service.carregarDadosDevolucao(id);

        EmprestimoComMultaDTO dto = new EmprestimoComMultaDTO();
        dto.setId(emprestimo.getId());
        dto.setLivro(emprestimo.getLivro());
        dto.setUsuario(emprestimo.getUsuario());
        dto.setDataEmprestimo(emprestimo.getDataEmprestimo());
        dto.setDataDevolucao(emprestimo.getDataDevolucao());
        dto.setDevolvido(emprestimo.isDevolvido());

        long between = ChronoUnit.DAYS.between(emprestimo.getDataDevolucao(), LocalDate.now());
        if (between > 0 && !multaRepository.existsByEmprestimo(emprestimo)) {
            dto.setMultaValor(between * Util.VALOR_MULTA);
        } else {
            dto.setMultaValor(0);
        }

        return dto;
    }

    @PatchMapping("/devolver/{id}")
    public ResponseEntity<?> devolverLivro(@PathVariable long id) {
        service.devolverLivro(id);
        return ResponseEntity.ok(Map.of("aviso", "Devolvido com sucesso"));
    }

    @PatchMapping("/remarcar/{id}")
    public ResponseEntity<?> remarcarComoNapDevolvido(@PathVariable long id) {
        service.marcarNaoDevolvido(id);
        return ResponseEntity.ok(Map.of("aviso", "O empréstimo está marcado como não devolvido a partir de agora"));
    }

    @PatchMapping("/renovar/{id}")
    public ResponseEntity<?> renovar(@PathVariable long id) throws Exception {
        Emprestimo emprestimo = service.renovarEmprestio(id);

        if (emprestimo == null) {
            throw new Exception("Erro inesperado ao renovar emprestimo");
        }

        return ResponseEntity.ok(Map.of("aviso", "Renovado com sucesso!"));

    }

}
