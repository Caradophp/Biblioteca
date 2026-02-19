package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.dto.MultaDTO;
import com.biblioteca.biblioteca.model.Multa;
import com.biblioteca.biblioteca.service.MultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/multa")
public class MultaController {

    @Autowired
    private MultaService service;

    @PostMapping("/pagar")
    public ResponseEntity<?> pagarMulta(@RequestBody MultaDTO multa) {
        Multa multaRegistrada = service.registrarMulta(multa);

        if (multaRegistrada == null) {
            return ResponseEntity.internalServerError().body(Map.of("aviso", "Erro inesperado ao registrar pagamento de multa. Contate o suporte técnico"));
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/erro/humano")
    public ResponseEntity<?> erroHumano(@RequestBody MultaDTO multa) {
        Multa multaRegitrada = service.registraErroHumano(multa);

        if (multaRegitrada == null) {
            return ResponseEntity.internalServerError().body(Map.of("aviso", "Erro inesperado ao registrar erro humano. Contate o suporte técnico"));
        }

        return ResponseEntity.ok().build();
    }

}
