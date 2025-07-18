package br.com.euliro.livraria.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.euliro.livraria.entities.Cart;
import br.com.euliro.livraria.entities.Order;
import br.com.euliro.livraria.services.CartService;
import br.com.euliro.livraria.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService service;
     
	@Autowired
	private CartService carrinhoService;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = service.findAll();																	
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}


    @PostMapping(value = "cart/user/{userId}")
	public ResponseEntity<Order> insert(@PathVariable Long userId) {
	    Cart cart = carrinhoService.getUserCart(userId);
	    
	    if(cart.getItems().isEmpty()) {
            throw new RuntimeException("Não é possível criar um pedido a partir de um carrinho vazio.");
	    }

	    Order newOrder = service.createFromCart(cart);
	    
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(newOrder.getId()).toUri();
	    return ResponseEntity.created(uri).body(newOrder);
	}
	
}
