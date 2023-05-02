package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long restauranteId){
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante, @PathVariable Long id){
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);
		
		try {
			if (restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formasPagamento", "endereco", 
						"dataCadastro", "produtos");
				Restaurante restauranteAtualizando = cadastroRestaurante.salvar(restauranteAtual.get());
				return ResponseEntity.status(HttpStatus.OK).body(restauranteAtual.get());
			}else {
				return ResponseEntity.notFound().build();
			}
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> excluir(@PathVariable Long id){
		try {
			cadastroRestaurante.remover(id);
			return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}catch (EntidadeEmUsoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@RequestBody Map<String, Object> campos, @PathVariable Long id){
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);
		
		if (!restauranteAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual.get());
		
		return atualizar(restauranteAtual.get(), id);
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nome, valor) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nome);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			System.out.println(nome + " = " + valor + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
