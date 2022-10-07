package com.projeto.view.cadastro;

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

import com.projeto.model.model.Cadastro;
import com.projeto.model.service.CadastroService;
import com.projeto.util.ProcessamentoDados;

import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;

public class TabelaCadastro extends JInternalFrame{

	
	private static final long serialVersionUID = 5216390601550814551L;
	
	private static final int CODIGO 		= 0;
	private static final int USUARIO		= 1;
	private static final int SENHA			= 2;
	
	private JTable tabelaCadastros;
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
	
	private JLabel lblTotalRegistro;
	private JLabel lblShowRegistros;
	private JLabel lblTotalPagina;
	private JLabel lblShowTotal;
	private JLabel lblPgAtual;
	private JLabel lblShowPagina;
	
	
	private TabelaCadastroModel tabelaCadastroModel;
	private TableRowSorter<TabelaCadastroModel> sortTabelaCadastro;
	
	private CadastroService cadastroService;
	
	private Integer totalRegistros = 0;
	private Integer registroPorPagina = 5;
	private Integer totalPaginas = 1;
	private Integer paginaAtual = 1;
	
	
	private static TabelaCadastro tabelaCadastro;
	
	public static  TabelaCadastro getInstancia() {
		
		if(Objects.isNull(tabelaCadastro)) {
			tabelaCadastro = new TabelaCadastro();
		}
		
		return tabelaCadastro;
	}
	
	private TabelaCadastro() {
		setTitle("Tabela Cadastro");
		//setIconImage(Toolkit.getDefaultToolkit().getImage(TabelaCadastro.class.getResource("/imagens/list.png")));
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
				filtraNomeCadastro();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeCadastro();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeCadastro();
			}
		});	
		
	}
	
	private void incluirNovoRegistro() {
		CadastroFrame cadastroFrame = new CadastroFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tabelaCadastros, tabelaCadastroModel, 0);
		cadastroFrame.setLocationRelativeTo(null);
		cadastroFrame.setVisible(true);
	}
	
	protected void alterarRegistro() {
		
		Integer  linha = 0;
		
		if(tabelaCadastros.getSelectedRow() != -1 && tabelaCadastros.getSelectedRow() < tabelaCadastroModel.getRowCount()) {
			linha = tabelaCadastros.getSelectedRow();
			CadastroFrame cadastroFrame = new CadastroFrame(new JFrame(), true, ProcessamentoDados.ALTERAR, tabelaCadastros, tabelaCadastroModel, linha);
			cadastroFrame.setLocationRelativeTo(null);
			cadastroFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	protected void deletarRegistro() {
		Integer  linha = 0;
		
		if(tabelaCadastros.getSelectedRow() != -1 && tabelaCadastros.getSelectedRow() < tabelaCadastroModel.getRowCount()) {
			linha = tabelaCadastros.getSelectedRow();
			CadastroFrame cadastroFrame = new CadastroFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR, tabelaCadastros, tabelaCadastroModel, linha);
			cadastroFrame.setLocationRelativeTo(null);
			cadastroFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	private void consultarRegistros() {
		Integer  linha = 0;
		
		if(tabelaCadastros.getSelectedRow() != -1 && tabelaCadastros.getSelectedRow() < tabelaCadastroModel.getRowCount()) {
			linha = tabelaCadastros.getSelectedRow();
			CadastroFrame cadastroFrame = new CadastroFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR, tabelaCadastros, tabelaCadastroModel, linha);
			cadastroFrame.setLocationRelativeTo(null);
			cadastroFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	protected void filtraNomeCadastro() {
		List<Cadastro> listaCadastro = new ArrayList<Cadastro>();
		
		listaCadastro = cadastroService.mostrarCadastro(textFieldBuscarNome.getText());
		tabelaCadastroModel.setListaCadastro(listaCadastro);
		
		sortTabelaCadastro = new TableRowSorter<TabelaCadastroModel>(tabelaCadastroModel);
		tabelaCadastros.setRowSorter(sortTabelaCadastro);
    }
	
	private void iniciarPaginacao() {
		
		
		
		List<Cadastro> listaCadastro = new ArrayList<Cadastro>();
		
		
		
		
		totalRegistros = buscarTotalRegistrosCadastro();
		
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
		
		
		
		
		listaCadastro = carregarListaCadastro(paginaAtual, registroPorPagina);
		
		
		tabelaCadastroModel.setListaCadastro(listaCadastro);
		tabelaCadastros.setModel(tabelaCadastroModel);
		
		
		tabelaCadastros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		sortTabelaCadastro = new TableRowSorter<TabelaCadastroModel>(tabelaCadastroModel);
		
		tabelaCadastros.setRowSorter(sortTabelaCadastro);
		
		tabelaCadastros.setAutoCreateRowSorter(true);
		tabelaCadastros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaCadastroModel.fireTableDataChanged();
		
		tabelaCadastros.getColumnModel().getColumn(CODIGO).setPreferredWidth(5);
		tabelaCadastros.getColumnModel().getColumn(USUARIO).setPreferredWidth(50);
		tabelaCadastros.getColumnModel().getColumn(SENHA).setPreferredWidth(20);
		
		
		tabelaCadastros.getColumnModel().getColumn(CODIGO).setCellRenderer(ProcessamentoDados.alinharColunaCentro());
		tabelaCadastros.getColumnModel().getColumn(USUARIO).setCellRenderer(ProcessamentoDados.alinharColunaCentro());
		//scrollPane.setViewportView(tabelaCadastros);	
	}

	private List<Cadastro> carregarListaCadastro(Integer paginaAtual2, Integer registroPorPagina2) {
		
		CadastroService cadastroService = new CadastroService();
		return cadastroService.carregarListaCadastro(((paginaAtual-1)*registroPorPagina), registroPorPagina);
	}





	private Integer buscarTotalRegistrosCadastro() {
		return cadastroService.countTotalRegistroCadastros();
	}





	private void initComponents() {
		
		
		cadastroService = new CadastroService();
		tabelaCadastroModel = new TabelaCadastroModel();
		
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
		
		
		tabelaCadastros = new JTable();
		scrollPane_1.setViewportView(tabelaCadastros);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(38, 464, 398, 41);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrimeiro.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 10, 85, 21);
		panel_1.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(105, 10, 85, 21);
		panel_1.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(200, 10, 85, 21);
		panel_1.add(btnProximo);
		
		btnUltima = new JButton("");
		btnUltima.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltima.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/go-last.png")));
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
		btnInclusao.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/book_add.png")));
		btnInclusao.setMnemonic(KeyEvent.VK_I);
		btnInclusao.setBounds(35, 10, 128, 21);
		panel_2.add(btnInclusao);
		
		btnAlteração = new JButton("Altera\u00E7\u00E3o");
		btnAlteração.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlteração.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/book_edit.png")));
		btnAlteração.setMnemonic(KeyEvent.VK_A);
		btnAlteração.setBounds(173, 10, 128, 21);
		panel_2.add(btnAlteração);
		
		btnExclusao = new JButton("Exclus\u00E3o");
		btnExclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExclusao.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setBounds(311, 10, 128, 21);
		panel_2.add(btnExclusao);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/book_open.png")));
		btnConsultar.setBounds(449, 10, 128, 21);
		panel_2.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaCadastro.class.getResource("/imagens/sair.png")));
		btnFechar.setBounds(814, 10, 128, 21);
		panel_2.add(btnFechar);
		
	}
}
