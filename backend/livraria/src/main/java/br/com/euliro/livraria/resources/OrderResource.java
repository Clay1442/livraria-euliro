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

import br.com.euliro.livraria.dto.OrderDTO;
import br.com.euliro.livraria.entities.Cart;
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
	public ResponseEntity<List<OrderDTO>> findAll() {
		List<OrderDTO> list = service.findAll();																	
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
		OrderDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/my-orders")
	public ResponseEntity<List<OrderDTO>> findMyOrders() {
		List<OrderDTO> list = service.findMyOrders();
		return ResponseEntity.ok().body(list);
	}

	
	
    @PostMapping(value = "from-cart/user/{userId}")
	public ResponseEntity<OrderDTO> insert(@PathVariable Long userId) {
	    Cart cart = carrinhoService.findCartEntityByUserId(userId);
	    
	    if(cart.getItems().isEmpty()) {
            throw new RuntimeException("Não é possível criar um pedido a partir de um carrinho vazio.");
	    }

	    OrderDTO newOrder = service.createFromCart(cart);
	    
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	        .buildAndExpand(newOrder.getId()).toUri();
	    return ResponseEntity.created(uri).body(newOrder);
	}
	
}
