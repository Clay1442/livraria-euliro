package br.com.euliro.livraria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		Optional<User> userOptional = repository.findById(id);
		return userOptional.orElseThrow(() -> new RuntimeException("Usuário não encontrado! Id: " + id));
	}

	@Transactional
	public User insert(User obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	@Transactional
	public User update(Long id, User newData) {
		User entity = findById(id);
		updateData(entity, newData);
		return repository.save(entity);
	}

	private void updateData(User entity, User newData) {
		entity.setName(newData.getName());
		entity.setEmail(newData.getEmail());
		entity.setBirthDate(newData.getBirthDate());
	}

	@Transactional
	public void updatePassword(Long id, String oldPassword, String newPassword) {
		User user = findById(id);

		if (!user.getPassword().equals(oldPassword)) {
			throw new RuntimeException("A senha antiga não confere!");
		}

		user.setPassword(newPassword);
		repository.save(user);
	}
}