package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
	
	private static final String MSG_FALHA_REMOVER_PERMISSAO = "A permissao de código %d não "
			+ "pode ser removida pois está vinculada a um grupo";
	
	@Autowired
	public PermissaoRepository permissaoRepository;
	
	public Permissao buscarOuFalhar (Long permissoId) {
		return permissaoRepository.findById(permissoId).orElseThrow(
				() -> new PermissaoNaoEncontradaException(permissoId));
	}
	
	@Transactional
	public Permissao concederPermissao(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	@Transactional
	public void retirarPermissao(Long permissaoId) {
		try {
			permissaoRepository.deleteById(permissaoId);
			permissaoRepository.flush();
		}catch (DataIntegrityViolationException e) {
			throw new NegocioException(MSG_FALHA_REMOVER_PERMISSAO);
		}catch (EmptyResultDataAccessException e) {
			throw new PermissaoNaoEncontradaException(permissaoId);
		}
	}

}
