package br.com.euliro.livraria.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.entities.Book;
import br.com.euliro.livraria.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;


	public List<Book> findAll() {
		return repository.findAll();
	}

	public Book findById(Long id) {
		Optional<Book> optionalBook = repository.findById(id);
		return optionalBook.orElseThrow(() -> new RuntimeException("Book not found! Id: " + id));
	}

	@Transactional
	public Book insert(Book obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	@Transactional
	public Book updateDescription(Long id, Book newData) {
		Book entity = findById(id);
		updateDescriptionData(entity, newData);
		return repository.save(entity);
	}

	private void updateDescriptionData(Book entity, Book newData) {
		entity.setTitle(newData.getTitle());
		entity.setDescription(newData.getDescription());
	}

	@Transactional
	public Book addStock(Long id, int quantity) {
		Book book = findById(id);
		book.addToStock(quantity);
		return repository.save(book);
	}

	@Transactional
	public Book updatePrice(Long id, BigDecimal newPrice) {
		Book book = findById(id);
		book.updatePrice(newPrice);
		return repository.save(book);
	}
}