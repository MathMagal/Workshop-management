package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Produto;

public class ProdutoDAO {
private EntityManager entityManager;
	
	
	public ProdutoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save (Produto produto){
		this.getEntityManage().persist(produto);
	}
	
	public void update (Produto produto){
		this.getEntityManage().merge(produto);
	}
	
	public void delete(Produto produto){
		this.getEntityManage().remove(entityManager.getReference(Produto.class, produto.getId()));
	}
	
	public Produto findById(int id){
		return this.getEntityManage().find(Produto.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Produto> findALL(){
		Query query = this.getEntityManage().createQuery("SELECT a FROM Produto a");
		List<Produto> listaProduto = query.getResultList();
		return listaProduto;
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> listProdutoPorPagina(int paginaAtual, int registroPorPagina){
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		
		if(paginaAtual >=0) {
			Query query = this.getEntityManage().createQuery("SELECT a FROM Produto a")
												.setFirstResult(paginaAtual)
												.setMaxResults(registroPorPagina);

			listaProduto = query.getResultList();

			
		}
		
		return listaProduto;
	}
	
	public Integer countTotalRegistroProduto() {
		
		Query query = this.getEntityManage().createQuery("SELECT count(a) FROM Produto a");
		Long total = (Long) query.getSingleResult();
		
		return total.intValue();
	}
	
	public List<Produto> carregarListaProduto() {
		List<Produto> listaProduto = new ArrayList<>();
		
		TypedQuery<Produto> query = this.getEntityManage().createQuery("SELECT e FROM Produto e", Produto.class);
		
		listaProduto = query.getResultList();
		
		return listaProduto;
	}
	
	
	public List<Produto> BuscarProduto(String tipo){
		
		List<Produto> listaProduto = new ArrayList<>();
		
		TypedQuery<Produto> query = this.getEntityManage().createQuery("SELECT a FROM Produto a WHERE a.tipo_produto LIKE :tipo", Produto.class)
														  .setParameter("tipo","%"+ tipo +"%");
														
		listaProduto = query.getResultList();
		
		return listaProduto;
	
	}
	
	
	public EntityManager getEntityManage() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
