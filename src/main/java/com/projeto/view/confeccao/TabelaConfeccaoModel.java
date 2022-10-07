package com.projeto.view.confeccao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Confeccao;

public class TabelaConfeccaoModel extends AbstractTableModel {

	private static final long serialVersionUID = -445245047816016658L;
	
	
	private final String colunas[] = {
			"Código",
			"Estado Confecção",
			"Data Inicio",
			"Data Fim"
	};
	
	private static final int CODIGO 	= 0;
	private static final int ESTADO		= 1;
	private static final int INICIO  	= 2;
	private static final int FIM 		= 3;
	
	private List<Confeccao> listaConfeccao;
	

	public TabelaConfeccaoModel() {
		listaConfeccao = new ArrayList<Confeccao>();
	}
	
	public List<Confeccao> getListaConfeccao() {
		return listaConfeccao;
	}

	public void setListaConfeccao(List<Confeccao> listaConfeccao) {
		this.listaConfeccao = listaConfeccao;
	} 
	
	
	public Confeccao getConfeccao(int index) {
		return getListaConfeccao().get(index);
	}
	
	
	public void saveConfeccao(Confeccao confeccao){
		getListaConfeccao().add(confeccao); 
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void updateConfeccao(Confeccao confeccao, int index){
		getListaConfeccao().set(index, confeccao);
		fireTableRowsUpdated(index, index);
	}
	
	public void removeConfeccao(int index) {
		getListaConfeccao().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getListaConfeccao().clear();
		fireTableDataChanged();
	}
	
	public String getColumnName(int index){
		return getColunas()[index];
	}
	

	@Override
	public int getRowCount() {
		return getListaConfeccao().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Confeccao confeccao = getListaConfeccao().get(rowIndex);
		
		switch(columnIndex){
			case CODIGO:
				return confeccao.getId();
			case ESTADO:
				return confeccao.getEstado_confeccao();
			case INICIO:
				return confeccao.getDataInicio();
			case FIM:
				return confeccao.getDataFim();
			default:
				return confeccao;
		
		}
		
			

		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case CODIGO:
				return Integer.class;
			case ESTADO:
				return String.class;
			case INICIO:
				return String.class;
			case FIM:
				return String.class;
			default:
				return null;
		}
	}
	
	
	public String[] getColunas() {
		return colunas;
	}
}
