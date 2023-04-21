package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;

@Service
public class CadastroFormaPagamento {
	
	@Autowired
	private Forma_PagamentoRepository formaPagamentoRepository;
	
	public Forma_Pagamento cadastrar(Forma_Pagamento pagamento) {
		return formaPagamentoRepository.save(pagamento);
	}
	
	public void deletar(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
		}catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe o codigo %d para pagamento", id)
					);
		}
	}
	

}
