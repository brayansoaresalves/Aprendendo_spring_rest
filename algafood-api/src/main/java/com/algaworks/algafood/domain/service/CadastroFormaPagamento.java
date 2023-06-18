package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncotradaException;
import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;

@Service
public class CadastroFormaPagamento {
	
	@Autowired
	private Forma_PagamentoRepository formaPagamentoRepository;
	@Transactional
	public Forma_Pagamento cadastrar(Forma_Pagamento pagamento) {
		return formaPagamentoRepository.save(pagamento);
	}
	@Transactional
	public void deletar(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
		}catch (EmptyResultDataAccessException ex) {
			throw new FormaPagamentoNaoEncotradaException(id);
		}
	}
	
	public Forma_Pagamento buscarOuFalhar(Long formaId) {
		return formaPagamentoRepository.findById(formaId).orElseThrow(
				()-> new FormaPagamentoNaoEncotradaException(formaId));
	}
}
