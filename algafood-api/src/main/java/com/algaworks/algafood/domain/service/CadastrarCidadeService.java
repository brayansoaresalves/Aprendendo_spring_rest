package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastrarCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidades cadastrar(Cidades cidades) {
		
		Long estadoId = cidades.getEstado().getId();
		Estado estado = estadoRepository.buscarEstadoPorCodigo(estadoId);
		
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de Estado com o codigo %d",estadoId)
					);
		}
		
		cidades.setEstado(estado);
		return cidadeRepository.cadastrarCidade(cidades);
		
	}
	
	public void excluir(Long id) {
		try {
			cidadeRepository.removeCidade(id);
		}catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("A cidade de codígo %d não esta cadastrada", id)
					);
		}
	}

}
