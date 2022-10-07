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
@Table(name="TAB_PEDIDO")
public class Pedido {
	private Integer id;
	private String pagamento;
	private String numero_pedido;
	private String status_pagamento;
	private Confeccao confeccao;
	
	@OneToMany(mappedBy = "pedido")
	private List<ProdutoPedido> listaProdutoPedido;
	
	public Pedido() {
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="PAGAMENTO_Pedido", length = 20, nullable = false)
	public String getPagamento() {
		return pagamento;
	}
	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}
	
	@Column(name="STATUS_Pedido", length = 20, nullable = false)
	public String getStatus_pagamento() {
		return status_pagamento;
	}
	public void setStatus_pagamento(String status_pagamento) {
		this.status_pagamento = status_pagamento;
	}
	@Column(name="NUMERO_Pedido", length = 100, nullable = false)
	public String getNumeroPedido() {
		return numero_pedido;
	}
	public void setNumeroPedido(String numero_pedido) {
		this.numero_pedido = numero_pedido;
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
		Pedido other = (Pedido) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", pagamento=" + pagamento + ", numeroPedido=" + numero_pedido
				+ ", status_pagamento=" + status_pagamento + ", listaProdutoPedido=" + listaProdutoPedido + "]";
	}
	
	@ManyToOne
	@JoinColumn(name = "CONFECCAO_ID", nullable = false)
	public Confeccao getConfeccao() {
		return confeccao;
	}
	public void setConfeccao(Confeccao confeccao) {
		this.confeccao = confeccao;
	}

}
