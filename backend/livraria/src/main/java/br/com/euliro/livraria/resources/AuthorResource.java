package br.com.euliro.livraria.resources;

import java.net.URI;
import java.util.List;

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

import br.com.euliro.livraria.entities.Author;
import br.com.euliro.livraria.services.AuthorService;

@RestController
@RequestMapping(value = "/authors")
public class AuthorResource {

	@Autowired
	private AuthorService service;

	@GetMapping
	public ResponseEntity<List<Author>> findAll() {
		List<Author> author = service.findAll();
		return ResponseEntity.ok().body(author);
	}
    
	@GetMapping(value = "/{id}")
	public ResponseEntity<Author> findById(@PathVariable Long id) {
          Author author = service.findById(id);
          return ResponseEntity.ok().body(author);
	}

	@PostMapping
	public ResponseEntity<Author> newAuthor(@RequestBody Author obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri(); 
	    return ResponseEntity.created(uri).body(obj);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable	Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	} 
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author obj){
		obj = service.update(id, obj);
	    return ResponseEntity.ok().body(obj);
		
	}
	
	
	
}
