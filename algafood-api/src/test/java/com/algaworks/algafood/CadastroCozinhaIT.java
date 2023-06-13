package com.algaworks.algafood;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Test
	public void deveRetornarStatus200_quandoConsultarCozinha() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.given().basePath("/cozinhas").port(port).accept(ContentType.JSON).when()
		.get().then().statusCode(200);
	}
	
	/*
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenario
		Cozinha novacozinha = new Cozinha();
		novacozinha.setNome("Chineza");
		
		//ação
		novacozinha = cadastroCozinha.salvar(novacozinha);
		//validação
		assertThat(novacozinha).isNotNull();
		assertThat(novacozinha.getId()).isNotNull();
	}
	
	@Test
	public void testarCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> 
		cadastroCozinha.salvar(novaCozinha));
		
		assertThat(erroEsperado).isNotNull();
		
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		
		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinha.excluir(1L);
		});
		
	}
	
	@Test
	public void devefalhar_quandoExcluirCozinhaInexistente() {
		EntidadeNaoEncontradaException erroEsperado = Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> {
			cadastroCozinha.excluir(1000L);
		});
	}
*/
}