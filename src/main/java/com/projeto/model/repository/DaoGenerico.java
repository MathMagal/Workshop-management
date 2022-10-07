package com.projeto.model.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


public abstract class DaoGenerico<T,PK extends Serializable>{
		
	private EntityManager entityManager;
	private final Class<T> persistenceClass;
	
	@SuppressWarnings("unchecked")
	public DaoGenerico(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.persistenceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	
	public void save(T object) {
		this.getEntityManager().persist(object);
	}
	
	public void update(T object) {
		this.getEntityManager().merge(object);
	}
	
	public void delete(T object) {
		this.getEntityManager().remove(object);
	}
	
	
	public T findByID(PK id) {
		return this.getEntityManager().find(persistenceClass, id);
	}
	
	
	public List<T> findAll(Class<T> classe){
		List<T> lista = new ArrayList<T>();
		
		TypedQuery<T> query =  this.getEntityManager()
									.createQuery("SELECT o FROM "+classe.getSimpleName()+" o", persistenceClass);
		
		lista = query.getResultList();
		
		return lista;
	}
	
	
	public List<T> listPorPagina(int paginaAtual, int registroPorPagina, Class<T> classe){
		
		List<T> lista = new ArrayList<T>();
		if(paginaAtual >=0) {
			
			TypedQuery <T> query  = this.getEntityManager().createQuery("SELECT o FROM " + classe.getSimpleName()+" o", persistenceClass)
															.setFirstResult(paginaAtual)
															.setMaxResults(registroPorPagina);

			lista = query.getResultList();
		}
		
		
		return lista;
	
	}
	
	
	public Integer countTotalRegistro(Class<T> classe) {
		
		TypedQuery <Long> query = this.getEntityManager().createQuery("SELECT count(o) FROM " + classe.getSimpleName() + " o",Long.class);
		Long total = (Long) query.getSingleResult();
		
		return total.intValue();
	}
	
	
	

	public EntityManager getEntityManager() {
		return entityManager;
	}

	
	
}
