package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.ProdutoPedido;
import com.projeto.model.model.ProdutoPedidoPK;

public class ProdutoPedidoDAO {
private EntityManager entityManager;
	
	
	public ProdutoPedidoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save (ProdutoPedido ProdutoPedido){
		this.getEntityManage().persist(ProdutoPedido);
	}
	
	public void update (ProdutoPedido ProdutoPedido){
		this.getEntityManage().merge(ProdutoPedido);
	}
	
	public void delete(ProdutoPedido ProdutoPedido){
		this.getEntityManage().remove(entityManager.getReference(ProdutoPedido.class, ProdutoPedido.getId()));
	}
	
	public ProdutoPedido findById(ProdutoPedidoPK id){
		return this.getEntityManage().find(ProdutoPedido.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<ProdutoPedido> findALL(){
		Query query = this.getEntityManage().createQuery("SELECT a FROM ProdutoPedido a");
		List<ProdutoPedido> listaProdutoPedido = query.getResultList();
		return listaProdutoPedido;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoPedido> listProdutoPedidoPorPagina(int paginaAtual, int registroPorPagina){
		
		List<ProdutoPedido> listaProdutoPedido = new ArrayList<ProdutoPedido>();
		
		if(paginaAtual >=0) {
			Query query = this.getEntityManage().createQuery("SELECT a FROM ProdutoPedido a")
												.setFirstResult(paginaAtual)
												.setMaxResults(registroPorPagina);

			listaProdutoPedido = query.getResultList();
		}
		
		return listaProdutoPedido;
	}
	
	public Integer countTotalRegistroProdutoPedido() {
		
		Query query = this.getEntityManage().createQuery("SELECT count(a) FROM ProdutoPedido a");
		Long total = (Long) query.getSingleResult();
		
		return total.intValue();
	}
	
	public List<ProdutoPedido> carregarListaProdutoPedido() {
		List<ProdutoPedido> listaProdutoPedido = new ArrayList<>();
		
		TypedQuery<ProdutoPedido> query = this.getEntityManage().createQuery("SELECT e FROM ProdutoPedido e", ProdutoPedido.class);
		
		listaProdutoPedido = query.getResultList();
		
		return listaProdutoPedido;
	}
	
	
	public List<ProdutoPedido> BuscarProdutoPedido(String tipo){
		
		List<ProdutoPedido> listaProdutoPedido = new ArrayList<>();
		
		TypedQuery<ProdutoPedido> query = this.getEntityManage().createQuery("SELECT a FROM ProdutoPedido a WHERE a.tipo_ProdutoPedido LIKE :tipo", ProdutoPedido.class)
														  		.setParameter("tipo","%"+ tipo +"%");
														
		listaProdutoPedido = query.getResultList();
		
		return listaProdutoPedido;
	
	}
	
	
	public EntityManager getEntityManage() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
