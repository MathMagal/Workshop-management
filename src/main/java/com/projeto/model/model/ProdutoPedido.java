package com.projeto.model.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_PRODUTOS_PEDIDOS")
public class ProdutoPedido implements Serializable{

	private static final long serialVersionUID = -1792624123551846734L;
	
	private ProdutoPedidoPK id;
	private String precoTotal;
	private String cliente;
	private String status;
	
	private Produto produto;
	private Pedido pedido; 
	
	
	public ProdutoPedido() {
	}

	public ProdutoPedido(ProdutoPedidoPK id) {
		this.id = id;
	}

	@EmbeddedId
	public ProdutoPedidoPK getId() {
		return id;
	}

	public void setId(ProdutoPedidoPK id) {
		this.id = id;
	}
	
	
	@Column(name= "PRECO_TOTAL", nullable = false)
	public String getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(String precoTotal) {
		this.precoTotal = precoTotal;
	}
	
	@Column(name= "CLIENTE", nullable = false)
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	@Column(name= "STATUS", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "PRODUTO_ID", nullable = false, insertable = false, updatable = false)
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	@ManyToOne
	@JoinColumn(name = "PEDIDO_ID", nullable = false, insertable = false, updatable = false)
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		ProdutoPedido other = (ProdutoPedido) obj;
		return Objects.equals(id, other.id);
	}
	

}
