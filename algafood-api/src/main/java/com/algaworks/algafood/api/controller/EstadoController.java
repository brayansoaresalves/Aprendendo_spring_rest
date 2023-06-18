package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoIdInput;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@GetMapping
	public List<EstadoDTO> listar(){
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable Long id){
		Estado estado = cadastroEstado.buscarOuFalhar(id);
		
		return estadoModelAssembler.toModel(estado);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO salvar(@RequestBody @Valid EstadoInput estadoInput) {
		
		try {
			Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
			
			return estadoModelAssembler.toModel(cadastroEstado.cadastrarEstado(estado));
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long estadoId){
		try {
			
			Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
			estadoInputDisassembler.copyToDomainObject(estadoAtual, estadoInput);
			return estadoModelAssembler.toModel(estadoAtual);
			
		}catch (EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id){
		cadastroEstado.excluir(buscar(id).getId());
	}
	
	
}
