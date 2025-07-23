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

import br.com.euliro.livraria.dto.UserCreateDTO;
import br.com.euliro.livraria.dto.UserDTO;
import br.com.euliro.livraria.dto.UserUpdateDTO;
import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<UserDTO> obj = service.findAll();
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateDTO obj) {

		User newUserEntity  = service.create(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUserEntity.getId()).toUri();

		UserDTO responseDto = new UserDTO(newUserEntity);

		return ResponseEntity.created(uri).body(responseDto);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserUpdateDTO> update(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
		UserUpdateDTO userUpdate = service.update(id, dto);
		return ResponseEntity.ok().body(userUpdate);
	}

}
