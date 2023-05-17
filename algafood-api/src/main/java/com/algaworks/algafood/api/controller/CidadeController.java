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
	
	@GetMapping
	public List<Cidades> listar(){
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{cidadeId}")
	public Cidades buscarCidade(@PathVariable Long cidadeId) {
		return cadastroCidade.buscarOuFalhar(cidadeId);

	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidades salvar(@RequestBody Cidades cidade){
		try {
			return cadastroCidade.cadastrar(cidade);
		}catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
		
	}
	
	
	@PutMapping("/{cidadeId}")
	public Cidades atualizar(@RequestBody Cidades cidade, @PathVariable Long cidadeId){
		
		try {
			
			Cidades cidadeAtualizada = cadastroCidade.buscarOuFalhar(cidadeId);

			BeanUtils.copyProperties(cidade, cidadeAtualizada, "id");
			
			return cadastroCidade.cadastrar(cidadeAtualizada);
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
