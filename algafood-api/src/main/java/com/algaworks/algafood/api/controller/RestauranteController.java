package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	public List<RestauranteDTO> listar(){
		return toCollectionModel(restauranteRepository.findAll());
	
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscarPorId(@PathVariable Long restauranteId){
		Restaurante restaurante =  cadastroRestaurante.buscarOuFalhar(restauranteId);

		return toModel(restaurante);

	}

	private RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteModel = new RestauranteDTO();
		restauranteModel.setId(restaurante.getId());
		restauranteModel.setNome(restaurante.getNome());
		restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		restauranteModel.setCozinha(cozinhaDTO);
		return restauranteModel;
	}
	
	private List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes){
		return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
	}
	
	private Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
		try {
			
			Restaurante restaurante = toDomainObject(restauranteInput);
			
			return toModel(cadastroRestaurante.salvar(restaurante));
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long id){
		Restaurante restaurante = toDomainObject(restauranteInput);
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id); 

		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", 
						"dataCadastro", "produtos");
		try {
			return toModel(cadastroRestaurante.salvar(restauranteAtual));
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		

	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id){
		cadastroRestaurante.remover(buscarPorId(id).getId());
	}

	/*@PatchMapping("/{id}")
	public Restaurante atualizarParcial(@RequestBody Map<String, Object> campos, @PathVariable Long id, HttpServletRequest request){
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
		
		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		
		return atualizar(restauranteAtual, id);
	}*/

	/*private void validate(Restaurante restaurante, String objectName) {
		// TODO Auto-generated method stub
		
		BeanPropertyBindingResult bindResult = new BeanPropertyBindingResult(restaurante, objectName);
		
		validator.validate(restaurante, bindResult);
		
		if (bindResult.hasErrors()) {
			throw new ValidacaoException(bindResult);
		}
		
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
			
			camposOrigem.forEach((nome, valor) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nome);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				System.out.println(nome + " = " + valor + " = " + novoValor);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		}catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}*/
	
}
