package br.com.euliro.livraria.resources;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.euliro.livraria.entities.Book;
import br.com.euliro.livraria.services.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

	@Autowired
	private BookService service;

	@GetMapping
	public ResponseEntity<List<Book>> findAll() {
		List<Book> book = service.findAll();
		return ResponseEntity.ok().body(book);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Book> findById(@PathVariable Long id) {
		Book book = service.findById(id);
		return ResponseEntity.ok().body(book);
	}

	@PostMapping
	public ResponseEntity<Book> insert(@RequestBody Book obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book obj) {
		obj = service.updateDescription(id, obj);
		return ResponseEntity.ok().body(obj);

	}

	@PatchMapping(value = "/{id}/stock")
	public ResponseEntity<Book> addStock(@PathVariable Long id, Map<String, Integer> body) {
		int quantity = body.get("quantity");
		Book obj = service.addStock(id, quantity);
		return ResponseEntity.ok().body(obj);

	}

	@PatchMapping(value = "/{id}/price")
	public ResponseEntity<Book> updatePrice(@PathVariable Long id, @RequestBody Map<String, BigDecimal> body) {
		BigDecimal newPrice = body.get("price");
		Book obj = service.updatePrice(id, newPrice);
		return ResponseEntity.ok().body(obj);

	}

}
