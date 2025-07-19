package br.com.euliro.livraria.dto;

public class AddItemRequestDTO {
	    private Long bookId;
	    private int quantity;

	    public Long getBookId() {
	        return bookId;
	    }
	    public void setBookId(Long bookId) {
	        this.bookId = bookId;
	    }
	    public int getQuantity() {
	        return quantity;
	    }
	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }
}
