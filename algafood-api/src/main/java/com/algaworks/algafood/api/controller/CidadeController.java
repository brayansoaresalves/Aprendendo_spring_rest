package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastrarCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastrarCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidades> listar(){
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidades> buscarCidade(@PathVariable Long cidadeId) {
		Optional<Cidades> cidade = cidadeRepository.findById(cidadeId);
		
		if (!cidade.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(cidade.get());
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidades cidade){
		try {
			cidade = cadastroCidade.cadastrar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@RequestBody Cidades cidade, @PathVariable Long cidadeId){
		Optional<Cidades> cidadeAtualizada = cidadeRepository.findById(cidadeId);
		
		try {
			if (cidadeAtualizada.isPresent()) {
				BeanUtils.copyProperties(cidade, cidadeAtualizada.get(), "id");
				Cidades cidadeSalva = cadastroCidade.cadastrar(cidadeAtualizada.get());
				return ResponseEntity.ok(cidadeSalva);
			}
			
			return ResponseEntity.notFound().build();
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> excluir(@PathVariable Long cidadeId){
		try {
			cadastroCidade.excluir(cidadeId);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}catch (EntidadeEmUsoException ex1) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex1.getMessage());
		}
	}

}
