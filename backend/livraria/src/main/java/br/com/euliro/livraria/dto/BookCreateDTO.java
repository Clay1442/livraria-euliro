package br.com.euliro.livraria.dto;

import java.math.BigDecimal;
import java.util.List;

public class BookCreateDTO {
	    private String title;
	    private String description;
	    private BigDecimal price;
	    private int stock;
	    private List<Long> authorIds;
		
	    
	    public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public int getStock() {
			return stock;
		}
		public void setStock(int stock) {
			this.stock = stock;
		}
		public List<Long> getAuthorIds() {
			return authorIds;
		}
		public void setAuthorIds(List<Long> authorIds) {
			this.authorIds = authorIds;
		}
	    
	    
}
