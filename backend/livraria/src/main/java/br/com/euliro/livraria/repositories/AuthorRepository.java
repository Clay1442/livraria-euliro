package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Author;
import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends JpaRepository<Author, Long>{

	List<Author> findAllByActiveTrue();
	
	Optional<Author> findByIdAndActiveTrue(Long id);
	
}
