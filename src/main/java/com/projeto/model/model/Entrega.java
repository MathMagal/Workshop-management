package com.projeto.model.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TAB_ENTREGA")
public class Entrega {
	
	private int id;
	private String nome_entregador;
	private String Data_entrega;
	private String EstadoEntrega;
	
	private List<Confeccao> listaConfeccao;
	
	public Entrega() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_Entrega")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="DATA_entrega", length = 80, nullable = false)
	public String getData_entrega() {
		return Data_entrega;
	}
	public void setData_entrega(String data_entrega) {
		Data_entrega = data_entrega;
	}
	@Column(name="ESTADO_Entrega", length = 80, nullable = false)
	public String getEstadoEntrega() {
		return EstadoEntrega;
	}
	public void setEstadoEntrega(String estadoEntrega) {
		EstadoEntrega = estadoEntrega;
	}
	@Column(name="NOME_entrega", length = 80, nullable = false)
	public String getNome_entregador() {
		return nome_entregador;
	}

	public void setNome_entregador(String nome_entregador) {
		this.nome_entregador = nome_entregador;
	}
	
	@OneToMany(mappedBy = "entrega")
	public List<Confeccao> getListaConfeccao() {
		return listaConfeccao;
	}

	public void setListaConfeccao(List<Confeccao> listaConfeccao) {
		this.listaConfeccao = listaConfeccao;
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
		Entrega other = (Entrega) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Entrega [id=" + id + ", nome_entregador=" + nome_entregador + ", Data_entrega=" + Data_entrega
				+ ", EstadoEntrega=" + EstadoEntrega + "]";
	}
	
}
