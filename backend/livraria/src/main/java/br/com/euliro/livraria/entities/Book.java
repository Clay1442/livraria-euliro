package br.com.euliro.livraria.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_books") 
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String description;
	private BigDecimal price;
	private int stock;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "tb_book_author", 
		joinColumns = @JoinColumn(name = "book_id"), 
		inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> authors = new HashSet<>();

	public Book() {
	}

	public Book(Long id, String title, String description, BigDecimal price, int stock) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	
	public int getStock() {
		return stock;
	}


	protected void setStock(int stock) {
		this.stock = stock;
	}

	public void addAuthor(Author author) {
	    this.authors.add(author);
	    author.getBooks().add(this);
	}

	public void removeAuthor(Author author) {
	    this.authors.remove(author);
	    author.getBooks().remove(this); 
	}
	
	public Set<Author> getAuthors() {
		return authors;
	}

	// Business Methods
	public void addToStock(int amount) {
		if (amount > 0) {
			this.stock += amount;
		}
	}

	public boolean removeFromStock(int quantity) {
		if (quantity > 0 && this.stock >= quantity) {
			this.stock -= quantity;
			return true;
		}
		return false;
	}

	public void updatePrice(BigDecimal newPrice) {
		if (newPrice != null && newPrice.compareTo(BigDecimal.ZERO) >= 0) {
			this.price = newPrice; 
		} else {
            throw new IllegalArgumentException("Price cannot be null or negative.");
        }
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(id, other.id);
	}
}