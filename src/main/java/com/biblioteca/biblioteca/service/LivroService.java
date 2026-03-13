package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.Notificacao;
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

    @Autowired
    private NotificacaoPorUsuarioService notificacaoPorUsuarioService;

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }

    public Livro salvarLivro(Livro livro, long idUsuario) {

        Livro save = livroRepository.save(livro);
        Notificacao notificacao = new Notificacao();
        notificacao.setTitulo("Novo Livro Disponível");
        notificacao.setDescricao("Prezado(a) usuário, informamos que o livro " + livro.getTitulo() + ", escrito pelo(a) ilustre " + livro.getAutor() + ". Encontrasse disponível em nossa biblioteca");
        notificacaoPorUsuarioService.register(notificacao, idUsuario);

        return save;
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

    public List<Livro> listarLivrosLimite6() {
        return listarLivros().stream()
                .limit(6)
                .toList();
    }
}
