package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping
	public List<PedidoModel> consultarPedidos(){
		return pedidoModelAssembler.toCollectionToModel(pedidoRepository.findAll());
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoModel buscarPedido(@PathVariable Long pedidoId) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(pedidoId);
		
		return pedidoModelAssembler.toModel(pedido);
	}

}
