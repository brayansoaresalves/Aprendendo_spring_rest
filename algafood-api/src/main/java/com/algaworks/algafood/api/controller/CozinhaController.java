package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaIdInput;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	
	@GetMapping
	public List<CozinhaDTO> listar(){
		return cozinhaModelAssembler.toCollectionModel(cozinhaRepository.findAll());
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		try {
			
			Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
			return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
			
		}catch (EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
		
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar (@RequestBody @Valid CozinhaInput cozinhaInput,@PathVariable Long cozinhaId){
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
		
	}
	/*
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover(@PathVariable Long cozinhaId){
		try {
		
			cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();

		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}*/
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){

		cadastroCozinha.excluir(buscar(cozinhaId).getId());

	}
}
