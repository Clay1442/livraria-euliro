package br.com.euliro.livraria.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Order;
import br.com.euliro.livraria.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByClient(User client);
	
}
