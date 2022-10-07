package com.projeto.model.service;

import java.util.List;

import javax.persistence.EntityTransaction;

import com.projeto.model.model.Cliente;
import com.projeto.model.repository.ClienteDAO;

public class ClienteService extends ConexaoService<Cliente> {
	
	private ClienteDAO clienteDao;
	
	public ClienteService(){
		clienteDao = new ClienteDAO(abrirBancoDados());
	}
	
	
	@Override
	public void save (Cliente cliente) {
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			clienteDao.save(cliente);
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
	
	@Override
	public void update (Cliente cliente) {
		
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			clienteDao.update(cliente);
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
	@Override
	public void delete (Cliente cliente){
	
		EntityTransaction trx = this.getEntityManager().getTransaction();
		try{
			trx.begin();
			clienteDao.delete(cliente);
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
	
	
	public Cliente FindByID (Integer id){

		Cliente cliente = clienteDao.findByID(id);
		return cliente;
	}
	

	public Integer countTotalRegistroClientes() {
		return clienteDao.countTotalRegistro(Cliente.class);
		
	}

	public List<Cliente> carregarListaCliente(Integer paginaAtual, Integer registroPorPagina) {
		
		return clienteDao.listPorPagina(paginaAtual, registroPorPagina, Cliente.class);
	}

	public List<Cliente> carregarListaCliente() {
		return clienteDao.findAll(Cliente.class);
		
	}
	
	public List<Cliente> mostrarCliente(String name){
		return clienteDao.BuscarClientePorNome(name);
	}

	public List<Cliente> carregarListaCliente(String nome) {
		return clienteDao.carregarListaCliente(nome);
	}
	
	public List<Cliente> carregarListaClientePorParamentro(Integer codigoInicial, Integer codigoFinal){
		return clienteDao.carregarListaClientePorParametro(codigoInicial, codigoFinal);
	}
	
}
