package br.com.euliro.livraria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.com.euliro.livraria.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);

}
