package com.projeto.model.service;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.projeto.model.model.Cadastro;
import com.projeto.model.repository.CadastroDAO;
import com.projeto.persistencia.ConexaoBancoDados;

public class CadastroService {
	
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	private CadastroDAO cadastroDao;
	
	public CadastroService(){
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
		cadastroDao = new CadastroDAO(this.entityManager);
	}
	
	private void fecharBancoDados(){
		if(this.getEntityManager().isOpen()){
			this.getEntityManager().close();
		}
	}
	
	
	public void save (Cadastro cadastro) {
		 
		
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			cadastroDao.save(cadastro);
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
	
	public void update (Cadastro cadastro) {
		
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			cadastroDao.update(cadastro);
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
	
	public void delete (Cadastro Cadastro){
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			cadastroDao.delete(Cadastro);
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
	
	
	public Cadastro FindByID (Integer id){

		Cadastro Cadastro = cadastroDao.findById(id);
		fecharBancoDados();
		return Cadastro;
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Integer countTotalRegistroCadastros() {
		return cadastroDao.countTotalRegistroCadastro();
		
	}

	public List<Cadastro> carregarListaCadastro(Integer paginaAtual, Integer registroPorPagina) {
		
		return cadastroDao.listCadastroPorPagina(paginaAtual, registroPorPagina);
	}

	public List<Cadastro> carregarListaCadastro() {
		return cadastroDao.carregarListaCadastro();
		
	}
	
	public List<Cadastro> mostrarCadastro(String name){
		return cadastroDao.BuscarCadastroPorNome(name);
	}

	public List<Cadastro> carregarListaCadastro(String nome) {
		return cadastroDao.carregarListaCadastro(nome);
	}
	
	
	public List<Cadastro> loginUsuario(String usuario, String senha){
		return cadastroDao.loginUsuario(usuario, senha);
	}
	
}
