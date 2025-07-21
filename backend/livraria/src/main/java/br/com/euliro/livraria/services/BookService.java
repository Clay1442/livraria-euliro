package br.com.euliro.livraria.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.dto.BookCreateDTO;
import br.com.euliro.livraria.dto.BookDTO;
import br.com.euliro.livraria.dto.BookUpdateDTO;
import br.com.euliro.livraria.entities.Author;
import br.com.euliro.livraria.entities.Book;
import br.com.euliro.livraria.repositories.AuthorRepository;
import br.com.euliro.livraria.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	@Autowired
	private AuthorRepository authorRepository;

	// metodo privado auxiliar que retorna um order
	private Book findEntityById(Long id) {
		Optional<Book> bookOptional = repository.findById(id);
		return bookOptional.orElseThrow(() -> new RuntimeException("Livro não encontrado! Id: " + id));
	}

	public List<BookDTO> findAll() {
		List<Book> list = repository.findAll();
		return list.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());
	}

	public BookDTO findById(Long id) {
		Book optionalBook = findEntityById(id);
		return new BookDTO(optionalBook);
	}

	@Transactional
	public Book create(BookCreateDTO dto) {
		Book newBook = new Book();
		newBook.setTitle(dto.getTitle());
		newBook.setDescription(dto.getDescription());
		newBook.setPrice(dto.getPrice());
		newBook.addToStock(dto.getStock());

		for (Long authorId : dto.getAuthorIds()) {
			Author author = authorRepository.findById(authorId)
					.orElseThrow(() -> new RuntimeException("Autor não encontrado: " + authorId));
			newBook.addAuthor(author);
		}

		Book savedBook = repository.save(newBook);

		return repository.save(savedBook);
	}

	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	@Transactional
	public BookUpdateDTO updateDescription(Long id, BookUpdateDTO newData) {
		Book entity = findEntityById(id);
		updateDescriptionData(entity, newData);
		Book savedBook = repository.save(entity);
		return new BookUpdateDTO(savedBook);
	}

	private void updateDescriptionData(Book entity, BookUpdateDTO newData) {
		entity.setTitle(newData.getTitle());
		entity.setDescription(newData.getDescription());
	}

	@Transactional
	public BookDTO addStock(Long id, int quantity) {
		Book book = findEntityById(id);
		book.addToStock(quantity);
		Book savedBook = repository.save(book);
		return new BookDTO(savedBook);
	}

	@Transactional
	public BookDTO removeStock(Long id, int quantity) {
		Book book = findEntityById(id);
		book.removeFromStock(quantity);
		Book savedBook = repository.save(book);
		return new BookDTO(savedBook);
	}

	@Transactional
	public BookDTO updatePrice(Long id, BigDecimal newPrice) {
		Book book = findEntityById(id);
		book.updatePrice(newPrice);
		Book savedBook = repository.save(book);
		return new BookDTO(savedBook);
	}
}