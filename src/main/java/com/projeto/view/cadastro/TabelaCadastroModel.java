package com.projeto.view.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Cadastro;

public class TabelaCadastroModel extends AbstractTableModel {

	private static final long serialVersionUID = -445245047816016658L;
	
	
	private final String colunas[] = {
			"Código",
			"Usuario",
			"Senha",
	};
	
	private static final int CODIGO 		= 0;
	private static final int USUARIO		= 1;
	private static final int SENHA			= 2;
	
	private List<Cadastro> listaCadastro;
	

	public TabelaCadastroModel() {
		listaCadastro = new ArrayList<Cadastro>();
	}
	
	public List<Cadastro> getListaCadastro() {
		return listaCadastro;
	}

	public void setListaCadastro(List<Cadastro> listaCadastro) {
		this.listaCadastro = listaCadastro;
	} 
	
	
	public Cadastro getCadastro(int index) {
		return getListaCadastro().get(index);
	}
	
	
	public void saveCadastro(Cadastro cadastro){
		getListaCadastro().add(cadastro); 
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void updateCadastro(Cadastro cadastro, int index){
		getListaCadastro().set(index, cadastro);
		fireTableRowsUpdated(index, index);
	}
	
	public void removeCadastro(int index) {
		getListaCadastro().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getListaCadastro().clear();
		fireTableDataChanged();
	}
	
	public String getColumnName(int index){
		return getColunas()[index];
	}
	

	@Override
	public int getRowCount() {
		return getListaCadastro().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cadastro Cadastro = getListaCadastro().get(rowIndex);
		
		switch(columnIndex){
			case CODIGO:
				return Cadastro.getId();
			case USUARIO:
				return Cadastro.getUsuario();
			case SENHA:
				return Cadastro.getSenha();
			default:
				return Cadastro;
		
		}
		
			

		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case CODIGO:
				return Integer.class;
			case USUARIO:
				return String.class;
			case SENHA:
				return String.class;
			default:
				return null;
		}
	}
	
	
	public String[] getColunas() {
		return colunas;
	}
}
