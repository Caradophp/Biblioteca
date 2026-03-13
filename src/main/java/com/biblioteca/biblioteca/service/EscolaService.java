package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.EscolaDTO;
import com.biblioteca.biblioteca.dto.EscolaEnderecoDTO;
import com.biblioteca.biblioteca.exception.RegraNegocioException;
import com.biblioteca.biblioteca.model.Endereco;
import com.biblioteca.biblioteca.model.Escola;
import com.biblioteca.biblioteca.repository.EnderecoRepository;
import com.biblioteca.biblioteca.repository.EscolaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EscolaService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    @PersistenceContext
    private EntityManager manager;

    public Escola registrarEscola(EscolaEnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setEstado(dto.endereco().estado());
        endereco.setNumero(Integer.parseInt(dto.endereco().numero()));
        endereco.setNomeRua(dto.endereco().nomeRua());
        endereco.setNomeBairro(dto.endereco().nomeBairro());
        endereco.setMunicipio(dto.endereco().municipio());
        Escola escola = new Escola();
        escola.setNome(dto.escola().nome());

        // revisar
        if (!enderecoRepository.existsByNomeBairroAndNomeRuaAndNumero(endereco.getNomeBairro(), endereco.getNomeRua(), endereco.getNumero())) {
            Endereco save = enderecoRepository.save(endereco);
            escola.setEndereco(save);
        }

        return escolaRepository.save(escola);
    }

    public List<Escola> buscarEscolas() {
        List<Escola> all = (List<Escola>) escolaRepository.findAll();

        return all.stream()
                .sorted(Comparator.comparing(Escola::getId))
                .toList();
    }

    public List<Escola> findWithJoin() {
        return escolaRepository.findWithJoin();
    }

    public Escola buscarEscolaPorId(long id) {
        return escolaRepository.findById(id).orElseThrow(() -> new RegraNegocioException("ID informado não encontrado"));
    }

    @Transactional
    public Escola atualizar(EscolaEnderecoDTO dto, long id) {
        Optional<Escola> byId = escolaRepository.findById(id);

        Escola escola = byId.orElseThrow(() -> new RegraNegocioException("ID informado é inválido"));
        Endereco endereco = escola.getEndereco();

        escola.setNome(dto.escola().nome());
        endereco.setEstado(dto.endereco().estado());
        endereco.setMunicipio(dto.endereco().municipio());
        endereco.setNomeBairro(dto.endereco().nomeBairro());
        endereco.setNomeRua(dto.endereco().nomeRua());
        endereco.setNumero(Integer.parseInt(dto.endereco().numero()));

        escola.setEndereco(endereco);

        return escola;

    }

    public boolean deletar(long id) {
        try {
            Escola escola = buscarEscolaPorId(id);
            escolaRepository.delete(escola);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public List<Escola> pesquisar(String param) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM cadastros.escolas es");
        builder.append("    JOIN cadastros.enderecos en");
        builder.append("    ON es.endereco_id = en.id");
        builder.append("    WHERE es.nome ILIKE :param OR");
        builder.append("    en.estado ILIKE :param OR");
        builder.append("    en.municipio ILIKE :param");

        return (List<Escola>) manager.createNativeQuery(builder.toString(), Escola.class)
                .setParameter("param", "%" + param + "%")
                .getResultList();
    }
}
