package com.projeto.view.produtoPedido;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import com.projeto.model.model.ProdutoPedido;
import com.projeto.model.service.ProdutoPedidoService;
import com.projeto.util.ProcessamentoDados;

import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;

public class TabelaProdutoPedido extends JInternalFrame{

	
	private static final long serialVersionUID = 5216390601550814551L;
	
	private static final int NUMERO_PEDIDO		= 0;
	private static final int PRODUTO 			= 1;
	private static final int PRECO_TOTAL		= 2;
	private static final int CLIENTE			= 3;
	private static final int STATUS				= 4;
	
	
	private JTable tabelaProdutoPedidos;
	private JTextField textFieldBuscarProduto;
	private JScrollPane scrollPane;
	private JComboBox<String> comboBoxRegistro;
	
	private JButton btnPrimeiro;
	private JButton btnProximo;
	private JButton btnAnterior;
	private JButton btnUltima;
	private JButton btnInclusao;
	private JButton btnAlteração;
	private JButton btnExclusao;
	private JButton btnFechar;
	private JButton btnConsultar;
	
	
	private JLabel lblTotalRegistro;
	private JLabel lblShowRegistros;
	private JLabel lblTotalPagina;
	private JLabel lblShowTotal;
	private JLabel lblPgAtual;
	private JLabel lblShowPagina;
	
	
	private TabelaProdutoPedidoModel tabelaProdutoPedidoModel;
	private TableRowSorter<TabelaProdutoPedidoModel> sortTabelaProduto;
	
	private ProdutoPedidoService produtoPedidoService;
	
	private Integer totalRegistros = 0;
	private Integer registroPorPagina = 5;
	private Integer totalPaginas = 1;
	private Integer paginaAtual = 1;
	

	private static TabelaProdutoPedido tabelaProdutoPedido;
	
	public static  TabelaProdutoPedido getInstancia() {
		
		if(Objects.isNull(tabelaProdutoPedido)) {
			 tabelaProdutoPedido = new TabelaProdutoPedido();
		}
		
		return tabelaProdutoPedido;
	}
	
	
	private TabelaProdutoPedido() {
		setTitle("Tabela Produto - Pedido");
		//setIconImage(Toolkit.getDefaultToolkit().getImage(TabelaProduto.class.getResource("/imagens/list.png")));
		setBounds(new Rectangle(100, 100, 1000, 581));
		
		initComponents();
		iniciarPaginacao();
		CreatEvents();
		setResizable(false);
		//this.setLocationRelativeTo(null);
		
	}





