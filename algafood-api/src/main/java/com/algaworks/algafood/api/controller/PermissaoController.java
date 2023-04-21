package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<Permissao> localizar(@PathVariable Long permissaoId){
		Optional<Permissao> permissaoConcedida = permissaoRepository.findById(permissaoId);
		
		if (!permissaoConcedida.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(permissaoConcedida.get());
	}
	
	@PostMapping
	public ResponseEntity<Permissao> concederPermissao(@RequestBody Permissao permissao){
		permissao = cadastrarPermissao.salvar(permissao);
		return ResponseEntity.status(HttpStatus.CREATED).body(permissao);
	}
	
	@PutMapping("/{permissaoId}")
	public ResponseEntity<?> atualizarPermissao(@RequestBody Permissao permissao, @PathVariable Long permissaoId){
		Optional<Permissao> outraPermissao = permissaoRepository.findById(permissaoId);
		
		if (outraPermissao.isPresent()) {
			BeanUtils.copyProperties(permissao, outraPermissao.get(), "id");
			Permissao permissaoSalva = cadastrarPermissao.salvar(outraPermissao.get());
			return ResponseEntity.ok(outraPermissao.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Permissao> tirarPermissao(@PathVariable Long permissaoId){
		try {
			cadastrarPermissao.excluir(permissaoId);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
