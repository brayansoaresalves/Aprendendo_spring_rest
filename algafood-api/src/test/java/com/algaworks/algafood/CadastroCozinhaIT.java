package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourcesUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	private static final int ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private int qtdCozinhas;
	
	private Cozinha cozinhaTeste;
	
	private String jsonCorreto;

	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		jsonCorreto = ResourcesUtils.getContentFromResource("/json/correto/cozinha-chineza.json");
		
		databaseCleaner.clearTables();
		prepararDados();
		
		
		
	}
	
	@Test
	public void testRetornarStatus200_quandoConsultarCozinha() {
		
		RestAssured.given().accept(ContentType.JSON).when()
		.get().then().statusCode(200);
	}
	
	@Test
	public void testqtdRealcozinhas_quandoConsultarCozinha() {
		
		RestAssured.given().accept(ContentType.JSON).when()
		.get().then().body("", Matchers.hasSize(qtdCozinhas))
		.body("nome", Matchers.hasItems("BRASILEIRA", "TAILANDEZA"));
	}
	
	@Test
	public void testRetornarStatus201_quandoCadastrarCozinha() {
		RestAssured.given().body(jsonCorreto)
		.contentType(ContentType.JSON).accept(ContentType.JSON).when()
		.post().then().statusCode(HttpStatus.CREATED.value());
	}
	@Test
	public void testRetornarRespostaeStatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured.given().pathParam("cozinhaId", cozinhaTeste.getId())
		.accept(ContentType.JSON).get("/{cozinhaId}").then().statusCode(HttpStatus.OK.value())
		.body("nome", equalTo(cozinhaTeste.getNome()));
	}
	@Test
	public void testRetornarRespostaeStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given().pathParam("cozinhaId", ID_INEXISTENTE)
		.accept(ContentType.JSON).get("/{cozinhaId}").then().statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("TAILANDEZA");
		cozinhaRepository.save(cozinha);
		
		cozinhaTeste = new Cozinha();
		cozinhaTeste.setNome("BRASILEIRA");
		cozinhaRepository.save(cozinhaTeste);
		
		qtdCozinhas = (int)cozinhaRepository.count();
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