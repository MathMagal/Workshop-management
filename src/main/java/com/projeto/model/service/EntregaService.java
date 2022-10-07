package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Entrega;
import com.projeto.model.repository.EntregaDAO;
import com.projeto.persistencia.ConexaoBancoDados;

public class EntregaService {
	
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	private EntregaDAO entregaDao;
	
	public EntregaService(){
		this.entityManager = null;
		abrirBancoDados();
	}
	
	private void abrirBancoDados() {
		if(Objects.isNull(this.entityManager)){
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager());
		}
		
		if(!this.getEntityManager().isOpen()) {
			setEntityManager(ConexaoBancoDados.getConexao().getEntityManager());
		}
		entregaDao = new EntregaDAO(this.entityManager);
	}
	
	private void fecharBancoDados(){
		if(this.getEntityManager().isOpen()){
			this.getEntityManager().close();
		}
	}
	
	
	public void save (Entrega entrega) {
		 
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			entregaDao.save(entrega);
			trx.commit();
		}catch(Throwable t) {
			t.printStackTrace();
			if(trx.isActive()) {
				trx.rollback();
			}
		}finally{
			fecharBancoDados();
		}
		
	}
	
	public void update (Entrega entrega) {
		
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			entregaDao.update(entrega);
			trx.commit();
		}catch(Throwable t) {
			t.printStackTrace();
			if(trx.isActive()) {
				trx.rollback();
			}
		}finally{
			fecharBancoDados();
		}
	}
	
	public void delete (Entrega entrega){
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			entregaDao.delete(entrega);
			trx.commit();
		}catch(Throwable t) {
			t.printStackTrace();
			if(trx.isActive()) {
				trx.rollback();
			}
		}finally{
			fecharBancoDados();
		}
	}
	
	
	public Entrega FindByID (Integer id){

		Entrega entrega = entregaDao.findById(id);
		fecharBancoDados();
		return entrega;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Integer countTotalRegistroEntregas() {
		return entregaDao.countTotalRegistroEntrega();
		
	}

	public List<Entrega> carregarListaEntrega(Integer paginaAtual, Integer registroPorPagina) {
		
		return entregaDao.listEntregaPorPagina(paginaAtual, registroPorPagina);
	}

	public List<Entrega> carregarListaEntrega() {
		return entregaDao.carregarListaEntrega();
		
	}
	
	public List<Entrega> mostrarEntrega(String name){
		return entregaDao.BuscarEntregaPorNome(name);
	}

	public List<Entrega> carregarListaEntrega(String nome) {
		return entregaDao.carregarListaEntrega(nome);
	}
	
	public List<Entrega> carregarListaEntregaPorParamentro(Integer codigoInicial, Integer codigoFinal){
		return entregaDao.carregarListaEntregaPorParametro(codigoInicial, codigoFinal);
	}
	
}
