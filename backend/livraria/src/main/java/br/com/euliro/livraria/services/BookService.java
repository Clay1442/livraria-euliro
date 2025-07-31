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
import br.com.euliro.livraria.exceptions.ResourceNotFoundException;
import br.com.euliro.livraria.repositories.BookRepository;
import br.com.euliro.livraria.repositories.OrderItemRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private AuthorService authorService;

	// método auxiliar que retorna uma entidade
	public Book findEntityById(Long id) {
		Optional<Book> bookOptional = repository.findByIdAndActiveTrue(id);
		return bookOptional.orElseThrow(() -> new ResourceNotFoundException("Book não encontrado! Id: " + id));
	}

	@Transactional(readOnly = true)
	public List<BookDTO> findAll() {
		List<Book> list = repository.findAllByActiveTrue();
		return list.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public BookDTO findById(Long id) {
		Book optionalBook = findEntityById(id);
		return new BookDTO(optionalBook);
	}

	@Transactional
	public BookDTO create(BookCreateDTO dto) {
		Book newBook = new Book();
		newBook.setTitle(dto.getTitle());
		newBook.setDescription(dto.getDescription());
		newBook.setPrice(dto.getPrice());
		newBook.setImageUrl(dto.getImageUrl());
		newBook.addToStock(dto.getStock());

		for (Long authorId : dto.getAuthorIds()) {
			Author author = authorService.findEntityById(authorId);
			newBook.addAuthor(author);
		}

		Book savedBook = repository.save(newBook);

		return new BookDTO(savedBook);
	}

	public void delete(Long id) {
		Book book = findEntityById(id);

		boolean isBookInAnyOrder = orderItemRepository.existsByBook(book);
		if (isBookInAnyOrder) {
			// Se já foi vendido apenas desativa do client side
			book.setActive(false);
			repository.save(book);
		} else {
			book.getAuthors().clear(); // Limpa a relação M:N entre book e author
			// Se nunca foi vendido é deletado do banco
			repository.delete(book);
		}

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
	public BookDTO addStock(Long id, Integer quantity) {
		Book book = findEntityById(id);
		book.addToStock(quantity);
		Book savedBook = repository.save(book);
		return new BookDTO(savedBook);
	}

	@Transactional
	public BookDTO removeStock(Long id, Integer quantity) {
		Book book = findEntityById(id);
		if (book.getStock() < quantity) {
			throw new IllegalArgumentException("Tentativa de remover " + quantity + " unidades do livro '"
					+ book.getTitle() + "', mas apenas " + book.getStock() + " estão disponíveis. "
					+ "A verificação de negócio deveria ter impedido isso.");
		}
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