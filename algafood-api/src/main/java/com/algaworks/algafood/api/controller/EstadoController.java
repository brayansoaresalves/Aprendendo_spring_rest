package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	public CadastroEstadoService cadastroEstado;
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id){
		return cadastroEstado.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado salvar(@RequestBody Estado estado) {
		return cadastroEstado.cadastrarEstado(estado);
	}
	
	@PutMapping("/{estadoId}")
	public Estado atualizar(@RequestBody Estado estado, @PathVariable Long estadoId){
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return cadastroEstado.cadastrarEstado(estadoAtual);
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id){
		cadastroEstado.excluir(buscar(id).getId());
	}
	
	
}
