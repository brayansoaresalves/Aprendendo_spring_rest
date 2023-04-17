package com.algaworks.algafood.api.controller;

import java.util.List;

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

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.listar();
	}
	
	/*@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.listar());
	}*/
	
	//@ResponseStatus(HttpStatus.CREATED)
	/*@GetMapping("/{cozinhaId}")
	private Cozinha buscar(@PathVariable Long cozinhaId) {
		return cozinhaRepository.buscar(cozinhaId);
	}*/
	
	/*@GetMapping("/{id}")
	private ResponseEntity<Cozinha> buscar(@PathVariable Long id){
		Cozinha cozinha = cozinhaRepository.buscar(id);
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		//return ResponseEntity.ok(cozinha);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
		
		return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	}*/
	
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.notFound().build();
		
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar (@RequestBody Cozinha cozinha,@PathVariable Long cozinhaId){
		Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);
		//cozinhaAtual.setNome(cozinha.getNome());
		
		if (cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			cadastroCozinha.salvar(cozinhaAtual);
			
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
		
		
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
		try {
		
			cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();

		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch (EntidadeNaoEncontradaException e1) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
