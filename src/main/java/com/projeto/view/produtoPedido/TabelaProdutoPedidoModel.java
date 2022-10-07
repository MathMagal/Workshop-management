package com.projeto.view.produtoPedido;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.projeto.model.model.ProdutoPedido;

public class TabelaProdutoPedidoModel extends AbstractTableModel {

	private static final long serialVersionUID = -445245047816016658L;
	
	
	private final String colunas[] = {
			"Numero Pedido",
			"Produto",
			"Preco Total",
			"Cliente",
			"Status Pagamento"
	};
	
	private static final int NUMERO_PEDIDO		= 0;
	private static final int PRODUTO 			= 1;
	private static final int PRECO_TOTAL		= 2;
	private static final int CLIENTE			= 3;
	private static final int STATUS				= 4;
	
	

	
	private List<ProdutoPedido> listaProdutoPedido;
	
	public TabelaProdutoPedidoModel() {
		listaProdutoPedido = new ArrayList<ProdutoPedido>();
	}
	
	public List<ProdutoPedido> getListaProdutoPedido() {
		return listaProdutoPedido;
	}

	public void setListaProduto(List<ProdutoPedido> listaProdutoPedido) {
		this.listaProdutoPedido = listaProdutoPedido;
	} 
	
	
	public ProdutoPedido getProdutoPedido(int index) {
		return getListaProdutoPedido().get(index);
	}
	
	
	public void saveProdutoPedido(ProdutoPedido produtoPedido){
		getListaProdutoPedido().add(produtoPedido); 
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void updateProdutoPedido(ProdutoPedido produtoPedido, int index){
		getListaProdutoPedido().set(index, produtoPedido);
		fireTableRowsUpdated(index, index);
	}
	
	public void removeProdutoPedido(int index) {
		getListaProdutoPedido().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getListaProdutoPedido().clear();
		fireTableDataChanged();
	}
	
	public String getColumnName(int index){
		return getColunas()[index];
	}
	

	@Override
	public int getRowCount() {
		return getListaProdutoPedido().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProdutoPedido produtoPedido = getListaProdutoPedido().get(rowIndex);
		
		switch(columnIndex){
			case NUMERO_PEDIDO:
				return produtoPedido.getPedido().getNumeroPedido();
			case PRODUTO:
				return produtoPedido.getProduto().getTipo_produto();
			case PRECO_TOTAL:
				return produtoPedido.getProduto().getPreco_total();
			case CLIENTE:
				return produtoPedido.getProduto().getCliente().getNome();
			case STATUS:
				return produtoPedido.getPedido().getStatus_pagamento();
			default:
				return produtoPedido;
		
		}
		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
			switch(columnIndex) {
				case NUMERO_PEDIDO:
					return String.class;
				case PRODUTO:
					return String.class;
				case PRECO_TOTAL:
					return String.class;
				case CLIENTE:
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
