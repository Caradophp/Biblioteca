package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Escola;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolaRepository extends CrudRepository<Escola, Long> {
}
