package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastrarFormaPagamentoService {
	
	private static final String MSG_PAGAMENTO_EM_USO 
	= "Pagamento de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private FormaPagamentoRepository pagamentoRepository;
	
	
	public List<FormaPagamento> listar(){
		return pagamentoRepository.findAll();
	}
	
	public FormaPagamento buscarOuFalhar(Long pagamentoId) {
		return pagamentoRepository.findById(pagamentoId).orElseThrow(
				() -> new FormaPagamentoNaoEncontradoException(pagamentoId)
				);
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento pagamento) {
		return pagamentoRepository.save(pagamento);
	}
	
	@Transactional
	public void excluir (Long id) {
		try {
			pagamentoRepository.deleteById(id);
			pagamentoRepository.flush();
		}catch (EmptyResultDataAccessException ex) {
			throw new FormaPagamentoNaoEncontradoException(id);
		}catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(String.format(MSG_PAGAMENTO_EM_USO, id));
		}
	}

}
