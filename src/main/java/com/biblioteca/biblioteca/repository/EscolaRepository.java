package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.dto.EscolaDTO;
import com.biblioteca.biblioteca.model.Escola;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscolaRepository extends CrudRepository<Escola, Long> {

    String sql = """
                FROM Escola escola
                JOIN escola.endereco endereco
            """;

    @Query(value = sql)
    List<Escola> findWithJoin();

}
