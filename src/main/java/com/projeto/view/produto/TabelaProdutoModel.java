package com.projeto.view.produto;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import com.projeto.model.model.Produto;

public class TabelaProdutoModel extends AbstractTableModel {

	private static final long serialVersionUID = -445245047816016658L;
	
	
	private final String colunas[] = {
			"Código",
			"Produto",
			"Preço Unitário",
			"Quantidade",
			"Preço Total",
			"Cliente"
	};
	
	private static final int CODIGO 	= 0;
	private static final int PRODUTO	= 1;
	private static final int PRECO 		= 2;
	private static final int QUANTIDADE = 3;
	private static final int PRECO_TOTAL= 4;
	private static final int CLIENTE 	= 5;

	
	private List<Produto> listaProduto;
	
	

	

	public TabelaProdutoModel() {
		listaProduto = new ArrayList<Produto>();
	}
	
	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	} 
	
	
	public Produto getProduto(int index) {
		return getListaProduto().get(index);
	}
	
	
	public void saveProduto(Produto produto){
		getListaProduto().add(produto); 
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void updateProduto(Produto produto, int index){
		getListaProduto().set(index, produto);
		fireTableRowsUpdated(index, index);
	}
	
	public void removeProduto(int index) {
		getListaProduto().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getListaProduto().clear();
		fireTableDataChanged();
	}
	
	public String getColumnName(int index){
		return getColunas()[index];
	}
	

	@Override
	public int getRowCount() {
		return getListaProduto().size();
	}

	@Override
	public int getColumnCount() {
		return getColunas().length;
	}

	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Produto produto = getListaProduto().get(rowIndex);
		
		switch(columnIndex){
			case CODIGO:
				return produto.getId();
			case PRODUTO:
				return produto.getTipo_produto();
			case PRECO:
				return produto.getPreço_produto();
			case QUANTIDADE:
				return produto.getQuantidade();
			case PRECO_TOTAL:
				return produto.getPreco_total();
			case CLIENTE:
				return produto.getCliente().getNome();
		
			default:
				return produto;
		
		}
		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
			switch(columnIndex) {
				case CODIGO:
					return Integer.class;
				case PRODUTO:
					return String.class;
				case PRECO:
					return String.class;
				case QUANTIDADE:
					return Integer.class;
				case PRECO_TOTAL:
					return String.class;
				case CLIENTE:
					return String.class;
				default:
					return null;
			}
		
		
	}
	
	
	public String[] getColunas() {
		return colunas;
	}

}