	private void CreatEvents() {
		
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = 1;
				iniciarPaginacao();
			}
		});
		
		comboBoxRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registroPorPagina = Integer.valueOf(comboBoxRegistro.getSelectedItem().toString());
				iniciarPaginacao();
			}
		});
		
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(paginaAtual > 1) {
					paginaAtual = paginaAtual - 1;
					iniciarPaginacao();
				}
				
			}
		});
		
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(paginaAtual < totalPaginas) {
					paginaAtual = paginaAtual + 1;
					iniciarPaginacao();
				}
			}
		});
		
		btnUltima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = totalPaginas;
				iniciarPaginacao();
			}
		});
		
		btnInclusao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluirNovoRegistro();
			}
		});
		
		btnInclusao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_I) {
					incluirNovoRegistro();
				}
			}
		});
		
		btnAlteração.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarRegistro();
			}
		});
		
		btnAlteração.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_A){
					alterarRegistro();
				}
				
			}
		});
		
		btnExclusao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarRegistro();
			}
		});
		
		btnExclusao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_E){
					deletarRegistro();
				}
				
			}
		});
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarRegistros();
			}

			
		});
		btnConsultar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_C) {
					consultarRegistros();
				}
			}
		});
		
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnFechar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F) {
					dispose();
				}
			}
		});
		textFieldBuscarProduto.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraProduto();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraProduto();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraProduto();
			}

			
		});
		
		
	}
	
	private void incluirNovoRegistro() {
		ProdutoPedidoFrame ProdutoPedidoFrame = new ProdutoPedidoFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tabelaProdutoPedidos, tabelaProdutoPedidoModel, 0);
		ProdutoPedidoFrame.setLocationRelativeTo(null);
		ProdutoPedidoFrame.setVisible(true);
	}
	
	protected void alterarRegistro() {
		
		Integer  linha = 0;
		
		if(tabelaProdutoPedidos.getSelectedRow() != -1 && tabelaProdutoPedidos.getSelectedRow() < tabelaProdutoPedidoModel.getRowCount()) {
			linha = tabelaProdutoPedidos.getSelectedRow();
			ProdutoPedidoFrame ProdutoFrame = new ProdutoPedidoFrame(new JFrame(), true, ProcessamentoDados.ALTERAR, tabelaProdutoPedidos, tabelaProdutoPedidoModel, linha);
			ProdutoFrame.setLocationRelativeTo(null);
			ProdutoFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	protected void deletarRegistro() {
		Integer  linha = 0;
		
		if(tabelaProdutoPedidos.getSelectedRow() != -1 && tabelaProdutoPedidos.getSelectedRow() < tabelaProdutoPedidoModel.getRowCount()) {
			linha = tabelaProdutoPedidos.getSelectedRow();
			ProdutoPedidoFrame ProdutoFrame = new ProdutoPedidoFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR, tabelaProdutoPedidos, tabelaProdutoPedidoModel, linha);
			ProdutoFrame.setLocationRelativeTo(null);
			ProdutoFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	private void consultarRegistros() {
		Integer  linha = 0;
		
		if(tabelaProdutoPedidos.getSelectedRow() != -1 && tabelaProdutoPedidos.getSelectedRow() < tabelaProdutoPedidoModel.getRowCount()) {
			linha = tabelaProdutoPedidos.getSelectedRow();
			ProdutoPedidoFrame ProdutoFrame = new ProdutoPedidoFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR, tabelaProdutoPedidos, tabelaProdutoPedidoModel, linha);
			ProdutoFrame.setLocationRelativeTo(null);
			ProdutoFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void filtraProduto() {
		
		System.out.println("teste");
		RowFilter<TabelaProdutoPedidoModel, Object> rowFilter = null;
		String filter = textFieldBuscarProduto.getText();
		
		try {
			rowFilter = RowFilter.regexFilter(filter);
		}catch(PatternSyntaxException e) {
			return;
		}
		
		sortTabelaProduto.setRowFilter(rowFilter);
	}


	private void iniciarPaginacao() {
		
		
		
		List<ProdutoPedido> listaProdutoPedido = new ArrayList<ProdutoPedido>();
		
		
		@SuppressWarnings("serial")
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        Component cor = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 

		        String status = (String) value;
		        if ("Pago".equals(status)) {
		            cor.setForeground(ProcessamentoDados.VERDE);
		        } else {
		            cor.setForeground(ProcessamentoDados.VERMELHO);
		        }
		        return cor;
		    }
		};
		
		
		
		totalRegistros = buscarTotalRegistrosProduto();
		
		registroPorPagina = Integer.valueOf(comboBoxRegistro.getSelectedItem().toString());
		
		
		Double totalPaginaTabela = Math.ceil(totalRegistros / registroPorPagina.doubleValue());
		
		totalPaginas = totalPaginaTabela.intValue();
		
		lblShowRegistros.setText(ProcessamentoDados.converterInteiroParaString(totalRegistros));
		lblShowTotal.setText(ProcessamentoDados.converterInteiroParaString(totalPaginas));
		lblShowPagina.setText(ProcessamentoDados.converterInteiroParaString(paginaAtual));
		
		
		
		if(paginaAtual.equals(1)) {
			btnPrimeiro.setEnabled(false);
			btnAnterior.setEnabled(false);
		}else {
			btnPrimeiro.setEnabled(true);
			btnAnterior.setEnabled(true);
		}
		
		if(paginaAtual.equals(totalPaginas)) {
			btnUltima.setEnabled(false);
			btnProximo.setEnabled(false);
		}else {
			btnUltima.setEnabled(true);
			btnProximo.setEnabled(true);
		}
		
		if(paginaAtual>totalPaginas){
			paginaAtual = totalPaginas;
		}
		
		
		listaProdutoPedido = carregarListaProdutoPedido(paginaAtual, registroPorPagina);
		
		
		tabelaProdutoPedidoModel.setListaProduto(listaProdutoPedido);
		tabelaProdutoPedidos.setModel(tabelaProdutoPedidoModel);
		tabelaProdutoPedidoModel.fireTableDataChanged();
		tabelaProdutoPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		sortTabelaProduto = new TableRowSorter<TabelaProdutoPedidoModel>(tabelaProdutoPedidoModel);
		tabelaProdutoPedidos.setRowSorter(sortTabelaProduto);
		
		tabelaProdutoPedidos.setAutoCreateRowSorter(true);
		tabelaProdutoPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaProdutoPedidoModel.fireTableDataChanged();
		
		
		
		tabelaProdutoPedidos.getColumnModel().getColumn(NUMERO_PEDIDO).setPreferredWidth(10);
		tabelaProdutoPedidos.getColumnModel().getColumn(PRODUTO).setPreferredWidth(20);
		tabelaProdutoPedidos.getColumnModel().getColumn(PRECO_TOTAL).setPreferredWidth(10);
		tabelaProdutoPedidos.getColumnModel().getColumn(CLIENTE).setPreferredWidth(100);
		tabelaProdutoPedidos.getColumnModel().getColumn(STATUS).setPreferredWidth(20);
		
		tabelaProdutoPedidos.getColumnModel().getColumn(NUMERO_PEDIDO).setCellRenderer(ProcessamentoDados.alinharColunaCentro());
		tabelaProdutoPedidos.getColumnModel().getColumn(STATUS).setCellRenderer(renderer);

	}


	private List<ProdutoPedido> carregarListaProdutoPedido(Integer paginaAtual2, Integer registroPorPagina2) {
		
		ProdutoPedidoService produtoPedidoService = new ProdutoPedidoService();
		return produtoPedidoService.carregarListaProdutoPedido(((paginaAtual2-1)*registroPorPagina2), registroPorPagina2);
	}





	private Integer buscarTotalRegistrosProduto() {
		return produtoPedidoService.countTotalRegistroProdutoPedido();
	}





	private void initComponents() {
		
		
		produtoPedidoService = new ProdutoPedidoService();
		tabelaProdutoPedidoModel = new TabelaProdutoPedidoModel();
		
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 10, 971, 473);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(38, 49, 901, 342);
		panel.add(scrollPane_1);
		
		
		tabelaProdutoPedidos = new JTable();
		scrollPane_1.setViewportView(tabelaProdutoPedidos);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(38, 412, 398, 41);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrimeiro.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 10, 85, 21);
		panel_1.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(105, 10, 85, 21);
		panel_1.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(200, 10, 85, 21);
		panel_1.add(btnProximo);
		
		btnUltima = new JButton("");
		btnUltima.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltima.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/go-last.png")));
		btnUltima.setBounds(295, 10, 85, 21);
		panel_1.add(btnUltima);
		
		lblPgAtual = new JLabel("P\u00E1gina Atual:");
		lblPgAtual.setBounds(458, 427, 77, 13);
		panel.add(lblPgAtual);
		
		lblShowPagina = new JLabel("10");
		lblShowPagina.setBounds(545, 427, 26, 13);
		panel.add(lblShowPagina);
		
		comboBoxRegistro = new JComboBox<String>();
		comboBoxRegistro.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20", "50"}));
		comboBoxRegistro.setBounds(195, 10, 45, 21);
		panel.add(comboBoxRegistro);
		
		JLabel lblQtdRegistro = new JLabel("Quantidade de Registro:");
		lblQtdRegistro.setBounds(42, 14, 143, 13);
		panel.add(lblQtdRegistro);
		
		lblTotalPagina = new JLabel("Total de p\u00E1gina:");
		lblTotalPagina.setBounds(603, 427, 91, 13);
		panel.add(lblTotalPagina);
		
		lblShowTotal = new JLabel("20");
		lblShowTotal.setBounds(704, 427, 26, 13);
		panel.add(lblShowTotal);
		
		JLabel lblBuscarNome = new JLabel("Buscar por produto:");
		lblBuscarNome.setBounds(320, 14, 134, 13);
		panel.add(lblBuscarNome);
		
		textFieldBuscarProduto = new JTextField();
		textFieldBuscarProduto.setBounds(458, 10, 481, 19);
		panel.add(textFieldBuscarProduto);
		textFieldBuscarProduto.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 49, 901, 342);
		panel.add(scrollPane);
		
		lblTotalRegistro = new JLabel("Total De Registros:");
		lblTotalRegistro.setBounds(758, 427, 112, 13);
		panel.add(lblTotalRegistro);
		
		lblShowRegistros = new JLabel("200");
		lblShowRegistros.setBounds(876, 427, 45, 13);
		panel.add(lblShowRegistros);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds(129, 494, 736, 42);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		btnInclusao = new JButton("Inclus\u00E3o");
		btnInclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInclusao.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/book_add.png")));
		btnInclusao.setMnemonic(KeyEvent.VK_I);
		btnInclusao.setBounds(35, 10, 128, 21);
		panel_2.add(btnInclusao);
		
		btnAlteração = new JButton("Altera\u00E7\u00E3o");
		btnAlteração.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlteração.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/book_edit.png")));
		btnAlteração.setMnemonic(KeyEvent.VK_A);
		btnAlteração.setBounds(173, 10, 128, 21);
		panel_2.add(btnAlteração);
		
		btnExclusao = new JButton("Exclus\u00E3o");
		btnExclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExclusao.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setBounds(311, 10, 128, 21);
		panel_2.add(btnExclusao);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/book_open.png")));
		btnConsultar.setBounds(449, 10, 128, 21);
		panel_2.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaProdutoPedido.class.getResource("/imagens/sair.png")));
		btnFechar.setBounds(587, 10, 128, 21);
		panel_2.add(btnFechar);
		
	}
}
