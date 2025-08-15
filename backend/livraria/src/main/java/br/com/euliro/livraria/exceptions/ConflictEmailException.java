package br.com.euliro.livraria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictEmailException extends RuntimeException{
	private static final long serialVersionUID = 1L;
    
	public ConflictEmailException(String message){
		super(message);
	}
}
