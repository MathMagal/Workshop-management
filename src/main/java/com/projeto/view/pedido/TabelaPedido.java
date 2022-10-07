package com.projeto.view.pedido;

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

import com.projeto.model.model.Pedido;
import com.projeto.model.service.PedidoService;
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

public class TabelaPedido extends JInternalFrame{

	
	private static final long serialVersionUID = 5216390601550814551L;
	
	private static final int CODIGO 	 = 0;
	private static final int PAGAMENTO   = 1;
	private static final int STATUS      = 2;
	
	
	
	
	private JTable tabelaPedidos;
	private JTextField textFieldBuscarPedido;
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
	
	
	private TabelaPedidoModel tabelaPedidoModel;
	private TableRowSorter<TabelaPedidoModel> sortTabelaPedido;
	
	private PedidoService PedidoService;
	
	private Integer totalRegistros = 0;
	private Integer registroPorPagina = 5;
	private Integer totalPaginas = 1;
	private Integer paginaAtual = 1;
	
	

	private static TabelaPedido tabelaPedido;
	
	public static  TabelaPedido getInstancia() {
		
		if(Objects.isNull(tabelaPedido)) {
			tabelaPedido = new TabelaPedido();
		}
		
		return tabelaPedido;
	}
	
	
	private TabelaPedido() {
		setTitle("Tabela Pedido");
		//setIconImage(Toolkit.getDefaultToolkit().getImage(TabelaPedido.class.getResource("/imagens/list.png")));
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
		textFieldBuscarPedido.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraPedido();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraPedido();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraPedido();
			}

			
		});
		
		
	}
	
	private void incluirNovoRegistro() {
		PedidoFrame PedidoFrame = new PedidoFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tabelaPedidos, tabelaPedidoModel, 0);
		PedidoFrame.setLocationRelativeTo(null);
		PedidoFrame.setVisible(true);
	}
	
	protected void alterarRegistro() {
		
		Integer  linha = 0;
		
		if(tabelaPedidos.getSelectedRow() != -1 && tabelaPedidos.getSelectedRow() < tabelaPedidoModel.getRowCount()) {
			linha = tabelaPedidos.getSelectedRow();
			PedidoFrame PedidoFrame = new PedidoFrame(new JFrame(), true, ProcessamentoDados.ALTERAR, tabelaPedidos, tabelaPedidoModel, linha);
			PedidoFrame.setLocationRelativeTo(null);
			PedidoFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	protected void deletarRegistro() {
		Integer  linha = 0;
		
		if(tabelaPedidos.getSelectedRow() != -1 && tabelaPedidos.getSelectedRow() < tabelaPedidoModel.getRowCount()) {
			linha = tabelaPedidos.getSelectedRow();
			PedidoFrame PedidoFrame = new PedidoFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR, tabelaPedidos, tabelaPedidoModel, linha);
			PedidoFrame.setLocationRelativeTo(null);
			PedidoFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	private void consultarRegistros() {
		Integer  linha = 0;
		
		if(tabelaPedidos.getSelectedRow() != -1 && tabelaPedidos.getSelectedRow() < tabelaPedidoModel.getRowCount()) {
			linha = tabelaPedidos.getSelectedRow();
			PedidoFrame PedidoFrame = new PedidoFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR, tabelaPedidos, tabelaPedidoModel, linha);
			PedidoFrame.setLocationRelativeTo(null);
			PedidoFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void filtraPedido() {
		
		System.out.println("teste");
		RowFilter<TabelaPedidoModel, Object> rowFilter = null;
		String filter = textFieldBuscarPedido.getText();
		
		try {
			rowFilter = RowFilter.regexFilter(filter);
		}catch(PatternSyntaxException e) {
			return;
		}
		
		sortTabelaPedido.setRowFilter(rowFilter);
	}


	private void iniciarPaginacao() {
		
		
		
		List<Pedido> listaPedido = new ArrayList<Pedido>();
		
		
		
		totalRegistros = buscarTotalRegistrosPedido();
		
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
		
		
		listaPedido = carregarListaPedido(paginaAtual, registroPorPagina);
		
		
		tabelaPedidoModel.setListaPedido(listaPedido);
		tabelaPedidos.setModel(tabelaPedidoModel);
		tabelaPedidoModel.fireTableDataChanged();
		tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		sortTabelaPedido = new TableRowSorter<TabelaPedidoModel>(tabelaPedidoModel);
		tabelaPedidos.setRowSorter(sortTabelaPedido);
		
		tabelaPedidos.setAutoCreateRowSorter(true);
		tabelaPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaPedidoModel.fireTableDataChanged();
		
		
		tabelaPedidos.getColumnModel().getColumn(CODIGO).setPreferredWidth(10);
		tabelaPedidos.getColumnModel().getColumn(PAGAMENTO).setPreferredWidth(40);
		tabelaPedidos.getColumnModel().getColumn(STATUS).setPreferredWidth(40);
		
		
		
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
		
		
		
		tabelaPedidos.getColumnModel().getColumn(CODIGO).setCellRenderer(ProcessamentoDados.alinharColunaCentro());
		tabelaPedidos.getColumnModel().getColumn(STATUS).setCellRenderer(renderer);
		
		//scrollPane.setViewportView(tabelaPedidos);

	}


	private List<Pedido> carregarListaPedido(Integer paginaAtual2, Integer registroPorPagina2) {
		
		PedidoService pedidoService = new PedidoService();
		return pedidoService.carregarListaPedido(((paginaAtual2-1)*registroPorPagina2), registroPorPagina2);
	}





	private Integer buscarTotalRegistrosPedido() {
		return PedidoService.countTotalRegistroPedido();
	}





	private void initComponents() {
		
		
		PedidoService = new PedidoService();
		tabelaPedidoModel = new TabelaPedidoModel();
		
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
		
		
		tabelaPedidos = new JTable();
		scrollPane_1.setViewportView(tabelaPedidos);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(38, 412, 398, 41);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrimeiro.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 10, 85, 21);
		panel_1.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(105, 10, 85, 21);
		panel_1.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(200, 10, 85, 21);
		panel_1.add(btnProximo);
		
		btnUltima = new JButton("");
		btnUltima.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltima.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/go-last.png")));
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
		
		JLabel lblBuscarNome = new JLabel("Buscar por Pedido:");
		lblBuscarNome.setBounds(320, 14, 134, 13);
		panel.add(lblBuscarNome);
		
		textFieldBuscarPedido = new JTextField();
		textFieldBuscarPedido.setBounds(458, 10, 481, 19);
		panel.add(textFieldBuscarPedido);
		textFieldBuscarPedido.setColumns(10);
		
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
		btnInclusao.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/book_add.png")));
		btnInclusao.setMnemonic(KeyEvent.VK_I);
		btnInclusao.setBounds(35, 10, 128, 21);
		panel_2.add(btnInclusao);
		
		btnAlteração = new JButton("Altera\u00E7\u00E3o");
		btnAlteração.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlteração.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/book_edit.png")));
		btnAlteração.setMnemonic(KeyEvent.VK_A);
		btnAlteração.setBounds(173, 10, 128, 21);
		panel_2.add(btnAlteração);
		
		btnExclusao = new JButton("Exclus\u00E3o");
		btnExclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExclusao.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setBounds(311, 10, 128, 21);
		panel_2.add(btnExclusao);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/book_open.png")));
		btnConsultar.setBounds(449, 10, 128, 21);
		panel_2.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaPedido.class.getResource("/imagens/sair.png")));
		btnFechar.setBounds(587, 10, 128, 21);
		panel_2.add(btnFechar);
		
	}
}
