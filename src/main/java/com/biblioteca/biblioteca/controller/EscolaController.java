package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.EscolaDTO;
import com.biblioteca.biblioteca.dto.EscolaEnderecoDTO;
import com.biblioteca.biblioteca.model.Escola;
import com.biblioteca.biblioteca.service.EscolaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/escolas")
public class EscolaController {

    @Autowired
    private EscolaService service;

    @GetMapping
    public List<Escola> listarEscolas() {
        return service.findWithJoin();
    }

    @PostMapping
    public Escola registrarEscola(@Valid @RequestBody EscolaEnderecoDTO dto) {
        return service.registrarEscola(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody EscolaEnderecoDTO dto, @PathVariable long id) {
        service.atualizar(dto, id);
        return ResponseEntity.ok(Map.of("aviso","Atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        boolean deletar = service.deletar(id);

        if (deletar) {
            return ResponseEntity.ok(Map.of("aviso", "Deletado com sucesso"));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
