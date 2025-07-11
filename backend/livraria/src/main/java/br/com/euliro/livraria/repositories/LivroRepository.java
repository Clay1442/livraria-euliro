package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

}
