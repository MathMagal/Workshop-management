package com.projeto.model.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="TAB_PRODUTO")
public class Produto {
	private int id;
	private String tipo_produto;
	private String preço_produto;
	private Integer quantidade;
	private String preco_total;
	private Cliente cliente;
	
	@OneToMany(mappedBy = "produto")
	private List<ProdutoPedido>  listaProdutoPedido;
	
	
	
	public Produto() {
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="TIPO", length = 50, nullable = false)
	public String getTipo_produto() {
		return tipo_produto;
	}

	public void setTipo_produto(String tipo_produto) {
		this.tipo_produto = tipo_produto;
	}
	@Column(name="PRECO", length = 50, nullable = false)
	public String getPreço_produto() {
		return preço_produto;
	}

	public void setPreço_produto(String preço_produto) {
		this.preço_produto = preço_produto;
	}
	
	@Column(name="QUANTIDADE", length = 10, nullable = false)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name="PRECO_TOTAL", length = 50, nullable = false)
	public String getPreco_total() {
		return preco_total;
	}
	public void setPreco_total(String preco_total) {
		this.preco_total = preco_total;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "produto [id_produto=" + id + ", tipo_produto=" + tipo_produto + ", preço_produto="
				+ preço_produto + ", quantidade=" + quantidade + ", preço_total=" + preco_total + ",cliente"+ cliente+ "]";
	}
	
	@ManyToOne
	@JoinColumn(name = "CLIENTE_ID", nullable = false)
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
