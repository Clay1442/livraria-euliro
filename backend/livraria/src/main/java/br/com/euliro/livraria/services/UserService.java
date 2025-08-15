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
import br.com.euliro.livraria.dto.UpdateRolesDTO;
import br.com.euliro.livraria.dto.UserCreateDTO;
import br.com.euliro.livraria.dto.UserDTO;
import br.com.euliro.livraria.dto.UserUpdateDTO;
import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.entities.enums.Role;
import br.com.euliro.livraria.exceptions.ConflictEmailException;
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
		Optional<User> userOptional = repository.findByIdAndActiveTrue(id);
		return userOptional.orElseThrow(() -> new ResourceNotFoundException("Usúario não encontrado! Id: " + id));
	}

	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		List<User> userList = repository.findAllByActiveTrue();
		return userList.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User userOptional = findEntityById(id);
		return new UserDTO(userOptional);
	}

	@Transactional
	public User create(UserCreateDTO dto) {
		if(repository.findByEmail(dto.getEmail()).isPresent()){
		   throw new ConflictEmailException(dto.getEmail() + " ja cadastrado");
		}
		User entity = new User();
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setBirthDate(dto.getBirthDate());
		entity.addRole(Role.ROLE_CLIENTE);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		return repository.save(entity);
	}

	public void delete(Long id) {
		User user = findEntityById(id);
		user.setActive(false);
		repository.save(user);
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

	// captura os dados do usuário de forma mais segura sem expor o id
	@Transactional(readOnly = true)
	public UserDTO getMe() {
		UserDetails userDetails = authenticationService.getAuthenticatedUser();
		User user = (User) userDetails;
		return new UserDTO(user);
	}

	@Transactional
	public UserDTO updateRoles(Long id, UpdateRolesDTO dto) {
		User user = findEntityById(id);

		user.getRoles().clear();
		for (Role role : dto.getRoles()) {
			user.addRole(role);
		}
		
		User savedUser = repository.save(user);
		return new UserDTO(savedUser);
	}
}