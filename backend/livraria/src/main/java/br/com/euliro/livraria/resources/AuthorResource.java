package br.com.euliro.livraria.resources;

import java.net.URI;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.euliro.livraria.dto.AuthorCreateDTO;
import br.com.euliro.livraria.dto.AuthorDTO;
import br.com.euliro.livraria.entities.Author;
import br.com.euliro.livraria.services.AuthorService;

@RestController
@RequestMapping(value = "/authors")
public class AuthorResource {

	@Autowired
	private AuthorService service;

	@GetMapping
	public ResponseEntity<Set<AuthorDTO>> findAll() {
		Set<AuthorDTO> author = service.findAll();
		return ResponseEntity.ok().body(author);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AuthorDTO> findById(@PathVariable Long id) {
		AuthorDTO author = service.findById(id);
		return ResponseEntity.ok().body(author);
	}

	@PostMapping
	public ResponseEntity<AuthorDTO> create(@RequestBody AuthorCreateDTO obj) {
		Author newAuthor = service.create(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAuthor.getId())
				.toUri();

		AuthorDTO responseDto = new AuthorDTO(newAuthor);

		return ResponseEntity.created(uri).body(responseDto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorCreateDTO dto) {
		AuthorDTO obj = service.update(id, dto);
		return ResponseEntity.ok().body(obj);

	}

}
