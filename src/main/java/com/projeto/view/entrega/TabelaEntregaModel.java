package com.projeto.view.entrega;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.Entrega;

public class TabelaEntregaModel extends AbstractTableModel {

	private static final long serialVersionUID = -445245047816016658L;
	
	
	private final String colunas[] = {
			"Código",
			"Estado Entrega",
			"Data Entrega",
			"Entregador"
	};
	
	private static final int CODIGO 		= 0;
	private static final int ESTADO			= 1;
	private static final int DATA 			= 2;
	private static final int ENTREGADOR 	= 3;
	
	private List<Entrega> listaEntrega;
	

	public TabelaEntregaModel() {
		listaEntrega = new ArrayList<Entrega>();
	}
	
	public List<Entrega> getListaEntrega() {
		return listaEntrega;
	}

	public void setListaEntrega(List<Entrega> listaEntrega) {
		this.listaEntrega = listaEntrega;
	} 
	
	
	public Entrega getEntrega(int index) {
		return getListaEntrega().get(index);
	}
	
	
	public void saveEntrega(Entrega Entrega){
		getListaEntrega().add(Entrega); 
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void updateEntrega(Entrega Entrega, int index){
		getListaEntrega().set(index, Entrega);
		fireTableRowsUpdated(index, index);
	}
	
	public void removeEntrega(int index) {
		getListaEntrega().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getListaEntrega().clear();
		fireTableDataChanged();
	}
	
	public String getColumnName(int index){
		return getColunas()[index];
	}
	

	@Override
	public int getRowCount() {
		return getListaEntrega().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Entrega Entrega = getListaEntrega().get(rowIndex);
		
		switch(columnIndex){
			case CODIGO:
				return Entrega.getId();
			case ESTADO:
				return Entrega.getEstadoEntrega();
			case DATA:
				return Entrega.getData_entrega();
			case ENTREGADOR:
				return Entrega.getNome_entregador();
			default:
				return Entrega;
		
		}
		
			

		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case CODIGO:
				return Integer.class;
			case ESTADO:
				return String.class;
			case DATA:
				return String.class;
			case ENTREGADOR:
				return String.class;
			default:
				return null;
		}
	}
	
	
	public String[] getColunas() {
		return colunas;
	}
}
