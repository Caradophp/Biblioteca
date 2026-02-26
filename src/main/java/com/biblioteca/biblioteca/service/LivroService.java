package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.repository.LivroRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.biblioteca.biblioteca.utils.Util.isLong;

@Service
@SuppressWarnings("unchecked")
public class LivroService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }

    public Livro salvarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro editarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public void deletarLivro(Long id) {
        livroRepository.deleteById(id);
    }

    public List<Livro> buscar(String param) {
        StringBuilder sqlDeBusca = new StringBuilder();
        sqlDeBusca.append("SELECT * FROM arcevo.livros");
        sqlDeBusca.append("     WHERE titulo ILIKE :param OR autor ILIKE :param");
        sqlDeBusca.append("     OR status ILIKE :param");
        sqlDeBusca.append("     OR genero ILIKE :param");

        if (isLong(param)) {
            sqlDeBusca.append("     OR numero_livro = CAST(:param AS BIGINT)");
            sqlDeBusca.append("              OR ano = CAST(:param AS BIGINT)");

            return (List<Livro>) manager
                    .createNativeQuery(sqlDeBusca.toString(), Livro.class)
                    .setParameter("param", param)
                    .getResultList();
        }

        return (List<Livro>) manager
                .createNativeQuery(sqlDeBusca.toString(), Livro.class)
                .setParameter("param", "%" + param + "%")
                .getResultList();
    }
}
