package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
	}
	
	@Transactional
	public void entrega (String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
		
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
		
		pedido.cancelar();
	}

}
