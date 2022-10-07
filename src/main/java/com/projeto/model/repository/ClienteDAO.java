package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Cliente;


public class ClienteDAO extends DaoGenerico<Cliente, Integer>{

	
	public ClienteDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public List<Cliente> BuscarClientePorNome(String name){
		
		List<Cliente> listaCliente = new ArrayList<>();
		
		TypedQuery<Cliente> query = this.getEntityManager().createQuery("SELECT a FROM Cliente a WHERE a.nome LIKE :name", Cliente.class)
														  .setParameter("name","%"+ name +"%");
														
		listaCliente = query.getResultList();
		
		return listaCliente;
	
	}
	

	public List<Cliente> carregarListaCliente(String name) {
		
		List<Cliente> listaCliente = new ArrayList<>();
		
		TypedQuery<Cliente> query = this.getEntityManager().createQuery("SELECT a FROM Cliente a WHERE a.nome LIKE :name", Cliente.class)
														  .setParameter("name","%"+ name +"%");
														
		listaCliente = query.getResultList();
		
		return listaCliente;
	}

	public List<Cliente> carregarListaClientePorParametro(Integer codigoInicial, Integer codigoFinal) {
		List<Cliente> listaCliente = new ArrayList<>();
		
		TypedQuery<Cliente> query = this.getEntityManager().createQuery("SELECT a FROM Cliente a"
																		+" WHERE " 
																		+" 		a.id >= :codigoInicial "
																		+" AND "
																		+" 		a.id <= :codigoFinal "
																		+" ORDER BY a.nome ", Cliente.class)
														  .setParameter("codigoInicial", codigoInicial)
														  .setParameter("codigoFinal", codigoFinal);
														
		listaCliente = query.getResultList();
		
		return listaCliente;
	}
	
	
	
	
}
