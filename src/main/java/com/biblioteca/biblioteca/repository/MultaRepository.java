package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Multa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultaRepository extends CrudRepository<Multa, Long> {

    boolean existsByEmprestimo(Emprestimo emprestimo);

}
