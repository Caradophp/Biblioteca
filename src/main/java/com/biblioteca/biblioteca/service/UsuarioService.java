package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.config.EncoderConfig;
import com.biblioteca.biblioteca.dto.UsuarioDTO;
import com.biblioteca.biblioteca.exception.RegraNegocioException;
import com.biblioteca.biblioteca.request.EmailRequest;
import com.biblioteca.biblioteca.response.UsuarioResponse;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.biblioteca.model.Usuario;
import com.biblioteca.biblioteca.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
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
        usuario.setEscola(dto.escola());
        usuario.setNumero_matricula(dto.matricula());
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
        usuario.setEscola(dto.escola());
        usuario.setNumero_matricula(dto.matricula());

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
        int codigo = new Random(10000).nextInt();
        String codigoString = String.valueOf(codigo);
        codigo = Integer.parseInt(codigoString.replace("-", ""));
        emailService.enviarEmailHtml(dto.email(), "Recuperação de Senha", "Prezado(a), <strong>" + usuario.getNome() + "</strong>, segue abaixo o código de recuperação de senha, esse é um e-amil automático, portanto não responda.<br><br>Código: " + codigo);
    }
}

