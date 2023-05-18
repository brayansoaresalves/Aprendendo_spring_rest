package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastrarPermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {
	
	@Autowired
	private CadastrarPermissaoService cadastrarPermissao;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@GetMapping
	public List<Permissao> buscar(){
		return permissaoRepository.findAll();
	}
	
	@GetMapping("/{permissaoId}")
	public Permissao localizar(@PathVariable Long permissaoId){
		return cadastrarPermissao.buscarOuFalhar(permissaoId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Permissao concederPermissao(@RequestBody Permissao permissao){
		return cadastrarPermissao.salvar(permissao);
	}
	
	@PutMapping("/{permissaoId}")
	public Permissao atualizarPermissao(@RequestBody Permissao permissao, @PathVariable Long permissaoId){
		Permissao outraPermissao = cadastrarPermissao.buscarOuFalhar(permissaoId);

		BeanUtils.copyProperties(permissao, outraPermissao, "id");
		return cadastrarPermissao.salvar(outraPermissao);
		

	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void tirarPermissao(@PathVariable Long permissaoId){

		cadastrarPermissao.excluir(localizar(permissaoId).getId());


	}

}
