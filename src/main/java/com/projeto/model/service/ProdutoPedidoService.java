package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.ProdutoPedido;
import com.projeto.model.model.ProdutoPedidoPK;
import com.projeto.model.repository.ProdutoPedidoDAO;
import com.projeto.persistencia.ConexaoBancoDados;

public class ProdutoPedidoService {

	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	private ProdutoPedidoDAO produtoPedidoDao;
	
	public ProdutoPedidoService(){
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
		produtoPedidoDao = new ProdutoPedidoDAO(this.entityManager);
	}
	
	

	private void fecharBancoDados(){
		if(this.getEntityManager().isOpen()){
			this.getEntityManager().close();
		}
	}
	

	public void save (ProdutoPedido produtoPedido) {
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			produtoPedidoDao.save(produtoPedido);
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
	
	public void update (ProdutoPedido produtoPedido) {
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			produtoPedidoDao.update(produtoPedido);
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
	
	public void delete (ProdutoPedido produtoPedido){
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			produtoPedidoDao.delete(produtoPedido);
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
	
	public ProdutoPedido FindByID (ProdutoPedidoPK id){
		abrirBancoDados();
		ProdutoPedido produtoPedido = produtoPedidoDao.findById(id);
		fecharBancoDados();
		return produtoPedido;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Integer countTotalRegistroProdutoPedido() {
		return produtoPedidoDao.countTotalRegistroProdutoPedido();
	}

	public List<ProdutoPedido> carregarListaProdutoPedido(Integer paginaAtual, Integer registroPorPagina) {
		
		return produtoPedidoDao.listProdutoPedidoPorPagina(paginaAtual, registroPorPagina);
	}
	
	public List<ProdutoPedido> carregarListaProdutoPedido() {
		return produtoPedidoDao.carregarListaProdutoPedido();
		
	}
	
	public List<ProdutoPedido> BuscarProdutoPedido(String tipo){
		return produtoPedidoDao.BuscarProdutoPedido(tipo);
	}
	
}