package com.projeto.view.confeccao;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.projeto.model.model.Confeccao;
import com.projeto.model.service.ConfeccaoService;
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

public class ConfeccaoReports extends JFrame {


	private static final long serialVersionUID = 3779067772056556805L;
	private JPanel contentPane;
	private JTextField textFieldConfeccaoIni;
	private JTextField textFieldConfeccaoFim;
	private JButton btnRelatorio;
	private JButton btnFechar;
	private JButton btnConfeccaoInicial;
	private JButton btnConfeccaoFinal;
	
	private JComboBox<String> comboBoxTipoRelatorio;
	
	private DefaultListModel<String> listConfeccaoModel;
	private Integer ConfeccaoSelecionado[] = {};
	
	private Confeccao Confeccao;
	
	private Integer CODIGO_INICIAL 	= 0;
	private Integer CODIGO_FINAL 	= 0; 
	private Integer TOTAL_REGISTRO  = 0;
	private Integer totalRegistros 	= 0;	
	private JList<String> listConfeccaoInicial;
	private JPanel panel_1;
	private JList<String> listConfeccaoFinal;
	private JPanel panel_2;
	
	
	public ConfeccaoReports(Integer totalRegistros) {
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
		
		ConfeccaoService ConfeccaoService = new ConfeccaoService();
		
		List<Confeccao> listaConfeccao = ConfeccaoService.carregarListaConfeccaoPorParamentro(CODIGO_INICIAL, CODIGO_FINAL);
		
		if(TOTAL_REGISTRO == 0) {
			TOTAL_REGISTRO = listaConfeccao.size();
		}
		
		JasperReportsService jrp = new JasperReportsService();
		PrintJasperReports printJasperReports = new PrintJasperReports();
		
		printJasperReports.setFile("ListaConfeccao");
		printJasperReports.setCollection(listaConfeccao);
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
		
		printJasperReports.setFile("Confeccao");
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
	
	private void BuscarConfeccaoInicial() {
		BuscarConfeccao buscarConfeccao = new BuscarConfeccao(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarConfeccao.setLocationRelativeTo(null);
		buscarConfeccao.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarConfeccao.isConfirmado()) {
			Confeccao = new Confeccao();
			Confeccao = buscarConfeccao.getConfeccao();
			textFieldConfeccaoIni.setText(Confeccao.getDataInicio());
			CODIGO_INICIAL = Confeccao.getId();
		}
	}
	
	private void pesquisarConfeccaoInicial() {
		
		listConfeccaoModel.removeAllElements();
		listConfeccaoInicial.setVisible(ProcessamentoDados.VERDADEIRO);
		textFieldConfeccaoFim.setVisible(ProcessamentoDados.FALSO);
		
		ConfeccaoService ConfeccaoService = new ConfeccaoService();
		
		List<Confeccao> listaConfeccao = ConfeccaoService.carregarListaConfeccao(textFieldConfeccaoIni.getText());
		
		ConfeccaoSelecionado = new Integer[listaConfeccao.size()];
		
		for(int i = 0; i < listaConfeccao.size(); i++) {
			listConfeccaoModel.addElement(listaConfeccao.get(i).getDataInicio());
			ConfeccaoSelecionado[i] = listaConfeccao.get(i).getId();
		}
		
		listConfeccaoInicial.setModel(listConfeccaoModel);
		
	}
	
	
	
	
	private void BuscarConfeccaoFinal() {
		BuscarConfeccao buscarConfeccao = new BuscarConfeccao(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarConfeccao.setLocationRelativeTo(null);
		buscarConfeccao.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarConfeccao.isConfirmado()) {
			Confeccao = new Confeccao();
			Confeccao = buscarConfeccao.getConfeccao();
			textFieldConfeccaoFim.setText(Confeccao.getDataInicio());
			CODIGO_FINAL = Confeccao.getId();
		}
	}
	
	private void pesquisarConfeccaoFinal() {
		
		listConfeccaoModel.removeAllElements();
		listConfeccaoFinal.setVisible(ProcessamentoDados.VERDADEIRO);
		btnRelatorio.setVisible(ProcessamentoDados.FALSO);
		btnFechar.setVisible(ProcessamentoDados.FALSO);
		
		ConfeccaoService ConfeccaoService = new ConfeccaoService();
		
		List<Confeccao> listaConfeccao = ConfeccaoService.carregarListaConfeccao(textFieldConfeccaoFim.getText());
		
		ConfeccaoSelecionado = new Integer[listaConfeccao.size()];
		
		for(int i = 0; i < listaConfeccao.size(); i++) {
			listConfeccaoModel.addElement(listaConfeccao.get(i).getDataInicio());
			ConfeccaoSelecionado[i] = listaConfeccao.get(i).getId();
		}
		
		listConfeccaoFinal.setModel(listConfeccaoModel);
		
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
		btnConfeccaoInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarConfeccaoInicial();
			}
		});
		btnConfeccaoFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarConfeccaoFinal();
			}
		});
		textFieldConfeccaoIni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listConfeccaoInicial.setVisible(ProcessamentoDados.FALSO);
					textFieldConfeccaoFim.setVisible(ProcessamentoDados.VERDADEIRO);
					btnConfeccaoFinal.requestFocus();
				}
				else pesquisarConfeccaoInicial();
				
			}
		});
		textFieldConfeccaoFim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listConfeccaoFinal.setVisible(ProcessamentoDados.FALSO);
					btnRelatorio.setVisible(ProcessamentoDados.VERDADEIRO);
					btnFechar.setVisible(ProcessamentoDados.VERDADEIRO);
					btnRelatorio.requestFocus();
				}
				else pesquisarConfeccaoFinal();
				
			}
		});
		listConfeccaoInicial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listConfeccaoInicial.getSelectedIndex();
				Integer id = ConfeccaoSelecionado[item];
				
				Confeccao Confeccao = new Confeccao();
				ConfeccaoService ConfeccaoService = new ConfeccaoService();
				
				Confeccao = ConfeccaoService.FindByID(id);
				textFieldConfeccaoIni.setText(Confeccao.getDataInicio());
				CODIGO_INICIAL = Confeccao.getId();
				listConfeccaoInicial.setVisible(ProcessamentoDados.FALSO);
				textFieldConfeccaoFim.setVisible(ProcessamentoDados.VERDADEIRO);
				btnConfeccaoFinal.requestFocus();
			}
		});
		listConfeccaoFinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listConfeccaoFinal.getSelectedIndex();
				Integer id = ConfeccaoSelecionado[item];
				
				Confeccao Confeccao = new Confeccao();
				ConfeccaoService ConfeccaoService = new ConfeccaoService();
				
				Confeccao = ConfeccaoService.FindByID(id);
				textFieldConfeccaoFim.setText(Confeccao.getDataInicio());
				CODIGO_FINAL = Confeccao.getId();
				listConfeccaoFinal.setVisible(ProcessamentoDados.FALSO);
				btnRelatorio.setVisible(ProcessamentoDados.VERDADEIRO);
				btnFechar.setVisible(ProcessamentoDados.VERDADEIRO);
				btnRelatorio.requestFocus();
			}
		});	
	}
	
	
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfeccaoReports.class.getResource("/imagens/pdf.png")));
		setTitle("Relatorio Confeccao");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listConfeccaoModel = new DefaultListModel<String>();
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 474, 238);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(99, 100, 254, 2);
		panel.add(separator);
		
		JLabel lblCodigoInicial = new JLabel("Confeccao Inicial:");
		lblCodigoInicial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCodigoInicial.setBounds(10, 84, 81, 14);
		panel.add(lblCodigoInicial);
		
		textFieldConfeccaoIni = new JTextField();
		
		textFieldConfeccaoIni.setBackground(SystemColor.menu);
		textFieldConfeccaoIni.setBorder(null);
		textFieldConfeccaoIni.setBounds(99, 80, 254, 20);
		panel.add(textFieldConfeccaoIni);
		textFieldConfeccaoIni.setColumns(10);
		
		JLabel lblConfeccaoFinal = new JLabel("Confeccao Final:");
		lblConfeccaoFinal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfeccaoFinal.setBounds(10, 129, 81, 14);
		panel.add(lblConfeccaoFinal);
		
		textFieldConfeccaoFim = new JTextField();
		textFieldConfeccaoFim.setBackground(SystemColor.menu);
		textFieldConfeccaoFim.setBorder(null);
		textFieldConfeccaoFim.setBounds(99, 126, 254, 20);
		panel.add(textFieldConfeccaoFim);
		textFieldConfeccaoFim.setColumns(10);
		
		btnRelatorio = new JButton("Relatorio");
		btnRelatorio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRelatorio.setIcon(new ImageIcon(ConfeccaoReports.class.getResource("/imagens/pdf.png")));
		btnRelatorio.setBounds(99, 204, 109, 23);
		panel.add(btnRelatorio);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(ConfeccaoReports.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setBounds(244, 204, 109, 23);
		panel.add(btnFechar);
		
		btnConfeccaoInicial = new JButton("Buscar");
		btnConfeccaoInicial.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConfeccaoInicial.setIcon(new ImageIcon(ConfeccaoReports.class.getResource("/imagens/search.png")));
		btnConfeccaoInicial.setBounds(372, 80, 89, 23);
		panel.add(btnConfeccaoInicial);
		
		btnConfeccaoFinal = new JButton("Buscar");
		btnConfeccaoFinal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnConfeccaoFinal.setIcon(new ImageIcon(ConfeccaoReports.class.getResource("/imagens/search.png")));
		btnConfeccaoFinal.setBounds(372, 125, 89, 23);
		panel.add(btnConfeccaoFinal);
		
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
		
		listConfeccaoInicial = new JList<String>();
		listConfeccaoInicial.setBounds(0, 0, 254, 73);
		panel_1.add(listConfeccaoInicial);
		listConfeccaoInicial.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_2 = new JPanel();
		panel_2.setBounds(99, 145, 254, 82);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		listConfeccaoFinal = new JList<String>();
		listConfeccaoFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		listConfeccaoFinal.setBounds(0, 0, 254, 82);
		panel_2.add(listConfeccaoFinal);
		
		
		listConfeccaoFinal.setVisible(ProcessamentoDados.FALSO);
		listConfeccaoInicial.setVisible(ProcessamentoDados.FALSO);
		
	}
}
