package br.com.euliro.livraria.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
