package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

    boolean existsByNomeBairroAndNomeRuaAndNumero(String nomeBairro,
                                                  String nomeRua,
                                                  Integer numero);

}
