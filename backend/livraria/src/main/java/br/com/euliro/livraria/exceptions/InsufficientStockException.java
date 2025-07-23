package br.com.euliro.livraria.exceptions;

public class InsufficientStockException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InsufficientStockException(String msg){
		super(msg);
	}
	
}
