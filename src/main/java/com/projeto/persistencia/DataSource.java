package com.projeto.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;


public class DataSource {
	private static final String UNIT_NAME = "projeto";
	
	@PersistenceContext(unitName=UNIT_NAME)
	private EntityManager entityManager;
	
	
	private Connection connection = null;
	
	public DataSource() {
		this.entityManager = ConexaoBancoDados.getConexao().getEntityManager();
	}
	
	public Connection getConnection() {
		
		Session session =  this.entityManager.unwrap(Session.class);
		
		Conexao conexao = new Conexao();
		session.doWork(conexao);
		
		connection = conexao.getConnection();
		
		return connection;
	}
	
	
	private static class Conexao implements Work{
		
		private Connection connection;
		
		@Override
		public void execute(Connection connection) throws SQLException {
			
			this.connection = connection;
		}
		
		public Connection getConnection() {
			return this.connection;
		}
	}
	
}
