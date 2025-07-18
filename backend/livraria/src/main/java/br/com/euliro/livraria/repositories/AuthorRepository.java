package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}
