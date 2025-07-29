package br.com.euliro.livraria.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.dto.PasswordUpdateDTO;
import br.com.euliro.livraria.dto.UserCreateDTO;
import br.com.euliro.livraria.dto.UserDTO;
import br.com.euliro.livraria.dto.UserUpdateDTO;
import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.exceptions.ResourceNotFoundException;
import br.com.euliro.livraria.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationService authenticationService;

	// metodo privado auxiliar que retorna uma entidade
	private User findEntityById(Long id) {
		Optional<User> userOptional = repository.findById(id);
		return userOptional.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado! Id: " + id));
	}

	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		List<User> userList = repository.findAll();
		return userList.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User userOptional = findEntityById(id);
		return new UserDTO(userOptional);
	}

	@Transactional
	public User create(UserCreateDTO dto) {
		User entity = new User();
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setBirthDate(dto.getBirthDate());

		entity.setPassword(passwordEncoder.encode(dto.getPassword()));

		return repository.save(entity);
	}

	public void delete(Long id) {

		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado para o ID: " + id);
		}

		repository.deleteById(id);

	}

	@Transactional
	public UserUpdateDTO update(Long id, UserUpdateDTO dto) {
		User entity = findEntityById(id);
		updateData(entity, dto);
		repository.save(entity);
		return new UserUpdateDTO(entity);
	}

	private void updateData(User entity, UserUpdateDTO dto) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setBirthDate(dto.getBirthDate());
	}

	@Transactional
	public void updatePassword(Long id, PasswordUpdateDTO dto) {

		User user = findEntityById(id);

		if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
			throw new RuntimeException("A senha antiga não confere!");
		}

		user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		repository.save(user);
	}
	
	//captura os dados do usuário de forma mais segura sem expor o id
	@Transactional(readOnly = true)
	public UserDTO getMe(){
		UserDetails userDetails = authenticationService.getAuthenticatedUser();
		User user = (User) userDetails;
		return new UserDTO(user);
	}
	
}