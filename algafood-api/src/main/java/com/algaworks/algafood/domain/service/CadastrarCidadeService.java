package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastrarCidadeService {
	
	private static final String MSG_CIDADE_EM_USO 
	= "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	public Cidades cadastrar(Cidades cidades) {
		
		Long estadoId = cidades.getEstado().getId();
		
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		
		cidades.setEstado(estado);
		return cidadeRepository.save(cidades);
		
	}
	
	public void excluir(Long id) {
		try {
			cidadeRepository.deleteById(id);
		}catch (EmptyResultDataAccessException ex) {
			throw new CidadeNaoEncontradaException(id);
		}catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, id));
		}
	}
	
	public Cidades buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(
				() -> new CidadeNaoEncontradaException(
					cidadeId));
	}

}
