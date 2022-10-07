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
@Table(name="TAB_CLIENTE")
public class Cliente {
	
	private Integer id;
	private String cpf;
	private String nome;
	private String celular;
	private String email;
	private String rua;
	private String bairro;
	private String numero;
	private String cep;
	private String cidade;
	
	
	private List<Produto> listaProduto;
	
	

	public Cliente() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_Cliente")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="CPF_Cliente", length = 20, nullable = false)
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	@Column(name="NOME_Cliente", length = 80, nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Column(name="CELULAR_Cliente", length = 30, nullable = false)
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	@Column(name="EMAIL_Cliente", length = 80, nullable = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="RUA_Cliente", length = 80, nullable = false)
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}
	@Column(name="BAIRRO_Cliente", length = 80, nullable = false)
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	@Column(name="NUM_Cliente", length = 10, nullable = false)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	@Column(name="CIDADE_Cliente", length = 80, nullable = false)
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Column(name="CEP_Cliente", length = 20, nullable = false)
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@OneToMany(mappedBy = "cliente")
	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
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
		Cliente other = (Cliente) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "cliente [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", celular=" + celular + ", email=" + email
				+ "]";
	}
	
	
	
}
