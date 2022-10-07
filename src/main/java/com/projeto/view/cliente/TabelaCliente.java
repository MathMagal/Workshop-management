package com.projeto.view.cliente;

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

import com.projeto.model.model.Cliente;
import com.projeto.model.service.ClienteService;
import com.projeto.util.ProcessamentoDados;

import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;

public class TabelaCliente extends JInternalFrame{

	
	private static final long serialVersionUID = 5216390601550814551L;
	
	private static final int CODIGO 	= 0;
	private static final int NOME 		= 1;
	private static final int CPF  		= 2;
	private static final int CELULAR 	= 3;
	private static final int EMAIL 		= 4;
	private static final int RUA 		= 5;
	private static final int BAIRRO 	= 6;
	private static final int NUMERO 	= 7;
	private static final int CEP 		= 8;
	private static final int CIDADE 	= 9;
	
	private JTable tabelaClientes;
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
	
	
	private TabelaClienteModel tabelaClienteModel;
	private TableRowSorter<TabelaClienteModel> sortTabelaCliente;
	
	private ClienteService clienteService;
	
	private Integer totalRegistros = 0;
	private Integer registroPorPagina = 5;
	private Integer totalPaginas = 1;
	private Integer paginaAtual = 1;
	
	private static TabelaCliente tabelaCliente;
	
	public static  TabelaCliente getInstancia() {
		
		if(Objects.isNull(tabelaCliente)) {
			tabelaCliente = new TabelaCliente();
		}
		
		return tabelaCliente;
	}
	
	private TabelaCliente() {
		setTitle("Tabela Cliente");
		//setIconImage(Toolkit.getDefaultToolkit().getImage(TabelaCliente.class.getResource("/imagens/list.png")));
		setBounds(new Rectangle(100, 100, 1276, 550));
		//this.setLocationRelativeTo(null);
		
		initComponents();
		iniciarPaginacao();
		CreatEvents();
		fecharTabelaCliente();
		
	}

