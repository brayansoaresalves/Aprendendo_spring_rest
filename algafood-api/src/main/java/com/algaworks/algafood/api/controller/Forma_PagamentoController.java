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

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamento;

@RestController
@RequestMapping("/pagamentos")
public class Forma_PagamentoController {
	
	@Autowired
	private Forma_PagamentoRepository pagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamento cadastroPagamento;
	
	@GetMapping
	public List<Forma_Pagamento> listar(){
		return pagamentoRepository.findAll();
	}
	
	@GetMapping("/{pagamentoId}")
	public Forma_Pagamento buscarPagamento(@PathVariable Long pagamentoId){
		return cadastroPagamento.buscarOuFalhar(pagamentoId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Forma_Pagamento aderindoPagamento(@RequestBody Forma_Pagamento pagamento){
		return cadastroPagamento.cadastrar(pagamento);
	}
	
	@PutMapping("/{pagamentoId}")
	public Forma_Pagamento atualizarPagamento(@RequestBody Forma_Pagamento pagamento, @PathVariable Long pagamentoId){
		Forma_Pagamento pagamentos = cadastroPagamento.buscarOuFalhar(pagamentoId);
		
		BeanUtils.copyProperties(pagamento, pagamentos, "id");
		return cadastroPagamento.cadastrar(pagamentos);
			
	
	}
	
	@DeleteMapping("/{pagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirPagamento(@PathVariable Long pagamentoId){
		
		cadastroPagamento.deletar(buscarPagamento(pagamentoId).getId());
	}

}
