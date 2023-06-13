package com.algaworks.algafood;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private Flyway flyway;
	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		flyway.migrate();
	}
	
	@Test
	public void testRetornarStatus200_quandoConsultarCozinha() {
		
		RestAssured.given().accept(ContentType.JSON).when()
		.get().then().statusCode(200);
	}
	
	@Test
	public void testconter4cozinhas_quandoConsultarCozinha() {
		
		RestAssured.given().accept(ContentType.JSON).when()
		.get().then().body("", Matchers.hasSize(5))
		.body("nome", Matchers.hasItems("INDIANA", "TAILANDEZA"));
	}
	
	@Test
	public void testRetornarStatus201_quandoCadastrarCozinha() {
		RestAssured.given().body("{\"nome\": \"FRANCESA\"}")
		.contentType(ContentType.JSON).accept(ContentType.JSON).when()
		.post().then().statusCode(HttpStatus.CREATED.value());
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