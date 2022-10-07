package com.projeto.view.cliente;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Cliente;

public class TabelaClienteModel extends AbstractTableModel {

	private static final long serialVersionUID = -445245047816016658L;
	
	
	private final String colunas[] = {
			"Código",
			"Nome",
			"CPF",
			"Celular",
			"Email",
			"Rua",
			"Bairro",
			"Numero",
			"CEP",
			"Cidade"
	};
	
	private static final int CODIGO 	= 0;
	private static final int NOME 		= 1;
	private static final int CPF  		= 2;
	private static final int CELULAR 	= 3;
	private static final int EMAIL 		= 4;
	private static final int RUA 		= 5;
	private static final int BAIRRO 	= 6;
	private static final int NUMERO 	= 7;
	private static final int CEP 		= 8;
	private static final int CIDADE 	= 9;
	
	private List<Cliente> listaCliente;
	

	public TabelaClienteModel() {
		listaCliente = new ArrayList<Cliente>();
	}
	
	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	} 
	
	
	public Cliente getCliente(int index) {
		return getListaCliente().get(index);
	}
	
	
	public void saveCliente(Cliente cliente){
		getListaCliente().add(cliente); 
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void updateCliente(Cliente cliente, int index){
		getListaCliente().set(index, cliente);
		fireTableRowsUpdated(index, index);
	}
	
	public void removeCliente(int index) {
		getListaCliente().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getListaCliente().clear();
		fireTableDataChanged();
	}
	
	public String getColumnName(int index){
		return getColunas()[index];
	}
	

	@Override
	public int getRowCount() {
		return getListaCliente().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cliente cliente = getListaCliente().get(rowIndex);
		
		switch(columnIndex){
			case CODIGO:
				return cliente.getId();
			case NOME:
				return cliente.getNome();
			case CPF:
				return cliente.getCpf();
			case CELULAR:
				return cliente.getCelular();
			case EMAIL:
				return cliente.getEmail();
			case RUA:
				return cliente.getRua();
			case BAIRRO:
				return cliente.getBairro();
			case NUMERO:
				return cliente.getNumero();
			case CEP:
				return cliente.getCep();
			case CIDADE:
				return cliente.getCidade();
			default:
				return cliente;
		
		}
		
			

		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case CODIGO:
				return Integer.class;
			case NOME:
				return String.class;
			case CPF:
				return String.class;
			case CELULAR:
				return String.class;
			case EMAIL:
				return String.class;
			case RUA:
				return String.class;
			case BAIRRO:
				return String.class;
			case NUMERO:
				return String.class;
			case CEP:
				return String.class;
			case CIDADE:
				return String.class;
			default:
				return null;
		}
	}
	
	
	public String[] getColunas() {
		return colunas;
	}

}
