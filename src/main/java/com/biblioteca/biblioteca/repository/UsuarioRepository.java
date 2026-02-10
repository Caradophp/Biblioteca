package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNome(String nome);
    
    @Query(value = "SELECT * FROM usuarios WHERE nome ILIKE %:nome%", nativeQuery = true)
    List<Usuario> buscarUsuario(@Param("nome") String nome);

    Usuario findByEmail(String email);
}
