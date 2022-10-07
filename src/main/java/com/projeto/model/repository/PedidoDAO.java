package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Pedido;

public class PedidoDAO {
private EntityManager entityManager;
	
	
	public PedidoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save (Pedido pedido){
		this.getEntityManage().persist(pedido);
	}
	
	public void update (Pedido pedido){
		this.getEntityManage().merge(pedido);
	}
	
	public void delete(Pedido pedido){
		this.getEntityManage().remove(entityManager.getReference(Pedido.class, pedido.getId()));
	}
	
	public Pedido findById(int id){
		return this.getEntityManage().find(Pedido.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Pedido> findALL(){
		Query query = this.getEntityManage().createQuery("SELECT a FROM Pedido a");
		List<Pedido> listaPedido = query.getResultList();
		return listaPedido;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> listPedidoPorPagina(int paginaAtual, int registroPorPagina){
		
		List<Pedido> listaPedido = new ArrayList<Pedido>();
		
		if(paginaAtual >=0) {
			Query query = this.getEntityManage().createQuery("SELECT a FROM Pedido a")
												.setFirstResult(paginaAtual)
												.setMaxResults(registroPorPagina);

			listaPedido = query.getResultList();

			
		}
		
		return listaPedido;
	}
	
	public Integer countTotalRegistroPedido() {
		
		Query query = this.getEntityManage().createQuery("SELECT count(a) FROM Pedido a");
		Long total = (Long) query.getSingleResult();
		
		return total.intValue();
	}
	
	public List<Pedido> carregarListaPedido() {
		List<Pedido> listaPedido = new ArrayList<>();
		
		TypedQuery<Pedido> query = this.getEntityManage().createQuery("SELECT e FROM Pedido e", Pedido.class);
		
		listaPedido = query.getResultList();
		
		return listaPedido;
	}
	
public List<Pedido> BuscarPedido(String numero){
		
		List<Pedido> listaPedido = new ArrayList<>();
		
		TypedQuery<Pedido> query = this.getEntityManage().createQuery("SELECT a FROM Pedido a WHERE a.numero_pedido LIKE :numero", Pedido.class)
														  .setParameter("numero","%"+ numero +"%");
														
		listaPedido = query.getResultList();
		
		return listaPedido;
	
	}
	
	
	public EntityManager getEntityManage() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
