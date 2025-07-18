package br.com.euliro.livraria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Cart;
import br.com.euliro.livraria.entities.User;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Optional<Cart> findByClient(User client);

}
