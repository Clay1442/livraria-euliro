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

import br.com.euliro.livraria.dto.BookCreateDTO;
import br.com.euliro.livraria.dto.BookDTO;
import br.com.euliro.livraria.dto.BookUpdateDTO;
import br.com.euliro.livraria.entities.Book;
import br.com.euliro.livraria.services.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

	@Autowired
	private BookService service;

	@GetMapping
	public ResponseEntity<List<BookDTO>> findAll() {
		List<BookDTO> book = service.findAll();
		return ResponseEntity.ok().body(book);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
		BookDTO book = service.findById(id);
		return ResponseEntity.ok().body(book);
	}

	@PostMapping
	public ResponseEntity<BookDTO> create(@RequestBody BookCreateDTO dto) {
		Book newBook = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBook.getId()).toUri();
		BookDTO responseDto = new BookDTO(newBook);
		return ResponseEntity.created(uri).body(responseDto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<BookUpdateDTO> update(@PathVariable Long id, @RequestBody BookUpdateDTO dto) {
		dto = service.updateDescription(id, dto);
		return ResponseEntity.ok().body(dto);

	}

	@PatchMapping(value = "/{id}/stock")
	public ResponseEntity<BookDTO> addStock(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
		int quantity = body.get("quantity");
		BookDTO obj = service.addStock(id, quantity);
		return ResponseEntity.ok().body(obj);

	}

	@PatchMapping(value = "/{id}/stock/remove")
	public ResponseEntity<BookDTO> removeStock(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
		int quantity = body.get("quantity");
		BookDTO obj = service.removeStock(id, quantity);
		return ResponseEntity.ok().body(obj);
	}

	@PatchMapping(value = "/{id}/price")
	public ResponseEntity<BookDTO> updatePrice(@PathVariable Long id, @RequestBody Map<String, BigDecimal> body) {
		BigDecimal newPrice = body.get("price");
		BookDTO obj = service.updatePrice(id, newPrice);
		return ResponseEntity.ok().body(obj);

	}

}