	private void fecharTabelaCliente() {
		ProcessamentoDados.FehcarJanela(this);
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
				filtraNomeCliente();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeCliente();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeCliente();
			}
		});
		
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteReports clienteReports = new ClienteReports(totalRegistros);
				clienteReports.setLocationRelativeTo(null);
				clienteReports.setVisible(ProcessamentoDados.VERDADEIRO);
			}
		});
		
		
	}
	
	private void incluirNovoRegistro() {
		ClienteFrame clienteFrame = new ClienteFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tabelaClientes, tabelaClienteModel, 0);
		clienteFrame.setLocationRelativeTo(null);
		clienteFrame.setVisible(true);
	}
	
	protected void alterarRegistro() {
		
		Integer  linha = 0;
		
		if(tabelaClientes.getSelectedRow() != -1 && tabelaClientes.getSelectedRow() < tabelaClienteModel.getRowCount()) {
			linha = tabelaClientes.getSelectedRow();
			ClienteFrame clienteFrame = new ClienteFrame(new JFrame(), true, ProcessamentoDados.ALTERAR, tabelaClientes, tabelaClienteModel, linha);
			clienteFrame.setLocationRelativeTo(null);
			clienteFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	protected void deletarRegistro() {
		Integer  linha = 0;
		
		if(tabelaClientes.getSelectedRow() != -1 && tabelaClientes.getSelectedRow() < tabelaClienteModel.getRowCount()) {
			linha = tabelaClientes.getSelectedRow();
			ClienteFrame clienteFrame = new ClienteFrame(new JFrame(), true, ProcessamentoDados.EXCLUIR, tabelaClientes, tabelaClienteModel, linha);
			clienteFrame.setLocationRelativeTo(null);
			clienteFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	private void consultarRegistros() {
		Integer  linha = 0;
		
		if(tabelaClientes.getSelectedRow() != -1 && tabelaClientes.getSelectedRow() < tabelaClienteModel.getRowCount()) {
			linha = tabelaClientes.getSelectedRow();
			ClienteFrame clienteFrame = new ClienteFrame(new JFrame(), true, ProcessamentoDados.CONSULTAR, tabelaClientes, tabelaClienteModel, linha);
			clienteFrame.setLocationRelativeTo(null);
			clienteFrame.setVisible(true);
		}
		else{
			ProcessamentoDados.showMensagem("Selecione um registro","Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	protected void filtraNomeCliente() {
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		
		listaCliente = clienteService.mostrarCliente(textFieldBuscarNome.getText());
		tabelaClienteModel.setListaCliente(listaCliente);
		
		sortTabelaCliente = new TableRowSorter<TabelaClienteModel>(tabelaClienteModel);
		tabelaClientes.setRowSorter(sortTabelaCliente);
    }
	
	private void iniciarPaginacao() {
		
		
		
		List<Cliente> listaCliente = new ArrayList<Cliente>();

		totalRegistros = buscarTotalRegistrosCliente();
		
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
		
		
		
		
		listaCliente = carregarListaCliente(paginaAtual, registroPorPagina);
		
		
		tabelaClienteModel.setListaCliente(listaCliente);
		tabelaClientes.setModel(tabelaClienteModel);
		
		
		tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		sortTabelaCliente = new TableRowSorter<TabelaClienteModel>(tabelaClienteModel);
		
		tabelaClientes.setRowSorter(sortTabelaCliente);
		
		tabelaClientes.setAutoCreateRowSorter(true);
		tabelaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabelaClienteModel.fireTableDataChanged();
		
		tabelaClientes.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
		tabelaClientes.getColumnModel().getColumn(NOME).setPreferredWidth(150);
		tabelaClientes.getColumnModel().getColumn(CPF).setPreferredWidth(50);
		tabelaClientes.getColumnModel().getColumn(CELULAR).setPreferredWidth(50);
		tabelaClientes.getColumnModel().getColumn(EMAIL).setPreferredWidth(120);
		tabelaClientes.getColumnModel().getColumn(RUA).setPreferredWidth(90);
		tabelaClientes.getColumnModel().getColumn(BAIRRO).setPreferredWidth(100);
		tabelaClientes.getColumnModel().getColumn(NUMERO).setPreferredWidth(10);
		tabelaClientes.getColumnModel().getColumn(CEP).setPreferredWidth(30);
		tabelaClientes.getColumnModel().getColumn(CIDADE).setPreferredWidth(100);
		
		//scrollPane.setViewportView(tabelaClientes);
		
		
	}





	private List<Cliente> carregarListaCliente(Integer paginaAtual2, Integer registroPorPagina2) {
		
		ClienteService clienteService = new ClienteService();
		return clienteService.carregarListaCliente(((paginaAtual-1)*registroPorPagina), registroPorPagina);
	}





	private Integer buscarTotalRegistrosCliente() {
		return clienteService.countTotalRegistroClientes();
	}





	private void initComponents() {
		
		
		clienteService = new ClienteService();
		tabelaClienteModel = new TabelaClienteModel();
		
		@SuppressWarnings("unused")
		BasicInternalFrameUI bif  = ProcessamentoDados.removeMouseListener(this);
		
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 10, 1242, 434);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(38, 49, 1193, 301);
		panel.add(scrollPane_1);
		
		
		tabelaClientes = new JTable();
		scrollPane_1.setViewportView(tabelaClientes);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(38, 373, 398, 41);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnPrimeiro.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 10, 85, 21);
		panel_1.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnterior.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(105, 10, 85, 21);
		panel_1.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProximo.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(200, 10, 85, 21);
		panel_1.add(btnProximo);
		
		btnUltima = new JButton("");
		btnUltima.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUltima.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-last.png")));
		btnUltima.setBounds(295, 10, 85, 21);
		panel_1.add(btnUltima);
		
		lblPgAtual = new JLabel("P\u00E1gina Atual:");
		lblPgAtual.setBounds(458, 386, 77, 13);
		panel.add(lblPgAtual);
		
		lblShowPagina = new JLabel("10");
		lblShowPagina.setBounds(580, 386, 26, 13);
		panel.add(lblShowPagina);
		
		comboBoxRegistro = new JComboBox<String>();
		comboBoxRegistro.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20", "50"}));
		comboBoxRegistro.setBounds(195, 10, 45, 21);
		panel.add(comboBoxRegistro);
		
		JLabel lblQtdRegistro = new JLabel("Quantidade de Registro:");
		lblQtdRegistro.setBounds(42, 14, 143, 13);
		panel.add(lblQtdRegistro);
		
		lblTotalPagina = new JLabel("Total de p\u00E1gina:");
		lblTotalPagina.setBounds(688, 386, 91, 13);
		panel.add(lblTotalPagina);
		
		lblShowTotal = new JLabel("20");
		lblShowTotal.setBounds(789, 386, 45, 13);
		panel.add(lblShowTotal);
		
		JLabel lblBuscarNome = new JLabel("Buscar por nome:");
		lblBuscarNome.setBounds(610, 14, 104, 13);
		panel.add(lblBuscarNome);
		
		textFieldBuscarNome = new JTextField();
		textFieldBuscarNome.setBounds(716, 11, 515, 19);
		panel.add(textFieldBuscarNome);
		textFieldBuscarNome.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 49, 1193, 301);
		panel.add(scrollPane);
		
		lblTotalRegistro = new JLabel("Total De Registros:");
		lblTotalRegistro.setBounds(926, 386, 126, 13);
		panel.add(lblTotalRegistro);
		
		lblShowRegistros = new JLabel("200");
		lblShowRegistros.setBounds(1055, 386, 45, 13);
		panel.add(lblShowRegistros);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds(145, 455, 1032, 42);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		btnInclusao = new JButton("Inclus\u00E3o");
		btnInclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInclusao.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/book_add.png")));
		btnInclusao.setMnemonic(KeyEvent.VK_I);
		btnInclusao.setBounds(35, 10, 128, 21);
		panel_2.add(btnInclusao);
		
		btnAlteração = new JButton("Altera\u00E7\u00E3o");
		btnAlteração.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlteração.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/book_edit.png")));
		btnAlteração.setMnemonic(KeyEvent.VK_A);
		btnAlteração.setBounds(173, 10, 128, 21);
		panel_2.add(btnAlteração);
		
		btnExclusao = new JButton("Exclus\u00E3o");
		btnExclusao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExclusao.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setBounds(311, 10, 128, 21);
		panel_2.add(btnExclusao);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConsultar.setMnemonic(KeyEvent.VK_C);
		btnConsultar.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/book_open.png")));
		btnConsultar.setBounds(449, 10, 128, 21);
		panel_2.add(btnConsultar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/sair.png")));
		btnFechar.setBounds(894, 10, 128, 21);
		panel_2.add(btnFechar);
		
		btnRelatorio = new JButton("Relatorio");
		btnRelatorio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRelatorio.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/pdf.png")));
		
		btnRelatorio.setBounds(587, 10, 120, 21);
		panel_2.add(btnRelatorio);
		
	}
}
