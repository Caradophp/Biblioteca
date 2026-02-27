package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Escola;
import com.biblioteca.biblioteca.service.EscolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/escolas")
public class EscolaController {

    @Autowired
    private EscolaService service;

    @GetMapping
    public List<Escola> listarEscolas() {
        return service.buscarEscolas();
    }

}
