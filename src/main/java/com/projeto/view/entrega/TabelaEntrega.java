package com.projeto.view.entrega;

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
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.projeto.model.model.Entrega;
import com.projeto.model.service.EntregaService;
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
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;

public class TabelaEntrega extends JInternalFrame{

	
	private static final long serialVersionUID = 5216390601550814551L;
	
	private static final int CODIGO 		= 0;
	private static final int ESTADO			= 1;
	private static final int DATA 			= 2;
	private static final int ENTREGADOR 	= 3;
	
	private JTable tabelaEntregas;
	private JTextField textFieldBuscarNome;
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
	private JButton btnRelatorio;
	
	private JLabel lblTotalRegistro;
	private JLabel lblShowRegistros;
	private JLabel lblTotalPagina;
	private JLabel lblShowTotal;
	private JLabel lblPgAtual;
	private JLabel lblShowPagina;
	
	
	private TabelaEntregaModel tabelaEntregaModel;
	private TableRowSorter<TabelaEntregaModel> sortTabelaEntrega;
	
	private EntregaService EntregaService;
	
	private Integer totalRegistros = 0;
	private Integer registroPorPagina = 5;
	private Integer totalPaginas = 1;
	private Integer paginaAtual = 1;
	
	
	private static TabelaEntrega tabelaEntrega;
	
	public static  TabelaEntrega getInstancia() {
		
		if(Objects.isNull(tabelaEntrega)) {
			tabelaEntrega = new TabelaEntrega();
		}
		
		return tabelaEntrega;
	}
	
