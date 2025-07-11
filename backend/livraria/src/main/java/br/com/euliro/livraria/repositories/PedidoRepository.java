package br.com.euliro.livraria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.euliro.livraria.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
