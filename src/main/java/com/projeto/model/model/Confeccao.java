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
@Table(name="TAB_CONFECCAO")
public class Confeccao {
	private int id;
	private String dataInicio;
	private String dataFim;
	private String estado_confeccao;
	private Entrega entrega;
	
	private List<Pedido> listaPedido;
	
	public Confeccao() {
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_Cofeccao")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="ESTADO_confeccao", length = 20, nullable = false)
	public String getEstado_confeccao() {
		return estado_confeccao;
	}
	public void setEstado_confeccao(String estado_confeccao) {
		this.estado_confeccao = estado_confeccao;
	}
	@Column(name="INICIO_confeccao", length = 20, nullable = false)
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	@Column(name="FIM_confeccao", length = 20, nullable = true)
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
	
	@OneToMany(mappedBy = "confeccao")
	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
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
		Confeccao other = (Confeccao) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Confeccao [id_confeccao=" + id + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim
				+ ", estado_confeccao=" + estado_confeccao + "]";
	}
	
	@ManyToOne
	@JoinColumn(name = "ENTREGA_ID", nullable = true)
	public Entrega getEntrega() {
		return entrega;
	}
	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}
	
}
