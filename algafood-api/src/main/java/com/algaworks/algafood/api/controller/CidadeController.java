package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
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
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeDTO> listar(){
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscarCidade(@PathVariable Long cidadeId) {
		Cidades cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);

	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO salvar(@RequestBody @Valid CidadeInput cidadeInput){
		try {
			Cidades cidade = cidadeInputDisassembler.ToDomainObject(cidadeInput);
			return cidadeModelAssembler.toModel(cadastroCidade.cadastrar(cidade));
			
		}catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
		
	}
	
	
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@RequestBody @Valid CidadeInput cidadeInput, @PathVariable Long cidadeId){
		
		try {
			
			Cidades cidadeAtualizada = cadastroCidade.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtualizada);
			
			return cidadeModelAssembler.toModel(cadastroCidade.cadastrar(cidadeAtualizada));
		}catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
		
		
		
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cidadeId){
		cadastroCidade.excluir(buscarCidade(cidadeId).getId());
	}

}
