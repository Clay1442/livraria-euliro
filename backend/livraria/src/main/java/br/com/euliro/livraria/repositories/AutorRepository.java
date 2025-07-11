package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

}
