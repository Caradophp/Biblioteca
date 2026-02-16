package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.response.UsuarioResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNome(String nome);
    
    @Query(value = "SELECT * FROM usuarios WHERE nome ILIKE %:nome%", nativeQuery = true)
    List<Usuario> buscarUsuario(@Param("nome") String nome);

    @Query(value = "SELECT nome, escola, numero_matricula AS matricula FROM usuarios WHERE nome ILIKE %:param% OR escola ILIKE %:param% OR numero_matricula ILIKE %:param%", nativeQuery = true)
    List<UsuarioResponse> pesquisa(@Param("param") String param);

    Usuario findByEmail(String email);
}
