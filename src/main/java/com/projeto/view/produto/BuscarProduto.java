package com.projeto.view.produto;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Produto;
import com.projeto.model.service.ProdutoService;
import com.projeto.util.ProcessamentoDados;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuscarProduto extends JDialog {

	
	
	private static final long serialVersionUID = 3277907702980939499L;
	
	private static final int CODIGO 	= 0;
	private static final int PRODUTO 	= 1;
	private static final int PRECO		= 2;
	private static final int QUANTIDADE = 3;
	private static final int PRECO_TOTAL= 4;
	private static final int CLIENTE 	= 5;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldBuscarProdutoPorNome;
	
	private JButton okButton; 
	private JButton cancelButton;
	private JButton btnCadastrarProduto;
	
	private JTable tableProduto;
	private JScrollPane scrollPane;
	
	private TabelaProdutoModel tabelaProdutoModel;
	private TableRowSorter<TabelaProdutoModel> sortTabelaProduto;
	private List<RowSorter.SortKey> sortKeys;
	
	private List<Produto> listaProduto;
	private Produto produto;
	private ProdutoService ProdutoService;
	
	private Integer codigoProduto = 0;
	private Integer row=0;
	private String nomeProduto;
	private boolean isConfirmado;
	
	

	public BuscarProduto(JFrame frame, boolean modal ) {
		
		super(frame, modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarProduto.class.getResource("/imagens/search.png")));
		initComponents();
		createEvents();
		iniciarDados();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	private void iniciarDados() {
		listaProduto = new ArrayList<Produto>();
	}
	
	protected void inserirProduto() {
		ProdutoFrame produtoFrame = new ProdutoFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tableProduto, tabelaProdutoModel, 0);
		produtoFrame.setLocationRelativeTo(null);
		produtoFrame.setVisible(true);
	    tabelaProdutoModel.fireTableDataChanged();	
	}

	protected void selecionaProduto(MouseEvent e) {
		row = tableProduto.getSelectedRow();
		
		 if ( tableProduto.getRowSorter() != null ) {
			row =  tableProduto.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaProduto() {
		if ( tableProduto.getSelectedRow() != -1 && 
			 tableProduto.getSelectedRow() < tabelaProdutoModel.getRowCount() ) {
			 produto = new Produto();
			 //setCodigoProduto(Integer.valueOf(tableProduto.getValueAt(tableProduto.getSelectedRow(), CODIGO).toString()));
			 //setNomeProduto(tableProduto.getValueAt(tableProduto.getSelectedRow(), NOME).toString());
			 //row = tableProduto.getSelectedRow();
			 //if ( tableProduto.getRowSorter() != null ) {
			 //	 row =  tableProduto.getRowSorter().convertRowIndexToModel(row);
			 //}
			 setConfirmado(ProcessamentoDados.VERDADEIRO);
			 produto = tabelaProdutoModel.getProduto(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}


	private List<Produto> carregarListaProduto() {
		
		ProdutoService produtoService = new ProdutoService();
		
		return produtoService.carregarListaProduto();

	}
	
	
	
	

	private void createEvents() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableProduto.getSelectedRow() != -1 && tableProduto.getSelectedRow()< tabelaProdutoModel.getRowCount()) {
					selecionaProduto();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnCadastrarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirProduto();
			}
		});
		textFieldBuscarProdutoPorNome.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				pesquisarProduto();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pesquisarProduto();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pesquisarProduto();
			}

			
		});
		
	}
	
	
	private void pesquisarProduto() {
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		ProdutoService = new ProdutoService();
		
		listaProduto = ProdutoService.BuscarProduto(textFieldBuscarProdutoPorNome.getText());
		tabelaProdutoModel.setListaProduto(listaProduto);
		
		sortTabelaProduto = new TableRowSorter<TabelaProdutoModel>(tabelaProdutoModel);
		tableProduto.setRowSorter(sortTabelaProduto);
	}
	

	private void initComponents() {
		
		setTitle("Buscar Produto");
		setBounds(100, 100, 1148, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonPane.setBounds(10, 430, 1122, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				okButton = new JButton("Confirmar");
				okButton.setToolTipText("Confirmar selecao de Produto");
				okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				okButton.setIcon(new ImageIcon(BuscarProduto.class.getResource("/imagens/ok.png")));
				okButton.setBounds(10, 5, 100, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setToolTipText("Cancelar selecao de Produto");
				cancelButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				cancelButton.setIcon(new ImageIcon(BuscarProduto.class.getResource("/imagens/iconFechar.png")));
				cancelButton.setBounds(120, 5, 100, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			tabelaProdutoModel = new TabelaProdutoModel();
			
			listaProduto = carregarListaProduto();
			tabelaProdutoModel.setListaProduto(listaProduto);
			
			sortTabelaProduto = new TableRowSorter<TabelaProdutoModel>(tabelaProdutoModel);
			JPanel panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel.setBounds(10, 11, 1122, 408);
			contentPanel.add(panel);
			contentPanel.setLayout(null);
			panel.setLayout(null);
			{
				textFieldBuscarProdutoPorNome = new JTextField();
				textFieldBuscarProdutoPorNome.setBounds(106, 24, 469, 20);
				panel.add(textFieldBuscarProdutoPorNome);
				textFieldBuscarProdutoPorNome.setColumns(10);
			}
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 55, 1098, 328);
			panel.add(scrollPane);
			{
				tableProduto = new JTable();
				scrollPane.setViewportView(tableProduto);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			}
			
			tableProduto.setModel(tabelaProdutoModel);
			tableProduto.setFillsViewportHeight(true);
			tableProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableProduto.setRowSorter(sortTabelaProduto);
			
			tableProduto.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selecionaProduto(e);
				}
			});
			sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(PRODUTO, SortOrder.ASCENDING));	
			
			tableProduto.setAutoCreateRowSorter(true);
			tableProduto.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			{
				JLabel lblBuscarProduto = new JLabel("Buscar Produto:");
				lblBuscarProduto.setBounds(10, 27, 92, 14);
				panel.add(lblBuscarProduto);
				lblBuscarProduto.setHorizontalAlignment(SwingConstants.LEFT);
			}
			
			tableProduto.getColumnModel().getColumn(CODIGO).setPreferredWidth(20);
			tableProduto.getColumnModel().getColumn(PRODUTO).setPreferredWidth(50);
			tableProduto.getColumnModel().getColumn(PRECO).setPreferredWidth(50);
			tableProduto.getColumnModel().getColumn(QUANTIDADE).setPreferredWidth(50);
			tableProduto.getColumnModel().getColumn(PRECO_TOTAL).setPreferredWidth(50);
			tableProduto.getColumnModel().getColumn(CLIENTE).setPreferredWidth(100);
			tabelaProdutoModel.fireTableDataChanged();
			
			
			
			btnCadastrarProduto = new JButton("Cadastrar");
			btnCadastrarProduto.setToolTipText("Cadastrar Produto");
			btnCadastrarProduto.setIcon(new ImageIcon(BuscarProduto.class.getResource("/imagens/evasion.png")));
			btnCadastrarProduto.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			btnCadastrarProduto.setBounds(230, 5, 100, 23);
			buttonPane.add(btnCadastrarProduto);
		}
		
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}
	
	
	public Integer getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Integer codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
}