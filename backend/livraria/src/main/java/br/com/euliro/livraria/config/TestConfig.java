package br.com.euliro.livraria.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.euliro.livraria.entities.Autor;
import br.com.euliro.livraria.entities.Cliente;
import br.com.euliro.livraria.entities.ItemPedido;
import br.com.euliro.livraria.entities.Livro;
import br.com.euliro.livraria.entities.Pedido;
import br.com.euliro.livraria.entities.enums.StatusPedidos;
import br.com.euliro.livraria.repositories.AutorRepository;
import br.com.euliro.livraria.repositories.ClienteRepository;
import br.com.euliro.livraria.repositories.LivroRepository;
import br.com.euliro.livraria.repositories.PedidoRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public void run(String... args) throws Exception {

		Autor a1 = new Autor(null, "Maria", "Brown");
		Autor a2 = new Autor(null, "Alex", "Green");
		
		autorRepository.saveAll(Arrays.asList(a1, a2));


		Livro l1 = new Livro(null, "The Lord of the Rings", new BigDecimal("90.50"), 20,
				"Lorem ipsum dolor sit amet...");
		Livro l2 = new Livro(null, "Smart TV", new BigDecimal("54.90"), 5, "Lorem ipsum dolor sit amet...");

		livroRepository.saveAll(Arrays.asList(l1, l2));
		
		
		l1.getAutores().add(a2);
		l2.getAutores().add(a1);
		l1.getAutores().add(a1);
		
		livroRepository.save(l1);
		livroRepository.save(l2);

		Cliente c1 = new Cliente(null, "João Silva", "joao@gmail.com", "123456", LocalDate.parse("1990-05-15"));
		Cliente c2 = new Cliente(null, "Ana Costa", "ana@gmail.com", "654321", LocalDate.parse("1988-11-20"));
		
		clienteRepository.saveAll(Arrays.asList(c1, c2));

		Pedido p1 = new Pedido(null, c1, Instant.parse("2025-07-10T19:53:07Z"), StatusPedidos.AGUARDANDO_PAGAMENTO);
		Pedido p2 = new Pedido(null, c2, Instant.parse("2025-07-11T03:42:10Z"), StatusPedidos.PAGO);
		Pedido p3 = new Pedido(null, c1, Instant.parse("2025-07-11T15:21:22Z"), StatusPedidos.PAGO);

		pedidoRepository.saveAll(Arrays.asList(p1, p2, p3));

		ItemPedido ip1 = new ItemPedido(null, p1, l1, l1.getValor(), 1);
		ItemPedido ip3 = new ItemPedido(null, p2, l2, l2.getValor(), 1);

		p1.getItens().addAll(Arrays.asList(ip1, ip3));
		p2.getItens().add(ip3);
		
		pedidoRepository.saveAll(Arrays.asList(p1, p2));
		
		
		

	}
}