package com.projeto.model.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProdutoPedidoPK implements Serializable{

	
	private static final long serialVersionUID = 6451429446197414702L;
	
	private Integer ID_Produto;
	private Integer ID_Pedido;

	
	public ProdutoPedidoPK() {
		super();
	}
	
	public ProdutoPedidoPK(Integer iD_Produto, Integer iD_Pedido) {
		super();
		ID_Produto = iD_Produto;
		ID_Pedido = iD_Pedido;
	}
	
	
	
	@Column(name= "PRODUTO_ID", insertable = false, updatable = false, nullable = false)
	public Integer getID_Produto() {
		return ID_Produto;
	}
	public void setID_Produto(Integer iD_Produto) {
		ID_Produto = iD_Produto;
	}
	@Column(name= "PEDIDO_ID", insertable = false, updatable = false, nullable = false)
	public Integer getID_Pedido() {
		return ID_Pedido;
	}
	public void setID_Pedido(Integer iD_Pedido) {
		ID_Pedido = iD_Pedido;
	}
	
}
