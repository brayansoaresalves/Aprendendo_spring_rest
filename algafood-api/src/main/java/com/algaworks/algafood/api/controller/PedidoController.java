package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	/*
	@GetMapping
	public MappingJacksonValue listar(@RequestParam (required = false) String campos){
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoResumoModel> pedidosResumoModels = pedidoResumoModelAssembler.toCollectionToModel(pedidos);
		
		MappingJacksonValue pedidoWrapper = new MappingJacksonValue(pedidosResumoModels);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if (StringUtils.isNotEmpty(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.
					filterOutAllExcept(campos.split(",")));
		}
		
		
		pedidoWrapper.setFilters(filterProvider);
		return pedidoWrapper;
	}*/
	
	
	
	@GetMapping
	public List<PedidoResumoModel> consultarPedidos(){
		return pedidoResumoModelAssembler.toCollectionToModel(pedidoRepository.findAll());
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscarPedido(@PathVariable String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel emitirPedido(@RequestBody @Valid PedidoInput pedidoInput) {
		try {
			
			 Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

		        // TODO pegar usuário autenticado
		        novoPedido.setCliente(new Usuario());
		        novoPedido.getCliente().setId(1L);

		        novoPedido = cadastroPedidoService.emitirPedido(novoPedido);
		        
		        return pedidoModelAssembler.toModel(novoPedido);
			
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	

}
