package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastrarCidadeService cadastrarCidadeService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private CadastrarFormaPagamentoService cadastrarFormaPagamentoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	
	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido).orElseThrow(
				() -> new PedidoNaoEncontradoException(codigoPedido));
	}
	
	@Transactional
	public Pedido emitirPedido (Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calculaTotal();
		
		return pedidoRepository.save(pedido);
	}
	
	
	public void validarPedido(Pedido pedido) {
		Cidades cidade = cadastrarCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento pagamento = cadastrarFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(usuario);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(pagamento);
		
		if (restaurante.naoAceitaFormaPagamento(pagamento)) {
			throw new NegocioException(String.format("Forma de Pagamento %s não é aceita por esse restaurante.", 
					pagamento.getDescricao()));
		}
		
	}
	
	public void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = cadastroProdutoService.buscarOuFalhar(pedido.getRestaurante().getId(), 
					item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
		
	}

}
