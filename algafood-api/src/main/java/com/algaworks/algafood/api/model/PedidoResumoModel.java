package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")
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
