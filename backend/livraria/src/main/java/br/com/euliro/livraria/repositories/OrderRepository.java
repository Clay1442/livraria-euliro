package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
