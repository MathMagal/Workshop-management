package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Confeccao;


public class ConfeccaoDAO {

	private EntityManager entityManager;
	
	public ConfeccaoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save (Confeccao confeccao){
		this.getEntityManage().persist(confeccao);
	}
	
	public void update (Confeccao confeccao){
		this.getEntityManage().merge(confeccao);
	}
	
	public void delete(Confeccao confeccao){
		this.getEntityManage().remove(entityManager.getReference(Confeccao.class, confeccao.getId()));
		
	}
	
	public Confeccao findById(int id){
		return this.getEntityManage().find(Confeccao.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Confeccao> findALL(){
		Query query = this.getEntityManage().createQuery("SELECT a FROM Confeccao a");
		List<Confeccao> listaConfeccao = query.getResultList();
		return listaConfeccao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Confeccao> listConfeccaoPorPagina(int paginaAtual, int registroPorPagina){
		
		List<Confeccao> listaConfeccao = new ArrayList<Confeccao>();
		if(paginaAtual >=0) {
			Query query = this.getEntityManage().createQuery("SELECT a FROM Confeccao a")
												.setFirstResult(paginaAtual)
												.setMaxResults(registroPorPagina);

			listaConfeccao = query.getResultList();
		}
		
		
		return listaConfeccao;
	
	}
	public List<Confeccao> BuscarConfeccaoPorNome(String name){
		
		List<Confeccao> listaConfeccao = new ArrayList<>();
		
		TypedQuery<Confeccao> query = this.getEntityManage().createQuery("SELECT a FROM Confeccao a WHERE a.nome LIKE :name", Confeccao.class)
														  .setParameter("name","%"+ name +"%");
														
		listaConfeccao = query.getResultList();
		
		return listaConfeccao;
	
	}
	
	
	
	public Integer countTotalRegistroConfeccao() {
		
		Query query = this.getEntityManage().createQuery("SELECT count(a) FROM Confeccao a");
		Long total = (Long) query.getSingleResult();
		
		return total.intValue();
	}
	
	
	
	public EntityManager getEntityManage() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Confeccao> carregarListaConfeccao() {
		List<Confeccao> listaConfeccao = new ArrayList<>();
		
		TypedQuery<Confeccao> query = this.getEntityManage().createQuery("SELECT e FROM Confeccao e", Confeccao.class);
		
		listaConfeccao = query.getResultList();
		
		return listaConfeccao;
	}

	public List<Confeccao> carregarListaConfeccao(String name) {
		
		List<Confeccao> listaConfeccao = new ArrayList<>();
		
		TypedQuery<Confeccao> query = this.getEntityManage().createQuery("SELECT a FROM Confeccao a WHERE a.nome LIKE :name", Confeccao.class)
														  .setParameter("name","%"+ name +"%");
														
		listaConfeccao = query.getResultList();
		
		return listaConfeccao;
	}

	public List<Confeccao> carregarListaConfeccaoPorParametro(Integer codigoInicial, Integer codigoFinal) {
		List<Confeccao> listaConfeccao = new ArrayList<>();
		
		TypedQuery<Confeccao> query = this.getEntityManage().createQuery("SELECT a FROM Confeccao a"
																		+" WHERE " 
																		+" 		a.id >= :codigoInicial "
																		+" AND "
																		+" 		a.id <= :codigoFinal "
																		+" ORDER BY a.nome ", Confeccao.class)
														  .setParameter("codigoInicial", codigoInicial)
														  .setParameter("codigoFinal", codigoFinal);
														
		listaConfeccao = query.getResultList();
		
		return listaConfeccao;
	}
	
	
	
	
}
