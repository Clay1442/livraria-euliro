package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
