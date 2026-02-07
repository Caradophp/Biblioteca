package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long>{

    String sqlBuscaGenerica = """
            SELECT * FROM livros
            WHERE titulo ILIKE :param OR autor ILIKE :param
            """;

    @Query(value = sqlBuscaGenerica, nativeQuery = true)
    List<Livro> buscarLivros(@Param("param") String param);

}
