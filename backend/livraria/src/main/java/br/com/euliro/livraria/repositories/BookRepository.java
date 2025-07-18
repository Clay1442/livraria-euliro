package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
