package br.com.euliro.livraria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.euliro.livraria.entities.Author;
import br.com.euliro.livraria.repositories.AuthorRepository;

@Service
public class AuthorService {

    @Autowired 	
	private AuthorRepository repository;
	
	public List<Author> findAll(){
		return repository.findAll();
	}
	
	public Author findById(Long id){
	   Optional<Author> author = repository.findById(id);
	   return author.get();
	}	
	
	public Author insert(Author obj){
		Author newAuthor = repository.save(obj);
		return newAuthor; 
	}
	
	
	public void delete(Long id){
        Author author = findById(id);
		repository.deleteById(author.getId());
	}	
	
	
	public Author update(Long id, Author obj){
		Author autor = repository.getReferenceById(id);
		updateAuthor(autor, obj);
		return repository.save(autor);
		
	}
	
	private void updateAuthor(Author autor, Author obj) {
		 autor.setName(obj.getName());
		 autor.setLastName(obj.getLastName());
	}
	
	
	

	
}
