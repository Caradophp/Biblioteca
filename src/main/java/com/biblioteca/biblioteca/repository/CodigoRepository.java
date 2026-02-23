package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Codigo;
import com.biblioteca.biblioteca.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodigoRepository extends CrudRepository<Codigo, Long> {

    Codigo findByCodigoRecuperacao(int codigoRecuperacao);

    boolean existsByCodigoRecuperacaoAndUsuario(int codigoRecuperacao, Usuario usuario);

}
