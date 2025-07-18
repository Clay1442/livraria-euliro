package br.com.euliro.livraria.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	

}
