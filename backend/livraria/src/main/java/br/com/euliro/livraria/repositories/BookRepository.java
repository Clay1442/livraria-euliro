package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Author;
import br.com.euliro.livraria.entities.Book;
import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findAllByActiveTrue();
	
	Optional<Book> findByIdAndActiveTrue(Long id);
	
	boolean existsByAuthorsContaining(Author author);
}
