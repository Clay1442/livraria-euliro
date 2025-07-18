package br.com.euliro.livraria.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.entities.Cart;
import br.com.euliro.livraria.entities.CartItem;
import br.com.euliro.livraria.entities.OrderItem;
import br.com.euliro.livraria.entities.Book;
import br.com.euliro.livraria.entities.Order;
import br.com.euliro.livraria.entities.enums.OrderStatus;
import br.com.euliro.livraria.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private CartService cartService; 

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Long id) {
		Optional<Order> orderOptional = repository.findById(id);
		return orderOptional.orElseThrow(() -> new RuntimeException("Pedido não encontrado! Id: " + id));
	}
	
	@Transactional
	public Order createFromCart(Cart cart) {
		Order newOrder = new Order(null, cart.getClient(), Instant.now(), OrderStatus.AGUARDANDO_PAGAMENTO);

		for (CartItem cartItem : cart.getItems()) {
			OrderItem orderItem = new OrderItem(
                null, 
                newOrder, 
                cartItem.getBook(), 
                cartItem.getAmount(),
				cartItem.getUnitPrice()
            );
			newOrder.addItem(orderItem);
		}

		BigDecimal itemsSubtotal = newOrder.calculateItemsTotal();
		BigDecimal shippingFee = new BigDecimal("15.00"); 
		BigDecimal finalPrice = itemsSubtotal.add(shippingFee);

		newOrder.setTotalPrice(finalPrice);

		Order savedOrder = repository.save(newOrder);

		cartService.clearCart(cart.getId()); 

		return savedOrder;
	}
	
	@Transactional
	public Order approvePayment(Long orderId) {
		Order order = findById(orderId);
		if (order.getOrderStatus() != OrderStatus.AGUARDANDO_PAGAMENTO) {
			throw new RuntimeException("O pedido não está aguardando pagamento");
		}
		order.setOrderStatus(OrderStatus.PAGAMENTO_APROVADO);
		return repository.save(order);
	}

	@Transactional
	public Order markAsShipped(Long orderId, String trackingCode) {
		Order order = findById(orderId);
		if (order.getOrderStatus() != OrderStatus.PAGAMENTO_APROVADO) {
			throw new RuntimeException("O pedido deve ser pago antes do envio.");
		}
		order.setTrackingCode(trackingCode);
		order.setOrderStatus(OrderStatus.ENVIADO);
		return repository.save(order);
	}

	@Transactional
	public Order markAsDelivered(Long orderId) {
		Order order = findById(orderId);
		if (order.getOrderStatus() != OrderStatus.ENVIADO) {
			throw new RuntimeException("O pedido deve ser enviado antes de poder ser entregue.");
		}
		order.setOrderStatus(OrderStatus.ENTREGUE);
		return repository.save(order);
	}

	@Transactional
	public Order cancelOrder(Long orderId) {
		Order order = findById(orderId);
		if (order.getOrderStatus() != OrderStatus.AGUARDANDO_PAGAMENTO
				&& order.getOrderStatus() != OrderStatus.PAGAMENTO_APROVADO) {
			throw new RuntimeException("Não é possível cancelar o pedido com status: " + order.getOrderStatus());
		}

		for (OrderItem item : order.getItems()) {
			Book book = item.getBook();
			book.addToStock(item.getQuantity());
		}
		order.setOrderStatus(OrderStatus.CANCELADO);

		return repository.save(order);
	}
}