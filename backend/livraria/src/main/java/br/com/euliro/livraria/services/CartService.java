package br.com.euliro.livraria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.entities.Cart;
import br.com.euliro.livraria.entities.CartItem;
import br.com.euliro.livraria.entities.Book;
import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.repositories.CartRepository;
import br.com.euliro.livraria.repositories.CartItemRepository;
import br.com.euliro.livraria.repositories.BookRepository;
import br.com.euliro.livraria.repositories.UserRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Cart getUserCart(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Usuário não Encontrado!"));

		Optional<Cart> cartOpt = cartRepository.findByClient(user);

		return cartOpt.orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setClient(user);
			return cartRepository.save(newCart);
		});
	}

	@Transactional
	public Cart addItem(Long userId, Long bookId, int quantity) {
		Cart cart = getUserCart(userId);
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException("Livro não Encontrado!"));

		Optional<CartItem> existingItemOpt = cart.getItems().stream()
				.filter(item -> item.getBook().equals(book)).findFirst();

		if (existingItemOpt.isPresent()) {
			CartItem item = existingItemOpt.get();
			int newQuantity = item.getAmount() + quantity;

			if (newQuantity > book.getStock()) {
				throw new RuntimeException("Estoque insuficiente!");
			}
			item.setAmount(newQuantity);

		} else {
			if (quantity > book.getStock()) {
				throw new RuntimeException("Estoque Insuficiente!");
			}
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setBook(book);
			newItem.setAmount(quantity);
			newItem.setUnitPrice(book.getPrice());
			cart.addItem(newItem); 
		}
		return cartRepository.save(cart);
	}

	@Transactional
	public Cart removeItem(Long userId, Long cartItemId) {
		Cart cart = getUserCart(userId);

		CartItem itemToRemove = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Carrinho não encontrado!"));

		if (!itemToRemove.getCart().getClient().equals(cart.getClient())) {
			throw new SecurityException("Acesso negado: Você esta removendo um item que não pertence ao carrinho do usuário");
		}

		cart.removeItem(itemToRemove); 

	
		return cartRepository.save(cart);
	}
	
	@Transactional
	public void clearCart(Long cartId){
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new RuntimeException("Carrinho não Encontrado!"));
		
		cart.getItems().clear();
		
		cartRepository.save(cart);
	}
}