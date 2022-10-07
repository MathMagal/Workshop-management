package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;


import com.projeto.model.model.Pedido;
import com.projeto.model.repository.PedidoDAO;
import com.projeto.persistencia.ConexaoBancoDados;

public class PedidoService {

	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	private PedidoDAO pedidoDao;
	
	public PedidoService(){
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
		pedidoDao = new PedidoDAO(this.entityManager);
	}
	
	

	private void fecharBancoDados(){
		if(this.getEntityManager().isOpen()){
			this.getEntityManager().close();
		}
	}
	

	public void save (Pedido pedido) {
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			pedidoDao.save(pedido);
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
	
	public void update (Pedido pedido) {
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			pedidoDao.update(pedido);
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
	
	public void delete (Pedido pedido){
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			pedidoDao.delete(pedido);
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
	
	public Pedido FindByID (Integer id){
		abrirBancoDados();
		Pedido pedido = pedidoDao.findById(id);
		fecharBancoDados();
		return pedido;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Integer countTotalRegistroPedido() {
		return pedidoDao.countTotalRegistroPedido();
		
	}

	public List<Pedido> carregarListaPedido(Integer paginaAtual, Integer registroPorPagina) {
		
		return pedidoDao.listPedidoPorPagina(paginaAtual, registroPorPagina);
	}
	
	public List<Pedido> carregarListaPedido() {
		return pedidoDao.carregarListaPedido();
		
	}
	
	public List<Pedido> BuscarPedido(String tipo){
		return pedidoDao.BuscarPedido(tipo);
	}
	
}
