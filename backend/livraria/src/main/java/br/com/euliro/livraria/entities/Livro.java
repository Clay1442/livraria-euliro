package br.com.euliro.livraria.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_livros")
public class Livro implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	private BigDecimal valor;

	private int estoque;

	private String descricao;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "tb_livro_autor",
	joinColumns = @JoinColumn(name = "livro_id"),
	inverseJoinColumns = @JoinColumn(name = "autor_id"))
	private HashSet<Autor> autores = new HashSet<>();
	
	
	
	public Livro() {

	}

	public Livro(Long idLivro, String titulo, BigDecimal valor, int estoque, String descricao) {

		super();

		this.id = idLivro;

		this.titulo = titulo;

		this.valor = valor;

		this.estoque = estoque;

		this.descricao = descricao;

	}

	public Long getIdLivro() {

		return id;

	}

	public void setIdLivro(Long idLivro) {

		this.id = idLivro;

	}

	public String getTitulo() {

		return titulo;

	}

	public void setTitulo(String titulo) {

		this.titulo = titulo;

	}

	public BigDecimal getValor() {

		return valor;

	}

	public void setValor(BigDecimal valor) {

		this.valor = valor;

	}

	public int getEstoque() {

		return estoque;

	}

	public void setEstoque(int estoque) {

		this.estoque = estoque;

	}

	public String getDescricao() {

		return descricao;

	}

	public void setDescricao(String descricao) {

		this.descricao = descricao;

	}

	@Override

	public int hashCode() {

		return Objects.hash(id);

	}
	
	 public Set<Autor> getAutores() {
	        return autores;
	    }
	

	@Override
	public boolean equals(Object obj) {

		if (this == obj)

			return true;

		if (obj == null)

			return false;

		if (getClass() != obj.getClass())

			return false;

		Livro other = (Livro) obj;

		return Objects.equals(id, other.id);

	}

}