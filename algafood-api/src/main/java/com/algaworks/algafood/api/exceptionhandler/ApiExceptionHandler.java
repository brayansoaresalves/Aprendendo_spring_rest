package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, "
	+ "entre em contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, 
			HttpHeaders headers, HttpStatus status, WebRequest request){
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
	    		.map(objectError -> {
	    		String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
	    		
	    		String name = objectError.getObjectName();
	    		
	    		if (objectError instanceof FieldError) {
	    			name = ((FieldError)objectError).getField();
	    		}
	    		
	    		return Problem.Object.builder()
	    				.name(name)
	    				.userMessage(message)
	    				.build();}).collect(Collectors.toList());
	        
	    Problem problem = createProblemBuilder(status, problemType, detail)
	        .userMessage(detail)
	        .objects(problemObjects)
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

	    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	    
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request){
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUnException(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DO_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
	    
	    ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
	    String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
	            ex.getRequestURL());
	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
	    		.build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	} 
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
	    
	    if (e instanceof MethodArgumentTypeMismatchException) {
	        return handleMethodArgumentTypeMismatch(
	                (MethodArgumentTypeMismatchException) e, headers, status, request);
	    }

	    return super.handleTypeMismatch(e, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

	    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
	    		.build();

	    return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}else if (rootCause instanceof PropertyBindingException) {
			return handleBindException((PropertyBindingException)rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_ENCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleBindException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.PROPRIEDADE_INEXISTENTE;
		 String detail = String.format("A propriedade '%s' não existe. "
		            + "Corrija ou remova essa propriedade e tente novamente.", path);
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		
		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_ENCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s' "
				+ "que é do tipo inválido. Corrija e informe um valor compatível com o tipo "
				+ "%s.", path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradoException(EntidadeNaoEncontradaException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		//String detail = ex.getMessage();
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problema = createProblemBuilder(status, problemType, detail)
				.build();
		
		/*Problem problema = Problem.builder().status(status.value()).type("https://algafood.com.br/entidade-nao-encontrada")
				.title("Entidade não foi encontrada").detail(detail).build();*/
		
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarEstadoNaoEncontradoException(NegocioException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		Problem problema = createProblemBuilder(status, problemType, detail)
				.build();
		
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status , request);
	}
	/*
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException(){
		Problema problema = Problema.builder().dataHora(LocalDateTime.now()).mensagem(""
				+ "O tipo de Media não é aceito").build();
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
	}*/
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problem problema = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status , request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).
					status(status.value()).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).
					build();
		}else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers,status , request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle()).
				detail(detail).timeStamp(OffsetDateTime.now());
	}
}
