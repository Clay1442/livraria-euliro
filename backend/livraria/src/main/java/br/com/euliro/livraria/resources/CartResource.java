package br.com.euliro.livraria.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.euliro.livraria.dto.AddItemRequestDTO;
import br.com.euliro.livraria.dto.CartDTO;
import br.com.euliro.livraria.services.CartService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "/carts")
public class CartResource {

	@Autowired
	private CartService service;

	@GetMapping
	public ResponseEntity<List<CartDTO>> findAll() {
		List<CartDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<CartDTO> getUserCart(@PathVariable Long userId) {
		CartDTO obj = service.getUserCartDTO(userId);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/{userId}/items")
	public ResponseEntity<CartDTO> addItem(@PathVariable Long userId,@Valid @RequestBody AddItemRequestDTO dto) {
		CartDTO cart = service.addItem(userId, dto.getBookId(), dto.getQuantity());
		return ResponseEntity.ok().body(cart);
	}

	@DeleteMapping(value = "/{userId}/items/{itemId}")
	public ResponseEntity<CartDTO> removeItem(@PathVariable Long userId, @PathVariable Long itemId) {
		CartDTO cart = service.removeItem(userId, itemId);
		return ResponseEntity.ok().body(cart);
	}

	@DeleteMapping(value = "/carts/{cartId}/items")
	public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
		service.clearCart(cartId);
		return ResponseEntity.noContent().build();
	}
}
