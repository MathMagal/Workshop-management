package com.projeto.model.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.projeto.model.model.Cadastro;


public class CadastroDAO {

	private EntityManager entityManager;
	
	public CadastroDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void save (Cadastro cadastro){
		this.getEntityManage().persist(cadastro);
	}
	
	public void update (Cadastro cadastro){
		this.getEntityManage().merge(cadastro);
	}
	
	public void delete(Cadastro cadastro){
		this.getEntityManage().remove(entityManager.getReference(Cadastro.class, cadastro.getId()));
		
	}
	
	public Cadastro findById(int id){
		return this.getEntityManage().find(Cadastro.class, id);
	}
	@SuppressWarnings("unchecked")
	public List<Cadastro> findALL(){
		Query query = this.getEntityManage().createQuery("SELECT a FROM Cadastro a");
		List<Cadastro> listaCadastro = query.getResultList();
		return listaCadastro;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cadastro> listCadastroPorPagina(int paginaAtual, int registroPorPagina){
		
		List<Cadastro> listaCadastro = new ArrayList<Cadastro>();
		if(paginaAtual >=0) {
			Query query = this.getEntityManage().createQuery("SELECT a FROM Cadastro a")
												.setFirstResult(paginaAtual)
												.setMaxResults(registroPorPagina);

			listaCadastro = query.getResultList();
		}
		
		
		return listaCadastro;
	
	}
	public List<Cadastro> BuscarCadastroPorNome(String name){
		
		List<Cadastro> listaCadastro = new ArrayList<>();
		
		TypedQuery<Cadastro> query = this.getEntityManage().createQuery("SELECT a FROM Cadastro a WHERE a.nome LIKE :name", Cadastro.class)
														  .setParameter("name","%"+ name +"%");
														
		listaCadastro = query.getResultList();
		
		return listaCadastro;
	
	}
	
	
	
	public Integer countTotalRegistroCadastro() {
		
		Query query = this.getEntityManage().createQuery("SELECT count(a) FROM Cadastro a");
		Long total = (Long) query.getSingleResult();
		
		return total.intValue();
	}
	
	
	
	public EntityManager getEntityManage() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Cadastro> carregarListaCadastro() {
		List<Cadastro> listaCadastro = new ArrayList<>();
		
		TypedQuery<Cadastro> query = this.getEntityManage().createQuery("SELECT e FROM Cadastro e", Cadastro.class);
		
		listaCadastro = query.getResultList();
		
		return listaCadastro;
	}

	public List<Cadastro> carregarListaCadastro(String name) {
		
		List<Cadastro> listaCadastro = new ArrayList<>();
		
		TypedQuery<Cadastro> query = this.getEntityManage().createQuery("SELECT a FROM Cadastro a WHERE a.nome LIKE :name", Cadastro.class)
														  .setParameter("name","%"+ name +"%");
														
		listaCadastro = query.getResultList();
		
		return listaCadastro;
	}

	
	public List<Cadastro> loginUsuario(String usuario, String senha) {
		List<Cadastro> listaCadastro = new ArrayList<>();
		
		TypedQuery<Cadastro> query = this.getEntityManage().createQuery("SELECT a FROM Cadastro a"
																		+" WHERE " 
																		+" 		a.usuario LIKE :usuario "
																		+" AND "
																		+" 		a.senha LIKE :senha ", Cadastro.class)
														  .setParameter("usuario", usuario)
														  .setParameter("senha", senha);
														
		listaCadastro = query.getResultList();
		
		return listaCadastro;
	}
}
