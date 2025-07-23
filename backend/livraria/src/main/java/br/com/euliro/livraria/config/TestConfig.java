package br.com.euliro.livraria.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import br.com.euliro.livraria.dto.UserCreateDTO;
import br.com.euliro.livraria.entities.*;
import br.com.euliro.livraria.entities.enums.OrderStatus;
import br.com.euliro.livraria.repositories.*;
import br.com.euliro.livraria.services.UserService;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {


    @Autowired 
    private BookRepository bookRepository;
    @Autowired 
    private AuthorRepository authorRepository;
    @Autowired
    private UserService userService;
    @Autowired 
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Author author1 = new Author(null, "J.R.R.", "Tolkien");
        Author author2 = new Author(null, "J.K.", "Rowling");
        authorRepository.saveAll(Arrays.asList(author1, author2));

        User user1 = new User(null, "João Silva", "joao@gmail.com", "123456", LocalDate.parse("1990-05-15"));
        User user2 = new User(null, "Ana Costa", "ana@gmail.com", "654321", LocalDate.parse("1988-11-20"));
        UserCreateDTO dtoUser1 = new UserCreateDTO(user1);
        UserCreateDTO dtoUser2 = new UserCreateDTO(user2);
        User savedUser1 = userService.create(dtoUser1);
        User savedUser2 = userService.create(dtoUser2);
        
        Book book1 = new Book(null, "The Lord of the Rings", "A jornada...", new BigDecimal("150.00"), 20);
        Book book2 = new Book(null, "Harry Potter", "O início da saga...", new BigDecimal("50.00"), 30);
        Book book3 = new Book(null, "The Hobbit", "A aventura...", new BigDecimal("70.00"), 15);
        
        book1.getAuthors().add(author1);
        book2.getAuthors().add(author2);
        book3.getAuthors().add(author1);
        bookRepository.saveAll(Arrays.asList(book1, book2, book3));

        Order order1 = new Order(null, savedUser1, Instant.parse("2025-07-10T19:53:07Z"), OrderStatus.PAGAMENTO_APROVADO);
        Order order2 = new Order(null, savedUser2, Instant.parse("2025-07-11T03:42:10Z"), OrderStatus.ENVIADO);

        OrderItem oi1 = new OrderItem(null, order1, book1, 1, book1.getPrice());
        OrderItem oi2 = new OrderItem(null, order1, book3, 2, book3.getPrice());
        OrderItem oi3 = new OrderItem(null, order2, book2, 1, book2.getPrice());
    
        
        order1.addItem(oi1);;
        order1.addItem(oi2);;
        order2.addItem(oi3);
        
        order1.setTotalPrice(order1.calculateItemsTotal());
        order2.setTotalPrice(order2.calculateItemsTotal());
        
        orderRepository.saveAll(Arrays.asList(order1, order2));
    }
}