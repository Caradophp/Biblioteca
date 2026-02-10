package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService service;

    @GetMapping("/enviar-email")
    public String enviar() {
        service.enviarEmail(
                "l35129148@gmail.com",
                "Teste Spring Boot",
                "Olá! Email enviado pelo Spring Boot 😎"
        );
        return "Email enviado!";
    }
}
