package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.dto.MultaDTO;
import com.biblioteca.biblioteca.exception.RegraNegocioException;
import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Multa;
import com.biblioteca.biblioteca.repository.MultaRepository;
import com.biblioteca.biblioteca.utils.enums.FormaPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MultaService {

    @Autowired
    private MultaRepository repository;

    @Autowired
    private EmprestimoService emprestimoService;

    public Multa registrarMulta(MultaDTO dto) {
        Multa multa = new Multa();
        multa.setEmprestimo(emprestimoService.buscarEmprestimoPorId(dto.idEmprestimo()));
        multa.setMultaValor(dto.multaValor());

        if (dto.tipoPagamento().equals("pix")) {
            multa.setFormaPagamento(FormaPagamento.PIX);
        } else if (dto.tipoPagamento().equals("credito")) {
            multa.setFormaPagamento(FormaPagamento.CREDITO);
        } else if (dto.tipoPagamento().equals("debito")) {
            multa.setFormaPagamento(FormaPagamento.DEBITO);
        } else if (dto.tipoPagamento().equals("dinheiro")) {
            multa.setFormaPagamento(FormaPagamento.DINHEIRO);
        } else {
            throw new RegraNegocioException("Forma de pagamento selecionada não é válida");
        }

        return repository.save(multa);
    }

    @Transactional
    public Multa registraErroHumano(MultaDTO dto) {
        Multa multa = registrarMulta(dto);
        multa.setErroHumano(true);
        return multa;
    }

    public boolean isPaid(Emprestimo emprestimo) {
        return repository.existsByEmprestimo(emprestimo);
    }
}
