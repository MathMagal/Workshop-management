package com.projeto.view.entrega;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.projeto.model.model.Entrega;
import com.projeto.model.service.EntregaService;
import com.projeto.model.service.JasperReportsService;
import com.projeto.util.PrintJasperReports;
import com.projeto.util.ProcessamentoDados;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class EntregaReports extends JFrame {


	private static final long serialVersionUID = 3779067772056556805L;
	private JPanel contentPane;
	private JTextField textFieldEntregaIni;
	private JTextField textFieldEntregaFim;
	private JButton btnRelatorio;
	private JButton btnFechar;
	private JButton btnEntregaInicial;
	private JButton btnEntregaFinal;
	
	private JComboBox<String> comboBoxTipoRelatorio;
	
	private DefaultListModel<String> listEntregaModel;
	private Integer EntregaSelecionado[] = {};
	
	private Entrega entrega;
	
	private Integer CODIGO_INICIAL 	= 0;
	private Integer CODIGO_FINAL 	= 0; 
	private Integer TOTAL_REGISTRO  = 0;
	private Integer totalRegistros 	= 0;	
	private JList<String> listEntregaInicial;
	private JPanel panel_1;
	private JList<String> listEntregaFinal;
	private JPanel panel_2;
	
	
	public EntregaReports(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
		initComponents();
		createEvents();
	}
	
	private void viewReportsLista() {

		Integer numero = 0;
		
		if(CODIGO_INICIAL == 0 || CODIGO_FINAL == 0) {
			CODIGO_INICIAL = 1;
			CODIGO_FINAL = totalRegistros;
			TOTAL_REGISTRO = totalRegistros;
		}
		
		if(CODIGO_INICIAL > CODIGO_FINAL) {
			numero = CODIGO_FINAL;
			CODIGO_FINAL = CODIGO_INICIAL;
			CODIGO_INICIAL = numero;
		}
		
		EntregaService EntregaService = new EntregaService();
		
		List<Entrega> listaEntrega = EntregaService.carregarListaEntregaPorParamentro(CODIGO_INICIAL, CODIGO_FINAL);
		
		if(TOTAL_REGISTRO == 0) {
			TOTAL_REGISTRO = listaEntrega.size();
		}
		
		JasperReportsService jrp = new JasperReportsService();
		PrintJasperReports printJasperReports = new PrintJasperReports();
		
		printJasperReports.setFile("ListaEntrega");
		printJasperReports.setCollection(listaEntrega);
		printJasperReports.addParametros("TOTAL_REGISTROS", TOTAL_REGISTRO);
		
		JasperPrint jasperPrint = jrp.gerarRelatorioPorLista(printJasperReports);
		
		viewerReports(jasperPrint);
	}
	
	
	private void viewReportsSQL() {
		
		Integer numero = 0;
		
		if(CODIGO_INICIAL == 0 || CODIGO_FINAL == 0) {
			CODIGO_INICIAL = 1;
			CODIGO_FINAL = totalRegistros;
		}
		
		if(CODIGO_INICIAL > CODIGO_FINAL) {
			numero = CODIGO_FINAL;
			CODIGO_FINAL = CODIGO_INICIAL;
			CODIGO_INICIAL = numero;
		}
		
		JasperReportsService jrp = new JasperReportsService();
		PrintJasperReports printJasperReports = new PrintJasperReports();
		
		printJasperReports.setFile("Entrega");
		printJasperReports.addParametros("CODIGO_INICIAL", CODIGO_INICIAL);
		printJasperReports.addParametros("CODIGO_FINAL", CODIGO_FINAL);
		
		JasperPrint jasperPrint = jrp.gerarRelatorioPorSQL(printJasperReports);
		
		viewerReports(jasperPrint);
	}
	
	private void viewerReports(JasperPrint jasperPrint) {
		JFrame frameRelatorio = new JFrame();
		JRViewer viewer =  new JRViewer(jasperPrint);
		
		frameRelatorio.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		frameRelatorio.setExtendedState(MAXIMIZED_BOTH);
		frameRelatorio.setTitle("Vizualicao de Relatorio do Sistema");
		frameRelatorio.getContentPane().add(viewer);
		frameRelatorio.setVisible(ProcessamentoDados.VERDADEIRO);
	}
	
	private void BuscarEntregaInicial() {
		BuscarEntrega buscarEntrega = new BuscarEntrega(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarEntrega.setLocationRelativeTo(null);
		buscarEntrega.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarEntrega.isConfirmado()) {
			entrega = new Entrega();
			entrega = buscarEntrega.getEntrega();
			textFieldEntregaIni.setText(entrega.getNome_entregador());
			CODIGO_INICIAL = entrega.getId();
		}
	}
	
	private void pesquisarEntregaInicial() {
		
		listEntregaModel.removeAllElements();
		listEntregaInicial.setVisible(ProcessamentoDados.VERDADEIRO);
		textFieldEntregaFim.setVisible(ProcessamentoDados.FALSO);
		
		EntregaService EntregaService = new EntregaService();
		
		List<Entrega> listaEntrega = EntregaService.carregarListaEntrega(textFieldEntregaIni.getText());
		
		EntregaSelecionado = new Integer[listaEntrega.size()];
		
		for(int i = 0; i < listaEntrega.size(); i++) {
			listEntregaModel.addElement(listaEntrega.get(i).getNome_entregador());
			EntregaSelecionado[i] = listaEntrega.get(i).getId();
		}
		
		listEntregaInicial.setModel(listEntregaModel);
		
	}
	
	
	
	
	private void BuscarEntregaFinal() {
		BuscarEntrega buscarEntrega = new BuscarEntrega(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarEntrega.setLocationRelativeTo(null);
		buscarEntrega.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarEntrega.isConfirmado()) {
			entrega = new Entrega();
			entrega = buscarEntrega.getEntrega();
			textFieldEntregaFim.setText(entrega.getNome_entregador());
			CODIGO_FINAL = entrega.getId();
		}
	}
	
	private void pesquisarEntregaFinal() {
		
		listEntregaModel.removeAllElements();
		listEntregaFinal.setVisible(ProcessamentoDados.VERDADEIRO);
		btnRelatorio.setVisible(ProcessamentoDados.FALSO);
		btnFechar.setVisible(ProcessamentoDados.FALSO);
		
		EntregaService EntregaService = new EntregaService();
		
		List<Entrega> listaEntrega = EntregaService.carregarListaEntrega(textFieldEntregaFim.getText());
		
		EntregaSelecionado = new Integer[listaEntrega.size()];
		
		for(int i = 0; i < listaEntrega.size(); i++) {
			listEntregaModel.addElement(listaEntrega.get(i).getNome_entregador());
			EntregaSelecionado[i] = listaEntrega.get(i).getId();
		}
		
		listEntregaFinal.setModel(listEntregaModel);
		
	}
	
	private void escolhaRelatorio() {
		if(comboBoxTipoRelatorio.getSelectedItem() == "Lista Alfabética") {
			System.out.println("<<<<<<<<<<<<<<< LISTA >>>>>>>>>>>>>>>>>");
			viewReportsLista();
		}
		else if(comboBoxTipoRelatorio.getSelectedItem() == "Lista ID") {
			System.out.println("<<<<<<<<<<<<<<< ID >>>>>>>>>>>>>>>>>");
			viewReportsSQL();
		}
	}
	
	
	private void createEvents() {
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolhaRelatorio();
				dispose();
			}
		});
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnEntregaInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarEntregaInicial();
			}
		});
		btnEntregaFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarEntregaFinal();
			}
		});
		textFieldEntregaIni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listEntregaInicial.setVisible(ProcessamentoDados.FALSO);
					textFieldEntregaFim.setVisible(ProcessamentoDados.VERDADEIRO);
					btnEntregaFinal.requestFocus();
				}
				else pesquisarEntregaInicial();
				
			}
		});
		textFieldEntregaFim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listEntregaFinal.setVisible(ProcessamentoDados.FALSO);
					btnRelatorio.setVisible(ProcessamentoDados.VERDADEIRO);
					btnFechar.setVisible(ProcessamentoDados.VERDADEIRO);
					btnRelatorio.requestFocus();
				}
				else pesquisarEntregaFinal();
				
			}
		});
		listEntregaInicial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listEntregaInicial.getSelectedIndex();
				Integer id = EntregaSelecionado[item];
				
				Entrega entrega = new Entrega();
				EntregaService entregaService = new EntregaService();
				
				entrega = entregaService.FindByID(id);
				textFieldEntregaIni.setText(entrega.getNome_entregador());
				CODIGO_INICIAL = entrega.getId();
				listEntregaInicial.setVisible(ProcessamentoDados.FALSO);
				textFieldEntregaFim.setVisible(ProcessamentoDados.VERDADEIRO);
				btnEntregaFinal.requestFocus();
			}
		});
		listEntregaFinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listEntregaFinal.getSelectedIndex();
				Integer id = EntregaSelecionado[item];
				
				Entrega entrega = new Entrega();
				EntregaService entregaService = new EntregaService();
				
				entrega = entregaService.FindByID(id);
				textFieldEntregaFim.setText(entrega.getNome_entregador());
				CODIGO_FINAL = entrega.getId();
				listEntregaFinal.setVisible(ProcessamentoDados.FALSO);
				btnRelatorio.setVisible(ProcessamentoDados.VERDADEIRO);
				btnFechar.setVisible(ProcessamentoDados.VERDADEIRO);
				btnRelatorio.requestFocus();
			}
		});	
	}
	
	
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EntregaReports.class.getResource("/imagens/pdf.png")));
		setTitle("Relatorio Entrega");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listEntregaModel = new DefaultListModel<String>();
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 474, 238);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(99, 100, 254, 2);
		panel.add(separator);
		
		JLabel lblCodigoInicial = new JLabel("Entrega Inicial:");
		lblCodigoInicial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCodigoInicial.setBounds(10, 84, 81, 14);
		panel.add(lblCodigoInicial);
		
		textFieldEntregaIni = new JTextField();
		
		textFieldEntregaIni.setBackground(SystemColor.menu);
		textFieldEntregaIni.setBorder(null);
		textFieldEntregaIni.setBounds(99, 80, 254, 20);
		panel.add(textFieldEntregaIni);
		textFieldEntregaIni.setColumns(10);
		
		JLabel lblEntregaFinal = new JLabel("Entrega Final:");
		lblEntregaFinal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEntregaFinal.setBounds(10, 129, 81, 14);
		panel.add(lblEntregaFinal);
		
		textFieldEntregaFim = new JTextField();
		textFieldEntregaFim.setBackground(SystemColor.menu);
		textFieldEntregaFim.setBorder(null);
		textFieldEntregaFim.setBounds(99, 126, 254, 20);
		panel.add(textFieldEntregaFim);
		textFieldEntregaFim.setColumns(10);
		
		btnRelatorio = new JButton("Relatorio");
		btnRelatorio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRelatorio.setIcon(new ImageIcon(EntregaReports.class.getResource("/imagens/pdf.png")));
		btnRelatorio.setBounds(99, 204, 109, 23);
		panel.add(btnRelatorio);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(EntregaReports.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setBounds(244, 204, 109, 23);
		panel.add(btnFechar);
		
		btnEntregaInicial = new JButton("Buscar");
		btnEntregaInicial.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEntregaInicial.setIcon(new ImageIcon(EntregaReports.class.getResource("/imagens/search.png")));
		btnEntregaInicial.setBounds(372, 80, 89, 23);
		panel.add(btnEntregaInicial);
		
		btnEntregaFinal = new JButton("Buscar");
		btnEntregaFinal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEntregaFinal.setIcon(new ImageIcon(EntregaReports.class.getResource("/imagens/search.png")));
		btnEntregaFinal.setBounds(372, 125, 89, 23);
		panel.add(btnEntregaFinal);
		
		JLabel lblTipoRelatorio = new JLabel("Tipo Relat\u00F3rio:");
		lblTipoRelatorio.setBounds(10, 34, 81, 14);
		panel.add(lblTipoRelatorio);
		
		comboBoxTipoRelatorio = new JComboBox<String>();
		comboBoxTipoRelatorio.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecione um tipo", "Lista Alfabética", "Lista ID"}));
		comboBoxTipoRelatorio.setBounds(99, 30, 162, 22);
		panel.add(comboBoxTipoRelatorio);
		
		panel_1 = new JPanel();
		panel_1.setBounds(99, 100, 254, 69);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 45, 254, 2);
		panel_1.add(separator_1);
		
		listEntregaInicial = new JList<String>();
		listEntregaInicial.setBounds(0, 0, 254, 73);
		panel_1.add(listEntregaInicial);
		listEntregaInicial.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_2 = new JPanel();
		panel_2.setBounds(99, 145, 254, 82);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		listEntregaFinal = new JList<String>();
		listEntregaFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEntregaFinal.setBounds(0, 0, 254, 82);
		panel_2.add(listEntregaFinal);
		
		
		listEntregaFinal.setVisible(ProcessamentoDados.FALSO);
		listEntregaInicial.setVisible(ProcessamentoDados.FALSO);
		
	}
}
