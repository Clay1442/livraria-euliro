package br.com.euliro.livraria.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.dto.AuthorCreateDTO;
import br.com.euliro.livraria.dto.AuthorDTO;
import br.com.euliro.livraria.entities.Author;
import br.com.euliro.livraria.exceptions.ResourceNotFoundException;
import br.com.euliro.livraria.repositories.AuthorRepository;
import br.com.euliro.livraria.repositories.BookRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository repository;

	@Autowired
	private BookRepository bookRepository;

	// metodo privado auxiliar que retorna uma entidade
	public Author findEntityById(Long id) {
		Optional<Author> bookOptional = repository.findByIdAndActiveTrue(id);
		return bookOptional.orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado! Id: " + id));
	}

	@Transactional(readOnly = true)
	public Set<AuthorDTO> findAll() {
		List<Author> list = repository.findAllByActiveTrue();
		return list.stream().map(author -> new AuthorDTO(author)).collect(Collectors.toSet());
	}

	@Transactional(readOnly = true)
	public AuthorDTO findById(Long id) {
		Author authorOptional = findEntityById(id);
		return new AuthorDTO(authorOptional);
	}

	public Author create(AuthorCreateDTO dto) {
		Author newAuthor = new Author();
		newAuthor.setName(dto.getName());
		newAuthor.setLastName(dto.getLastName());
		return repository.save(newAuthor);
	}

	public void delete(Long id) {
		Author author = findEntityById(id);
		boolean isAuthorInAnyBook = bookRepository.existsByAuthorsContaining(author);
		if (isAuthorInAnyBook) {
			// Se já foi vendido apenas desativa do client side
			author.setActive(false);
			repository.save(author);
		} else {
			author.getBooks().clear();// Limpa a relação M:N entre book e author
			// Se nunca foi vendido é deletado do banco
			repository.delete(author);
		}

	}

	// reaproveitado o create DTO para o update
	public AuthorDTO update(Long id, AuthorCreateDTO obj) {
		Author autor = findEntityById(id);
		updateAuthor(autor, obj);
		Author savedAuthor = repository.save(autor);
		return new AuthorDTO(savedAuthor);

	}

	private void updateAuthor(Author autor, AuthorCreateDTO obj) {
		autor.setName(obj.getName());
		autor.setLastName(obj.getLastName());
	}

}
