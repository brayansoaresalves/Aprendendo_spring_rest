package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastrarPermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao salvar(Permissao permissao) {

		return permissaoRepository.save(permissao);
	}
	
	public void excluir(Long id) {
		try {
			permissaoRepository.deleteById(id);
		}catch (EmptyResultDataAccessException ex) {
			throw new PermissaoNaoEncontradaException(id);
		}
	}
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId).orElseThrow(
				() -> new PermissaoNaoEncontradaException(permissaoId));
	}

}
