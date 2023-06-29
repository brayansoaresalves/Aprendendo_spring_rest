package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoModel {
	
	private String codigoPedido;
	private BigDecimal subtotal;
	private BigDecimal TaxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
    private RestauranteResumidoModel restaurante;
    private UsuarioDTO cliente;

}
