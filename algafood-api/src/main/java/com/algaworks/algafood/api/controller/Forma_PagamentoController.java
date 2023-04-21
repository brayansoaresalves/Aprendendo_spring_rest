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
	public ResponseEntity<Forma_Pagamento> buscarPagamento(@PathVariable Long pagamentoId){
		Optional<Forma_Pagamento> pagamentos = pagamentoRepository.findById(pagamentoId);
		
		if (!pagamentos.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pagamentos.get());
	}
	
	@PostMapping
	public ResponseEntity<Forma_Pagamento> aderindoPagamento(@RequestBody Forma_Pagamento pagamento){
		pagamento = cadastroPagamento.cadastrar(pagamento);
		return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
	}
	
	@PutMapping("/{pagamentoId}")
	public ResponseEntity<?> atualizarPagamento(@RequestBody Forma_Pagamento pagamento, @PathVariable Long pagamentoId){
		Optional<Forma_Pagamento> pagamentos = pagamentoRepository.findById(pagamentoId);
		
		if (pagamentos.isPresent()) {
			BeanUtils.copyProperties(pagamento, pagamentos.get(), "id");
			cadastroPagamento.cadastrar(pagamentos.get());
			return ResponseEntity.ok(pagamentos.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{pagamentoId}")
	public ResponseEntity<Forma_Pagamento> excluirPagamento(@PathVariable Long pagamentoId){
		try {
			cadastroPagamento.deletar(pagamentoId);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

}
