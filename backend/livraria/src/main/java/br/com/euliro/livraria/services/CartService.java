package br.com.euliro.livraria.services;

	import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.entities.Cart;
import br.com.euliro.livraria.entities.CartItem;
import br.com.euliro.livraria.dto.CartDTO;
import br.com.euliro.livraria.entities.Book;
import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.exceptions.InsufficientStockException;
import br.com.euliro.livraria.exceptions.ResourceNotFoundException;
import br.com.euliro.livraria.repositories.CartRepository;
import br.com.euliro.livraria.repositories.CartItemRepository;
import br.com.euliro.livraria.repositories.UserRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private CartItemRepository cartItemRepository;

	// metodo auxiliar que retorna uma entidade
	public Cart findCartEntityByUserId(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Usúario não Encontrado"));

		Optional<Cart> cartOptional = cartRepository.findByClient(user);

		// Lógica "find or create" para a ENTIDADE
		return cartOptional.orElseGet(() -> {
			Cart newCartEntity = new Cart();
			newCartEntity.setClient(user);
			return cartRepository.save(newCartEntity);
		});
	}

	@Transactional(readOnly = true)
	public CartDTO getUserCartDTO(Long userId) {
		Cart entity = findCartEntityByUserId(userId);
		return new CartDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<CartDTO> findAll() {
		List<Cart> cartList = cartRepository.findAll();
		return cartList.stream().map(cart -> new CartDTO(cart)).collect(Collectors.toList());
	}

	@Transactional
	public CartDTO addItem(Long userId, Long bookId, Integer quantity) {
		Cart cart = findCartEntityByUserId(userId);
		Book book = bookService.findEntityById(bookId);

		Optional<CartItem> existingItemOpt = cart.getItems().stream().filter(item -> item.getBook().equals(book))
				.findFirst();

		if (existingItemOpt.isPresent()) {
			CartItem item = existingItemOpt.get();
			Integer newQuantity = item.getQuantity() + quantity;

			if (newQuantity > book.getStock()) {
				throw new InsufficientStockException("Estoque insuficiente para o livro '" + book.getTitle() + "'. "
						+ "Disponível: " + book.getStock() + ", Solicitado: " + newQuantity);
			}
			item.setQuantity(newQuantity);

		} else {
			if (quantity > book.getStock()) {
				throw new InsufficientStockException("Estoque insuficiente para o livro '" + book.getTitle() + "'. "
						+ "Disponível: " + book.getStock() + ", Solicitado: " + quantity);
			}

			CartItem newItem = new CartItem(null, quantity, book.getPrice(), cart, book);
			cart.addItem(newItem);
		}
		Cart savedCart = cartRepository.save(cart);

		return new CartDTO(savedCart);
	}

	@Transactional
	public CartDTO removeItem(Long userId, Long cartItemId) {
		Cart cart = findCartEntityByUserId(userId);

		CartItem itemToRemove = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item do carrinho não encontrado!"));

		if (!itemToRemove.getCart().getClient().equals(cart.getClient())) {
			throw new SecurityException(
					"Acesso negado: Você esta removendo um item que não pertence ao carrinho do usuário");
		}

		cart.removeItem(itemToRemove);

		Cart savedCart = cartRepository.save(cart);
		return new CartDTO(savedCart);
	}

	@Transactional
	public void clearCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Carrinho não Encontrado!"));

		cart.getItems().clear();

		cartRepository.save(cart);
	}
}