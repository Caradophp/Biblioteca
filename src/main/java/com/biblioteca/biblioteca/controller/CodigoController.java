package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.service.CodigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codigo")
public class CodigoController {

    @Autowired
    private CodigoService service;

    @GetMapping("/validar")
    public ResponseEntity<?> validar(@RequestParam int codigo, @RequestParam String email) {
        service.validCode(codigo, email);
        return ResponseEntity.ok().build();
    }

}
