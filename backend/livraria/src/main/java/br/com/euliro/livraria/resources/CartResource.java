package br.com.euliro.livraria.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.euliro.livraria.dto.AddItemRequestDTO;
import br.com.euliro.livraria.entities.Cart;
import br.com.euliro.livraria.services.CartService;

@RestController
@RequestMapping(value = "/carts")
public class CartResource {

	@Autowired
	private CartService service;
     
	
	@GetMapping
	public ResponseEntity<List<Cart>> findAll() {
		List<Cart> list = service.findAll();																	
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<Cart> getUserCart(@PathVariable Long userId) {
		Cart obj = service.getUserCart(userId);
		return ResponseEntity.ok().body(obj);
	}


    @PostMapping(value = "{userId}/items")
	public ResponseEntity<Cart> addItem(@PathVariable Long userId, @RequestBody AddItemRequestDTO dto) {
	   Cart cart = service.addItem(userId, dto.getBookId(), dto.getQuantity());
	   return ResponseEntity.ok().body(cart);
	}
        
    @DeleteMapping(value = "{userId}/items/{itemId}")
  	public ResponseEntity<Cart> removeItem(@PathVariable Long userId, @PathVariable  Long itemId) {
  	   Cart cart = service.removeItem(userId, itemId);
  	   return ResponseEntity.ok().body(cart);
  	}
      
    @DeleteMapping(value = "/carts/{cartId}/items")
  	public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
  	   service.clearCart(cartId);
	   return ResponseEntity.noContent().build();
  	}
}
