package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.EscolaEnderecoDTO;
import com.biblioteca.biblioteca.model.Endereco;
import com.biblioteca.biblioteca.model.Escola;
import com.biblioteca.biblioteca.repository.EnderecoRepository;
import com.biblioteca.biblioteca.repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscolaService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    public Escola registrarEscola(EscolaEnderecoDTO dto) {
        Endereco endereco = dto.endereco();
        Escola escola = dto.escola();

        // revisar
        if (!enderecoRepository.existsByNomeBairroAndNomeRuaAndNumero(endereco.getNomeBairro(), endereco.getNomeRua(), endereco.getNumero())) {
            enderecoRepository.save(endereco);
        }

        escola.setEndereco(endereco);

        Escola save = escolaRepository.save(escola);
        return save;
    }

    public List<Escola> buscarEscolas() {
        return (List<Escola>) escolaRepository.findAll();
    }

    public Escola buscarEscolaPorId(long id) {
        return escolaRepository.findById(id).get();
    }
}
