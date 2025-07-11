package br.com.euliro.livraria.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_item_carrinho")
public class ItemCarrinho implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int quantidade;
	private BigDecimal precoUnitario;

	@ManyToOne
	@JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;	

    @ManyToOne
    @JoinColumn(name = "livro_id")
	private Livro livro;
	
    public ItemCarrinho() {
    }

	public ItemCarrinho(Long id, int quantidade, BigDecimal precoUnitario, Carrinho carrinho, Livro livro) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
		this.carrinho = carrinho;
		this.livro = livro;
	}
	
	
	public BigDecimal getSubtotal(){
	 return precoUnitario.multiply(new BigDecimal(quantidade));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCarrinho other = (ItemCarrinho) obj;
		return Objects.equals(id, other.id);
	}
    
	
}
