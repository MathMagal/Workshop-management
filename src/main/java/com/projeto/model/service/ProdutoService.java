package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Produto;
import com.projeto.model.repository.ProdutoDAO;
import com.projeto.persistencia.ConexaoBancoDados;

public class ProdutoService {

	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	private ProdutoDAO produtoDao;
	
	public ProdutoService(){
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
		produtoDao = new ProdutoDAO(this.entityManager);
	}
	
	

	private void fecharBancoDados(){
		if(this.getEntityManager().isOpen()){
			this.getEntityManager().close();
		}
	}
	

	public void save (Produto produto) {
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			produtoDao.save(produto);
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
	
	public void update (Produto produto) {
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			produtoDao.update(produto);
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
	
	public void delete (Produto produto){
		
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			produtoDao.delete(produto);
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
	
	public Produto FindByID (Integer id){
		abrirBancoDados();
		Produto produto = produtoDao.findById(id);
		fecharBancoDados();
		return produto;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Integer countTotalRegistroProduto() {
		return produtoDao.countTotalRegistroProduto();
	}

	public List<Produto> carregarListaProduto(Integer paginaAtual, Integer registroPorPagina) {
		
		return produtoDao.listProdutoPorPagina(paginaAtual, registroPorPagina);
	}
	
	public List<Produto> carregarListaProduto() {
		return produtoDao.carregarListaProduto();
		
	}
	
	public List<Produto> BuscarProduto(String tipo){
		return produtoDao.BuscarProduto(tipo);
	}
	
}