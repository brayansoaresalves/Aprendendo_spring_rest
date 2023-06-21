package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoDTOAssembler grupoAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoDisassembler;
	
	
	@GetMapping
	public List<GrupoDTO> consultarGrupos(){
		return grupoAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		return grupoAssembler.toModel(cadastroGrupoService.buscarOuFalhar(grupoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO cadastrarNovo(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoDisassembler.toDomainObject(grupoInput);
		return grupoAssembler.toModel(cadastroGrupoService.cadastrar(grupo));
	}
	
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizarGrupo (@RequestBody @Valid GrupoInput grupoInput, @PathVariable Long grupoId) {
		Grupo novoGrupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		grupoDisassembler.copyToDomainObject(grupoInput, novoGrupo);
		return grupoAssembler.toModel(cadastroGrupoService.cadastrar(novoGrupo));
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId) {
		cadastroGrupoService.remover(cadastroGrupoService.buscarOuFalhar(grupoId).getId());
	}
}
