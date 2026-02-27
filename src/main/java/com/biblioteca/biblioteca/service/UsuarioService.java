package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.config.EncoderConfig;
import com.biblioteca.biblioteca.dto.UsuarioDTO;
import com.biblioteca.biblioteca.exception.RegraNegocioException;
import com.biblioteca.biblioteca.model.Codigo;
import com.biblioteca.biblioteca.repository.CodigoRepository;
import com.biblioteca.biblioteca.request.EmailRequest;
import com.biblioteca.biblioteca.request.RecuperacaoRequest;
import com.biblioteca.biblioteca.response.UsuarioResponse;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.UsuarioRepository;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CodigoRepository codigoRepository;

    @Autowired
    private EscolaService escolaService;
    
    @Autowired
    private EmailService emailService;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario salvarUsuario(UsuarioDTO dto) {

        if (usuarioRepository.findByEmail(dto.email()) != null) {
            throw new RegraNegocioException("Usuário já cadastrado");
        }

        if (!dto.tipoUsuario().equals("aluno") && !dto.tipoUsuario().equals("administrador") && !dto.tipoUsuario().equals("professor")) {
            throw new RegraNegocioException("Tipo de usuário informado não é valido");
        }

        String encode = encoderConfig.passwordEncoder().encode(dto.senha());
        Usuario usuario = new Usuario();
        usuario.setSenha(encode);
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setTipo_usuario(dto.tipoUsuario());
//        usuario.setEscola(dto.escola());
        usuario.setNumero_matricula(dto.matricula());
        usuario.setEscolaEntiy(escolaService.buscarEscolaPorId(dto.escolaId()));
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new RegraNegocioException("Usuário com esse ID não encontrado");
        }

        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }
    
    public List<Usuario> buscarUsuario(String nome) {
        return usuarioRepository.buscarUsuario(nome);
    }

    public Usuario editarUsuario(UsuarioDTO dto) {

        if (!dto.tipoUsuario().equals("aluno") && !dto.tipoUsuario().equals("administrador") && !dto.tipoUsuario().equals("professor")) {
            throw new RegraNegocioException("Tipo de usuário informado não é valido");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setTipo_usuario(dto.tipoUsuario());
//        usuario.setEscola(dto.escola());
        usuario.setNumero_matricula(dto.matricula());
        usuario.setEscolaEntiy(escolaService.buscarEscolaPorId(dto.escolaId()));

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<UsuarioResponse> pesquisa(String param) {
        return usuarioRepository.pesquisa(param);
    }
    
    public void enviarEmailRecuperacao(EmailRequest dto) throws MessagingException{
        Usuario usuario = buscarUsuarioPorEmail(dto.email());
        if (usuario == null) {
            throw new RegraNegocioException("E-mail fornecido não cadastrado");
        }
        double codigo = Math.round(Math.random() * 100000);
        String codigoString = String.valueOf(codigo);
        int codigoInt = Integer.parseInt(codigoString.replace(".0", "").replace("-", ""));
        Codigo c = new Codigo();
        c.setCodigoRecuperacao(codigoInt);
        c.setUsuario(usuario);
        codigoRepository.save(c);
        emailService.enviarEmailHtml(dto.email(), "Recuperação de Senha", "Prezado(a), <strong>" + usuario.getNome() + "</strong>, segue abaixo o código de recuperação de senha, esse é um e-amil automático, portanto não responda.<br><br>Código: " + codigoInt);
    }

    @Transactional
    public boolean mudarSenha(RecuperacaoRequest request) {

        if (!usuarioRepository.existsByEmail(request.email())) {
            throw new RegraNegocioException("E-mail informado não é válido");
        }

        if (!request.senha().equals(request.confirmeSenha())) {
            throw new RegraNegocioException("As senhas informadas devem ser iguais");
        }

        try {
            Usuario usuario = buscarUsuarioPorEmail(request.email());
            String encode = encoderConfig.passwordEncoder().encode(request.senha());

            if (encoderConfig.passwordEncoder().matches(request.senha(), usuario.getSenha())) {
                throw new RegraNegocioException("A nova senha não pode ser igual a anterior");
            }

            usuario.setSenha(encode);
            return true;
        } catch (TransactionException e) {
            e.printStackTrace();
            return false;
        }
    }
}

