package com.projeto.model.service;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.projeto.persistencia.ConexaoBancoDados;

public abstract class ConexaoService<T> {
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	
	public ConexaoService() {
		this.entityManager = ConexaoBancoDados.getConexao().getEntityManager();
	}
	
	
	public abstract void save(T object);
	public abstract void update(T object);
	public abstract void delete(T object);
	

	public EntityManager getEntityManager() {
		return entityManager;
	}


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public EntityManager abrirBancoDados() {
		if(Objects.isNull(this.getEntityManager())){
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager());
		}
		
		if(!this.getEntityManager().isOpen()) {
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager());
		}
		return getEntityManager();
	}
	
	public void fecharBancoDados(){
		if(this.getEntityManager().isOpen()){
			this.getEntityManager().close();
		}
	}
	
	
}
