package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.infrastructure.repository.espec.RestauranteEspecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.espec.RestauranteEspecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/testes")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> buscarPorNome(String nome){
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorNomeId(String nome, Long cozinhaId){
		return restauranteRepository.consultaPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> primeiroRestaurante(String nome){
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> top2Restaurante(String nome){
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/cozinha/exists")
	public boolean cozinhaExistis(String nome){
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/cozinhas/contagem-por-cozinhas")
	public int restauranteCountPorCozinha (Long cozinhaId) {
		return restauranteRepository.countBcountByCozinhaId(cozinhaId);
	}
	
	@GetMapping("restaurantes/por-nome-taxa")
	public List<Restaurante> restaurantePorNomeEFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("restaurantes/com-frete-gratis")
	public List<Restaurante> restauranteComFreteGratis(String nome){
		
		//return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
		return restauranteRepository.findComFreteGratis(nome);
	}
}
