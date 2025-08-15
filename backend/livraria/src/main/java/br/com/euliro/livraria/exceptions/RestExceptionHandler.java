package br.com.euliro.livraria.exceptions;

import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(ex.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation error");
		err.setMessage("Um ou mais campos estão inválidos.");
		err.setPath(request.getRequestURI());

		for (FieldError f : ex.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> validation(DataIntegrityViolationException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Data Integrity Violation");
		err.setMessage("Este recurso não pode ser excluído pois está em uso por outra parte do sistema.");
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> genericException(Exception ex, HttpServletRequest request) {
	    
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	    
	    StandardError err = new StandardError();
	    err.setTimestamp(Instant.now());
	    err.setStatus(status.value());
	    err.setError("Erro Interno do Servidor");
	    err.setMessage("Ocorreu um erro inesperado no sistema. Tente novamente mais tarde.");
	    err.setPath(request.getRequestURI());
	    
	    System.err.println("ERRO INESPERADO: " + ex.getMessage());
	    ex.printStackTrace();
	    
	    return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<StandardError> genericException(InsufficientStockException ex, HttpServletRequest request) {
	    
	    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
	    
	    StandardError err = new StandardError();
	    err.setTimestamp(Instant.now());
	    err.setStatus(status.value());
	    err.setError("Erro de Negócio: Estoque Insuficiente");
	    err.setMessage(ex.getMessage());
	    err.setPath(request.getRequestURI());
	    
	    return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ConflictEmailException.class)
	public ResponseEntity<StandardError> conflictEmailException(ConflictEmailException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Email já cadastrado");
		err.setMessage(ex.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	
}
