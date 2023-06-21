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

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastrarFormaPagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository pagamentoRepository;
	
	@Autowired
	private CadastrarFormaPagamentoService cadastroPagamentos;
	
	@Autowired
	private FormaPagamentoAssembler pagamentoAssembler;
	
	@Autowired
	private FormaPagamentoDisassembler pagamentoDisassembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> buscar(){
		return pagamentoAssembler.toCollectionToModel(pagamentoRepository.findAll());
	}
	
	@GetMapping("/{pagamentoId}")
	public FormaPagamentoDTO buscarPorId(@PathVariable Long pagamentoId) {
		return pagamentoAssembler.toModel(cadastroPagamentos.buscarOuFalhar(pagamentoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput pagamentoInput) {
		FormaPagamento pagamento = pagamentoDisassembler.toDomainObject(pagamentoInput);
		return pagamentoAssembler.toModel(cadastroPagamentos.salvar(pagamento));
	}
	
	@PutMapping("/{pagamentoId}")
	public FormaPagamentoDTO atualizar(@RequestBody @Valid FormaPagamentoInput pagamentoInput, 
			@PathVariable Long pagamentoId) {
		
		FormaPagamento pagamento = cadastroPagamentos.buscarOuFalhar(pagamentoId);
		pagamentoDisassembler.copyToDomainObjet(pagamentoInput, pagamento);
		return pagamentoAssembler.toModel(cadastroPagamentos.salvar(pagamento));
		
	}
	
	@DeleteMapping("/{pagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPagamento(@PathVariable Long pagamentoId) {
		
		cadastroPagamentos.excluir(buscarPorId(pagamentoId).getId());
		
	}

}
