package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.response.EmprestimoResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {

    String sql = """
            SELECT 
                e.id_emprestimo, 
                u.nome,
                l.titulo, 
                l.ano 
            FROM 
                emprestimos e 
            INNER JOIN 
                usuarios u 
                    ON u.id = e.usuario_id
            INNER JOIN 
                livros l 
                    ON l.id = e.livro_id
            """;

    @Query(value = sql ,nativeQuery = true)
    List<EmprestimoResponse> buscarEmprestimos();

    Optional<Emprestimo> findByUsuarioAndDevolvidoFalse(Usuario usuario);
}
