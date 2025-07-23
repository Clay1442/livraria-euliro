package br.com.euliro.livraria.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;

	public List<FieldMessage> erros = new ArrayList<>();

	
	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addError(String fieldName, String message) {  
		erros.add(new FieldMessage(fieldName, message));
	}
	

}
