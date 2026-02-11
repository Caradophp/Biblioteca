package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.biblioteca.response.EmprestimoResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    @PersistenceContext
    private EntityManager manager;

    public Emprestimo salvar(EmprestimoDTO dto) {

        if (dto.idLivro() <= 0 || dto.idUsuario() <= 0) {
            throw new RuntimeException("Usuaário ou Livro não infromado ao registrar emprestimo");
        }

        Usuario usuario = usuarioService.buscarUsuarioPorId(dto.idUsuario());
        Livro livro = livroService.buscarLivroPorId(dto.idLivro());

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);

        return repository.save(emprestimo);
    }

    public List<EmprestimoResponse> buscarEmprestimos() {
        return repository.buscarEmprestimos();
    }

    public boolean deletar(long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Emprestimo> pesquisar(String param) {
        StringBuilder sqlDeBusca = new StringBuilder();
        sqlDeBusca.append("SELECT * FROM emprestimos e");
        sqlDeBusca.append("     INNER JOIN usuarios u ON e.usuario_id = u.id");
        sqlDeBusca.append("     INNER JOIN livros l ON e.livro_id = l.id");
        sqlDeBusca.append("     WHERE u.nome ILIKE :param or l.titulo ILIKE :param;");

        return (List<Emprestimo>) manager.createNativeQuery(sqlDeBusca.toString(), Emprestimo.class)
                .setParameter("param", "%" + param + "%")
                .getResultList();
    }

}
