package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.EmprestimoComMultaDTO;
import com.biblioteca.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.exception.AvisosException;
import com.biblioteca.biblioteca.exception.RegraNegocioException;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.biblioteca.repository.MultaRepository;
import com.biblioteca.biblioteca.response.EmprestimoResponse;
import com.biblioteca.biblioteca.utils.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@SuppressWarnings("unchecked")
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    @Autowired
    private MultaRepository multaRepository;

    @PersistenceContext
    private EntityManager manager;

    public Emprestimo salvar(EmprestimoDTO dto) {

        if (dto.idLivro() <= 0 || dto.idUsuario() <= 0) {
            throw new RuntimeException("Usuário ou Livro não infromado ao registrar emprestimo");
        }

        Usuario usuario = usuarioService.buscarUsuarioPorId(dto.idUsuario());
        Livro livro = livroService.buscarLivroPorId(dto.idLivro());

        if (livro.getQuantidadeLivros() <= 0) {
            throw new RegraNegocioException("Livro não disponível para emprestar");
        }

        if (repository.findByUsuarioAndDevolvidoFalse(usuario).isPresent()) {
            throw new RegraNegocioException("Esse usuário já possui um emprestimo ativo. Portanto não pode realizar outro");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);

        int i = livro.getQuantidadeLivros() - 1;
        livro.setQuantidadeLivros(i);
        return repository.save(emprestimo);
    }

    @Transactional
    public Emprestimo atualizar(EmprestimoDTO dto, long id) {

        Emprestimo emprestimo = buscarEmprestimoPorId(id);

        if (emprestimo == null) {
            throw new RegraNegocioException("Emprestimo não encontrado");
        }

        emprestimo.setUsuario(usuarioService.buscarUsuarioPorId(dto.idUsuario()));
        emprestimo.setLivro(livroService.buscarLivroPorId(dto.idLivro()));

        return emprestimo;
    }

    public List<EmprestimoResponse> buscarEmprestimos() {
        return repository.buscarEmprestimos();
    }

    public List<EmprestimoResponse> buscarEmprestimosPorUsuario(long id) {
        return repository.buscarEmprestimosPorUsuario(id);
    }

    public Emprestimo buscarEmprestimoPorId(long id) {
        return repository.findById(id).get();
    }

    public boolean deletar(long id) {

        if (!repository.existsById(id)) {
            throw new RegraNegocioException("ID não encontrado");
        }

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
        sqlDeBusca.append("SELECT * FROM circulacao.emprestimos e");
        sqlDeBusca.append("     INNER JOIN cadastros.usuarios u ON e.usuario_id = u.id");
        sqlDeBusca.append("     INNER JOIN arcevo.livros l ON e.livro_id = l.id");
        sqlDeBusca.append("     WHERE u.nome ILIKE :param or l.titulo ILIKE :param;");

        return (List<Emprestimo>) manager.createNativeQuery(sqlDeBusca.toString(), Emprestimo.class)
                .setParameter("param", "%" + param + "%")
                .getResultList();
    }

    public Emprestimo carregarDadosDevolucao(long id) {

        if (!repository.existsById(id)) {
            throw new RegraNegocioException("ID não encontrado");
        }

        return buscarEmprestimoPorId(id);
    }

    @Transactional
    public void devolverLivro(long id) {

        Emprestimo emprestimo = buscarEmprestimoPorId(id);

        if (ChronoUnit.DAYS.between(emprestimo.getDataDevolucao(), LocalDate.now()) > 0 && !multaRepository.existsByEmprestimo(emprestimo)) {
            throw new RegraNegocioException("Existe uma multa a ser paga pelo usuário " + emprestimo.getUsuario().getNome());
        }

        emprestimo.setDevolvido(true);

        emprestimo.getLivro().setQuantidadeLivros(emprestimo.getLivro().getQuantidadeLivros() + 1);
    }

    @Transactional
    public void marcarNaoDevolvido(long id) {
        Emprestimo emprestimo = buscarEmprestimoPorId(id);
        emprestimo.setDevolvido(false);

        emprestimo.getLivro().setQuantidadeLivros(emprestimo.getLivro().getQuantidadeLivros() + 1);
    }

    @Transactional
    public Emprestimo renovarEmprestio(long id) {
        Emprestimo emprestimo = buscarEmprestimoPorId(id);
        emprestimo.setDataDevolucao(emprestimo.getDataDevolucao().plusDays(10));
        return emprestimo;
    }

    public EmprestimoComMultaDTO verificarEmprestimoPendentes(long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        Emprestimo e = repository.findByUsuario(usuario)
                .stream()
                .filter(emprestimo -> !emprestimo.isDevolvido() && (ChronoUnit.DAYS.between(emprestimo.getDataDevolucao(), LocalDate.now()) > 0))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(e)) {
            throw new AvisosException("Usuário não possuí pendencias");
        }

        float valorMulta = ChronoUnit.DAYS.between(e.getDataDevolucao(), LocalDate.now()) * Util.VALOR_MULTA;
        return new EmprestimoComMultaDTO(e.getId(), e.getUsuario(), e.getLivro(), e.isDevolvido(), valorMulta, e.getDataEmprestimo(), e.getDataDevolucao());
    }

}
