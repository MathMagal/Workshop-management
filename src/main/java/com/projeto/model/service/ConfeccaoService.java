package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Confeccao;
import com.projeto.model.repository.ConfeccaoDAO;
import com.projeto.persistencia.ConexaoBancoDados;

public class ConfeccaoService {
	
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	private ConfeccaoDAO confeccaoDao;
	
	public ConfeccaoService(){
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
		confeccaoDao = new ConfeccaoDAO(this.entityManager);
	}
	
	private void fecharBancoDados(){
		if(this.getEntityManager().isOpen()){
			this.getEntityManager().close();
		}
	}
	
	
	public void save (Confeccao Confeccao) {
		 
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			confeccaoDao.save(Confeccao);
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
	
	public void update (Confeccao Confeccao) {
		
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			confeccaoDao.update(Confeccao);
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
	
	public void delete (Confeccao Confeccao){
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			confeccaoDao.delete(Confeccao);
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
	
	
	public Confeccao FindByID (Integer id){

		Confeccao confeccao = confeccaoDao.findById(id);
		fecharBancoDados();
		return confeccao;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Integer countTotalRegistroConfeccaos() {
		return confeccaoDao.countTotalRegistroConfeccao();
		
	}

	public List<Confeccao> carregarListaConfeccao(Integer paginaAtual, Integer registroPorPagina) {
		
		return confeccaoDao.listConfeccaoPorPagina(paginaAtual, registroPorPagina);
	}

	public List<Confeccao> carregarListaConfeccao() {
		return confeccaoDao.carregarListaConfeccao();
		
	}
	
	public List<Confeccao> mostrarConfeccao(String name){
		return confeccaoDao.BuscarConfeccaoPorNome(name);
	}

	public List<Confeccao> carregarListaConfeccao(String nome) {
		return confeccaoDao.carregarListaConfeccao(nome);
	}
	
	public List<Confeccao> carregarListaConfeccaoPorParamentro(Integer codigoInicial, Integer codigoFinal){
		return confeccaoDao.carregarListaConfeccaoPorParametro(codigoInicial, codigoFinal);
	}
	
}
