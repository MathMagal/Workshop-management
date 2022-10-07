package com.projeto.view.pedido;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import com.projeto.model.model.Pedido;

public class TabelaPedidoModel extends AbstractTableModel {

	private static final long serialVersionUID = -445245047816016658L;
	
	
	private final String colunas[] = {
			"Numero Pedido",
			"Pagamento",
			"Status"
	};
	
	private static final int CODIGO 	 = 0;
	private static final int PAGAMENTO   = 1;
	private static final int STATUS      = 2;

	
	private List<Pedido> listaPedido;
	
	

	public TabelaPedidoModel() {
		listaPedido = new ArrayList<Pedido>();
	}
	
	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	} 
	
	
	public Pedido getPedido(int index) {
		return getListaPedido().get(index);
	}
	
	public void savePedido(Pedido pedido){
		getListaPedido().add(pedido); 
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void updatePedido(Pedido pedido, int index){
		getListaPedido().set(index, pedido);
		fireTableRowsUpdated(index, index);
	}
	
	public void removePedido(int index) {
		getListaPedido().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getListaPedido().clear();
		fireTableDataChanged();
	}
	
	public String getColumnName(int index){
		return getColunas()[index];
	}
	

	@Override
	public int getRowCount() {
		return getListaPedido().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pedido pedido = getListaPedido().get(rowIndex);
		
		switch(columnIndex){
			case CODIGO:
				return pedido.getNumeroPedido();
			case PAGAMENTO:
				return pedido.getPagamento();
			case STATUS:
				return pedido.getStatus_pagamento();
			default:
				return pedido;
		
		}
		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
			switch(columnIndex) {
				case CODIGO:
					return Integer.class;
				case PAGAMENTO:
					return String.class;
				case STATUS:
					return String.class;
				default:
					return null;
			}
		
		
	}
	
	
	public String[] getColunas() {
		return colunas;
	}

}
