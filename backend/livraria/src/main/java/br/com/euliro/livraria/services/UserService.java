package br.com.euliro.livraria.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.dto.PasswordUpdateDTO;
import br.com.euliro.livraria.dto.UserCreateDTO;
import br.com.euliro.livraria.dto.UserDTO;
import br.com.euliro.livraria.dto.UserUpdateDTO;
import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	// metodo privado auxiliar que retorna uma entidade
	private User findEntityById(Long id) {
		Optional<User> userOptional = repository.findById(id);
		return userOptional.orElseThrow(() -> new RuntimeException("Pedido não encontrado! Id: " + id));
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
	public User insert(UserCreateDTO dto) {
		User entity = new User();
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setBirthDate(dto.getBirthDate());
		entity.setPassword(dto.getPassword());
		return repository.save(entity);
	}

	public void delete(Long id) {
		findById(id);
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

		if (!user.getPassword().equals(dto.getOldPassword())) {
			throw new RuntimeException("A senha antiga não confere!");
		}
		user.setPassword(dto.getNewPassword());
		repository.save(user);
	}
}