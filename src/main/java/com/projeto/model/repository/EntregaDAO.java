package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Entrega;


public class EntregaDAO {

	private EntityManager entityManager;
	
	public EntregaDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save (Entrega entrega){
		this.getEntityManage().persist(entrega);
	}
	
	public void update (Entrega entrega){
		this.getEntityManage().merge(entrega);
	}
	
	public void delete(Entrega entrega){
		this.getEntityManage().remove(entityManager.getReference(Entrega.class, entrega.getId()));
		
	}
	
	public Entrega findById(int id){
		return this.getEntityManage().find(Entrega.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Entrega> findALL(){
		Query query = this.getEntityManage().createQuery("SELECT a FROM Entrega a");
		List<Entrega> listaEntrega = query.getResultList();
		return listaEntrega;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entrega> listEntregaPorPagina(int paginaAtual, int registroPorPagina){
		
		List<Entrega> listaEntrega = new ArrayList<Entrega>();
		if(paginaAtual >=0) {
			Query query = this.getEntityManage().createQuery("SELECT a FROM Entrega a")
												.setFirstResult(paginaAtual)
												.setMaxResults(registroPorPagina);

			listaEntrega = query.getResultList();
		}
		
		
		return listaEntrega;
	
	}
	public List<Entrega> BuscarEntregaPorNome(String name){
		
		List<Entrega> listaEntrega = new ArrayList<>();
		
		TypedQuery<Entrega> query = this.getEntityManage().createQuery("SELECT a FROM Entrega a WHERE a.nome LIKE :name", Entrega.class)
														  .setParameter("name","%"+ name +"%");
														
		listaEntrega = query.getResultList();
		
		return listaEntrega;
	
	}
	
	
	
	public Integer countTotalRegistroEntrega() {
		
		Query query = this.getEntityManage().createQuery("SELECT count(a) FROM Entrega a");
		Long total = (Long) query.getSingleResult();
		
		return total.intValue();
	}
	
	
	
	public EntityManager getEntityManage() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Entrega> carregarListaEntrega() {
		List<Entrega> listaEntrega = new ArrayList<>();
		
		TypedQuery<Entrega> query = this.getEntityManage().createQuery("SELECT e FROM Entrega e", Entrega.class);
		
		listaEntrega = query.getResultList();
		
		return listaEntrega;
	}

	public List<Entrega> carregarListaEntrega(String name) {
		
		List<Entrega> listaEntrega = new ArrayList<>();
		
		TypedQuery<Entrega> query = this.getEntityManage().createQuery("SELECT a FROM Entrega a WHERE a.nome LIKE :name", Entrega.class)
														  .setParameter("name","%"+ name +"%");
														
		listaEntrega = query.getResultList();
		
		return listaEntrega;
	}

	public List<Entrega> carregarListaEntregaPorParametro(Integer codigoInicial, Integer codigoFinal) {
		List<Entrega> listaEntrega = new ArrayList<>();
		
		TypedQuery<Entrega> query = this.getEntityManage().createQuery("SELECT a FROM Entrega a"
																		+" WHERE " 
																		+" 		a.id >= :codigoInicial "
																		+" AND "
																		+" 		a.id <= :codigoFinal "
																		+" ORDER BY a.nome ", Entrega.class)
														  .setParameter("codigoInicial", codigoInicial)
														  .setParameter("codigoFinal", codigoFinal);
														
		listaEntrega = query.getResultList();
		
		return listaEntrega;
	}
	
	
	
	
}