	private TabelaEntrega() {
		setTitle("Tabela Entrega");
		//setIconImage(Toolkit.getDefaultToolkit().getImage(TabelaEntrega.class.getResource("/imagens/list.png")));
		setBounds(new Rectangle(100, 100, 1000, 636));
		//this.setLocationRelativeTo(null);
		
		initComponents();
		iniciarPaginacao();
		CreatEvents();
		setResizable(false);
		
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
		
		textFieldBuscarNome.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeEntrega();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeEntrega();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeEntrega();
			}
		});
		
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EntregaReports EntregaReports = new EntregaReports(totalRegistros);
				EntregaReports.setLocationRelativeTo(null);
				EntregaReports.setVisible(ProcessamentoDados.VERDADEIRO);
			}
		});
		
		
	}
	
	private void incluirNovoRegistro() {
		EntregaFrame EntregaFrame = new EntregaFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tabelaEntregas, tabelaEntregaModel, 0);
		EntregaFrame.setLocationRelativeTo(null);
		EntregaFrame.setVisible(true);
	}
	
	protected void alterarRegistro() {
		
		Integer  linha = 0;
		
		if(tabelaEntregas.getSelectedRow() != -1 && tabelaEntregas.getSelectedRow() < tabelaEntregaModel.getRowCount()) {
			linha = tabelaEntregas.getSelectedRow();
			EntregaFrame EntregaFrame = new EntregaFrame(new JFrame(), true, ProcessamentoDados.ALTERAR, tabelaEntregas, tabelaEntregaModel, linha);
			EntregaFrame.setLocationRelativeTo(null);
			EntregaFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	protected void deletarRegistro() {
		Integer  linha = 0;
		
		if(tabelaEntregas.getSelectedRow() != -1 && tabelaEntregas.getSelectedRow() < tabelaEntregaModel.getRowCount()) {
			linha = tabelaEntregas.getSelectedRow();
			EntregaFrame EntregaFrame = new EntregaFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR, tabelaEntregas, tabelaEntregaModel, linha);
			EntregaFrame.setLocationRelativeTo(null);
			EntregaFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	private void consultarRegistros() {
		Integer  linha = 0;
		
		if(tabelaEntregas.getSelectedRow() != -1 && tabelaEntregas.getSelectedRow() < tabelaEntregaModel.getRowCount()) {
			linha = tabelaEntregas.getSelectedRow();
			EntregaFrame EntregaFrame = new EntregaFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR, tabelaEntregas, tabelaEntregaModel, linha);
			EntregaFrame.setLocationRelativeTo(null);
			EntregaFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	protected void filtraNomeEntrega() {
		List<Entrega> listaEntrega = new ArrayList<Entrega>();
		
		listaEntrega = EntregaService.mostrarEntrega(textFieldBuscarNome.getText());
		tabelaEntregaModel.setListaEntrega(listaEntrega);
		
		sortTabelaEntrega = new TableRowSorter<TabelaEntregaModel>(tabelaEntregaModel);
		tabelaEntregas.setRowSorter(sortTabelaEntrega);
    }
	
	private void iniciarPaginacao() {
		
		
		
		List<Entrega> listaEntrega = new ArrayList<Entrega>();
		
		
		
		
		totalRegistros = buscarTotalRegistrosEntrega();
		
		registroPorPagina = Integer.valueOf(comboBoxRegistro.getSelectedItem().toString());
		
		
		Double totalPaginaTabela = Math.ceil(totalRegistros / registroPorPagina.doubleValue());
		
		totalPaginas = totalPaginaTabela.intValue();
		
		lblShowRegistros.setText(ProcessamentoDados.converterInteiroParaString(totalRegistros));
		lblShowTotal.setText(ProcessamentoDados.converterInteiroParaString(totalPaginas));
		lblShowPagina.setText(ProcessamentoDados.converterInteiroParaString(paginaAtual));
		
		
		@SuppressWarnings("serial")
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        Component cor = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 

		        String status = (String) value;
		        if ("Entregue".equals(status)) {
		            cor.setForeground(ProcessamentoDados.VERDE);
		        } else  cor.setForeground(ProcessamentoDados.VERMELHO);
		           
		        return cor;
		    }
		};
		
		
		
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
		
		
		
		
		listaEntrega = carregarListaEntrega(paginaAtual, registroPorPagina);
		
		
		tabelaEntregaModel.setListaEntrega(listaEntrega);
		tabelaEntregas.setModel(tabelaEntregaModel);
		
		
		tabelaEntregas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		sortTabelaEntrega = new TableRowSorter<TabelaEntregaModel>(tabelaEntregaModel);
		
		tabelaEntregas.setRowSorter(sortTabelaEntrega);
		
		tabelaEntregas.setAutoCreateRowSorter(true);
		tabelaEntregas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaEntregaModel.fireTableDataChanged();
		
		tabelaEntregas.getColumnModel().getColumn(CODIGO).setPreferredWidth(5);
		tabelaEntregas.getColumnModel().getColumn(ESTADO).setPreferredWidth(20);
		tabelaEntregas.getColumnModel().getColumn(DATA).setPreferredWidth(20);
		tabelaEntregas.getColumnModel().getColumn(ENTREGADOR).setPreferredWidth(50);
		
		
		tabelaEntregas.getColumnModel().getColumn(CODIGO).setCellRenderer(ProcessamentoDados.alinharColunaCentro());
		tabelaEntregas.getColumnModel().getColumn(DATA).setCellRenderer(ProcessamentoDados.alinharColunaCentro());
		tabelaEntregas.getColumnModel().getColumn(ESTADO).setCellRenderer(renderer);
		//scrollPane.setViewportView(tabelaEntregas);	
	}

	private List<Entrega> carregarListaEntrega(Integer paginaAtual2, Integer registroPorPagina2) {
		
		EntregaService EntregaService = new EntregaService();
		return EntregaService.carregarListaEntrega(((paginaAtual-1)*registroPorPagina), registroPorPagina);
	}





	private Integer buscarTotalRegistrosEntrega() {
		return EntregaService.countTotalRegistroEntregas();
	}





	private void initComponents() {
		
		
		EntregaService = new EntregaService();
		tabelaEntregaModel = new TabelaEntregaModel();
		
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 10, 955, 535);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(38, 49, 888, 399);
		panel.add(scrollPane_1);
		
		
		tabelaEntregas = new JTable();
		scrollPane_1.setViewportView(tabelaEntregas);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(38, 464, 398, 41);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrimeiro.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 10, 85, 21);
		panel_1.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(105, 10, 85, 21);
		panel_1.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(200, 10, 85, 21);
		panel_1.add(btnProximo);
		
		btnUltima = new JButton("");
		btnUltima.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltima.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/go-last.png")));
		btnUltima.setBounds(295, 10, 85, 21);
		panel_1.add(btnUltima);
		
		lblPgAtual = new JLabel("P\u00E1gina Atual:");
		lblPgAtual.setBounds(471, 478, 77, 13);
		panel.add(lblPgAtual);
		
		lblShowPagina = new JLabel("10");
		lblShowPagina.setBounds(586, 478, 26, 13);
		panel.add(lblShowPagina);
		
		comboBoxRegistro = new JComboBox<String>();
		comboBoxRegistro.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20", "50"}));
		comboBoxRegistro.setBounds(195, 10, 45, 21);
		panel.add(comboBoxRegistro);
		
		JLabel lblQtdRegistro = new JLabel("Quantidade de Registro:");
		lblQtdRegistro.setBounds(42, 14, 143, 13);
		panel.add(lblQtdRegistro);
		
		lblTotalPagina = new JLabel("Total de p\u00E1gina:");
		lblTotalPagina.setBounds(467, 511, 91, 13);
		panel.add(lblTotalPagina);
		
		lblShowTotal = new JLabel("20");
		lblShowTotal.setBounds(586, 511, 45, 13);
		panel.add(lblShowTotal);
		
		JLabel lblBuscarNome = new JLabel("Buscar por nome:");
		lblBuscarNome.setBounds(319, 14, 104, 13);
		panel.add(lblBuscarNome);
		
		textFieldBuscarNome = new JTextField();
		textFieldBuscarNome.setBounds(442, 10, 484, 19);
		panel.add(textFieldBuscarNome);
		textFieldBuscarNome.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 49, 888, 399);
		panel.add(scrollPane);
		
		lblTotalRegistro = new JLabel("Total De Registros:");
		lblTotalRegistro.setBounds(671, 478, 129, 13);
		panel.add(lblTotalRegistro);
		
		lblShowRegistros = new JLabel("200");
		lblShowRegistros.setBounds(810, 478, 65, 13);
		panel.add(lblShowRegistros);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds(10, 556, 955, 42);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		btnInclusao = new JButton("Inclus\u00E3o");
		btnInclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInclusao.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/book_add.png")));
		btnInclusao.setMnemonic(KeyEvent.VK_I);
		btnInclusao.setBounds(35, 10, 128, 21);
		panel_2.add(btnInclusao);
		
		btnAlteração = new JButton("Altera\u00E7\u00E3o");
		btnAlteração.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlteração.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/book_edit.png")));
		btnAlteração.setMnemonic(KeyEvent.VK_A);
		btnAlteração.setBounds(173, 10, 128, 21);
		panel_2.add(btnAlteração);
		
		btnExclusao = new JButton("Exclus\u00E3o");
		btnExclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExclusao.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setBounds(311, 10, 128, 21);
		panel_2.add(btnExclusao);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/book_open.png")));
		btnConsultar.setBounds(449, 10, 128, 21);
		panel_2.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/sair.png")));
		btnFechar.setBounds(814, 10, 128, 21);
		panel_2.add(btnFechar);
		
		btnRelatorio = new JButton("Relatorio");
		btnRelatorio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRelatorio.setIcon(new ImageIcon(TabelaEntrega.class.getResource("/imagens/pdf.png")));
		
		btnRelatorio.setBounds(587, 10, 120, 21);
		panel_2.add(btnRelatorio);
		
	}
}
