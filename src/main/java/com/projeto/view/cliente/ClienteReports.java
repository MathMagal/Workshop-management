package com.projeto.view.cliente;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.projeto.model.model.Cliente;
import com.projeto.model.service.ClienteService;
import com.projeto.model.service.JasperReportsService;
import com.projeto.model.service.reports.RelatorioCliente;
import com.projeto.util.PrintJasperReports;
import com.projeto.util.ProcessamentoDados;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

import com.projeto.util.PdfViewer;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

public class ClienteReports extends JFrame {


	private static final long serialVersionUID = 3779067772056556805L;
	private JPanel contentPane;
	private JTextField textFieldClienteIni;
	private JTextField textFieldClienteFim;
	private JButton btnRelatorio;
	private JButton btnFechar;
	private JButton btnClienteInicial;
	private JButton btnClienteFinal;
	
	private JComboBox<String> comboBoxTipoRelatorio;
	
	private DefaultListModel<String> listClienteModel;
	private Integer clienteSelecionado[] = {};
	
	private Cliente cliente;
	
	private Integer CODIGO_INICIAL 	= 0;
	private Integer CODIGO_FINAL 	= 0; 
	private Integer TOTAL_REGISTRO  = 0;
	private Integer totalRegistros 	= 0;	
	private JList<String> listClienteInicial;
	private JPanel panel_1;
	private JList<String> listClienteFinal;
	private JPanel panel_2;
	
	
	public ClienteReports(Integer totalRegistros) {
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
		
		
		ClienteService clienteService = new ClienteService();
		
		List<Cliente> listaCliente = clienteService.carregarListaClientePorParamentro(CODIGO_INICIAL, CODIGO_FINAL);
		
		if(TOTAL_REGISTRO == 0) {
			TOTAL_REGISTRO = listaCliente.size();
		}
		
		/*
		JasperReportsService jrp = new JasperReportsService();
		PrintJasperReports printJasperReports = new PrintJasperReports();
		
		printJasperReports.setFile("ListaCliente");
		printJasperReports.setCollection(listaCliente);
		printJasperReports.addParametros("TOTAL_REGISTROS", TOTAL_REGISTRO);
		
		JasperPrint jasperPrint = jrp.gerarRelatorioPorLista(printJasperReports);
		
		viewerReports(jasperPrint);
		*/
		
		RelatorioCliente relatorioCliente =  new RelatorioCliente();
		
		relatorioCliente.generateReports(listaCliente);
		showReports();
	}
	
	
	private void showReports() {
			try { 
				
				JFrame frame = new JFrame(); 
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				File file = new File("/temp/relatorio_cliente.pdf"); 
				@SuppressWarnings("resource")
				RandomAccessFile raf = new RandomAccessFile(file, "r"); 
				FileChannel channel = raf.getChannel(); 
				ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size()); 
				final PDFFile pdffile = new PDFFile(buf); 
				PdfViewer pdfViewer = new PdfViewer(); 
				pdfViewer.setPDFFile(pdffile); 
				frame.add(pdfViewer); 
				frame.pack(); 
				frame.setVisible(true); 
				PDFPage page = pdffile.getPage(0); 
				pdfViewer.getPagePanel().showPage(page); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			}

			
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
		
		printJasperReports.setFile("Cliente");
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
	
	private void BuscarClienteInicial() {
		BuscarCliente buscarCliente = new BuscarCliente(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarCliente.setLocationRelativeTo(null);
		buscarCliente.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarCliente.isConfirmado()) {
			cliente = new Cliente();
			cliente = buscarCliente.getCliente();
			textFieldClienteIni.setText(cliente.getNome());
			CODIGO_INICIAL = cliente.getId();
		}
	}
	
	private void pesquisarClienteInicial() {
		
		listClienteModel.removeAllElements();
		listClienteInicial.setVisible(ProcessamentoDados.VERDADEIRO);
		textFieldClienteFim.setVisible(ProcessamentoDados.FALSO);
		
		ClienteService clienteService = new ClienteService();
		
		List<Cliente> listaCliente = clienteService.carregarListaCliente(textFieldClienteIni.getText());
		
		clienteSelecionado = new Integer[listaCliente.size()];
		
		for(int i = 0; i < listaCliente.size(); i++) {
			listClienteModel.addElement(listaCliente.get(i).getNome());
			clienteSelecionado[i] = listaCliente.get(i).getId();
		}
		
		listClienteInicial.setModel(listClienteModel);
		
	}
	
	
	
	
	private void BuscarClienteFinal() {
		BuscarCliente buscarCliente = new BuscarCliente(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarCliente.setLocationRelativeTo(null);
		buscarCliente.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarCliente.isConfirmado()) {
			cliente = new Cliente();
			cliente = buscarCliente.getCliente();
			textFieldClienteFim.setText(cliente.getNome());
			CODIGO_FINAL = cliente.getId();
		}
	}
	
	private void pesquisarClienteFinal() {
		
		listClienteModel.removeAllElements();
		listClienteFinal.setVisible(ProcessamentoDados.VERDADEIRO);
		btnRelatorio.setVisible(ProcessamentoDados.FALSO);
		btnFechar.setVisible(ProcessamentoDados.FALSO);
		
		ClienteService clienteService = new ClienteService();
		
		List<Cliente> listaCliente = clienteService.carregarListaCliente(textFieldClienteFim.getText());
		
		clienteSelecionado = new Integer[listaCliente.size()];
		
		for(int i = 0; i < listaCliente.size(); i++) {
			listClienteModel.addElement(listaCliente.get(i).getNome());
			clienteSelecionado[i] = listaCliente.get(i).getId();
		}
		
		listClienteFinal.setModel(listClienteModel);
		
	}
	
	private void escolhaRelatorio() {
		if(comboBoxTipoRelatorio.getSelectedItem() == "Lista Alfabética") {
			viewReportsLista();
		}
		else if(comboBoxTipoRelatorio.getSelectedItem() == "Lista ID") {
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
		btnClienteInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarClienteInicial();
			}
		});
		btnClienteFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarClienteFinal();
			}
		});
		textFieldClienteIni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listClienteInicial.setVisible(ProcessamentoDados.FALSO);
					textFieldClienteFim.setVisible(ProcessamentoDados.VERDADEIRO);
					btnClienteFinal.requestFocus();
				}
				else pesquisarClienteInicial();
				
			}
		});
		textFieldClienteFim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listClienteFinal.setVisible(ProcessamentoDados.FALSO);
					btnRelatorio.setVisible(ProcessamentoDados.VERDADEIRO);
					btnFechar.setVisible(ProcessamentoDados.VERDADEIRO);
					btnRelatorio.requestFocus();
				}
				else pesquisarClienteFinal();
				
			}
		});
		listClienteInicial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listClienteInicial.getSelectedIndex();
				Integer id = clienteSelecionado[item];
				
				Cliente cliente = new Cliente();
				ClienteService clienteService = new ClienteService();
				
				cliente = clienteService.FindByID(id);
				textFieldClienteIni.setText(cliente.getNome());
				CODIGO_INICIAL = cliente.getId();
				listClienteInicial.setVisible(ProcessamentoDados.FALSO);
				textFieldClienteFim.setVisible(ProcessamentoDados.VERDADEIRO);
				btnClienteFinal.requestFocus();
			}
		});
		listClienteFinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listClienteFinal.getSelectedIndex();
				Integer id = clienteSelecionado[item];
				
				Cliente cliente = new Cliente();
				ClienteService clienteService = new ClienteService();
				
				cliente = clienteService.FindByID(id);
				textFieldClienteFim.setText(cliente.getNome());
				CODIGO_FINAL = cliente.getId();
				listClienteFinal.setVisible(ProcessamentoDados.FALSO);
				btnRelatorio.setVisible(ProcessamentoDados.VERDADEIRO);
				btnFechar.setVisible(ProcessamentoDados.VERDADEIRO);
				btnRelatorio.requestFocus();
			}
		});	
	}
	
	
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteReports.class.getResource("/imagens/pdf.png")));
		setTitle("Relatorio Cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listClienteModel = new DefaultListModel<String>();
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 474, 238);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(99, 100, 254, 2);
		panel.add(separator);
		
		JLabel lblCodigoInicial = new JLabel("Cliente Inicial:");
		lblCodigoInicial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCodigoInicial.setBounds(10, 84, 81, 14);
		panel.add(lblCodigoInicial);
		
		textFieldClienteIni = new JTextField();
		
		textFieldClienteIni.setBackground(SystemColor.menu);
		textFieldClienteIni.setBorder(null);
		textFieldClienteIni.setBounds(99, 80, 254, 20);
		panel.add(textFieldClienteIni);
		textFieldClienteIni.setColumns(10);
		
		JLabel lblClienteFinal = new JLabel("Cliente Final:");
		lblClienteFinal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblClienteFinal.setBounds(10, 129, 81, 14);
		panel.add(lblClienteFinal);
		
		textFieldClienteFim = new JTextField();
		textFieldClienteFim.setBackground(SystemColor.menu);
		textFieldClienteFim.setBorder(null);
		textFieldClienteFim.setBounds(99, 126, 254, 20);
		panel.add(textFieldClienteFim);
		textFieldClienteFim.setColumns(10);
		
		btnRelatorio = new JButton("Relatorio");
		btnRelatorio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRelatorio.setIcon(new ImageIcon(ClienteReports.class.getResource("/imagens/pdf.png")));
		btnRelatorio.setBounds(99, 204, 109, 23);
		panel.add(btnRelatorio);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(ClienteReports.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setBounds(244, 204, 109, 23);
		panel.add(btnFechar);
		
		btnClienteInicial = new JButton("Buscar");
		btnClienteInicial.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnClienteInicial.setIcon(new ImageIcon(ClienteReports.class.getResource("/imagens/search.png")));
		btnClienteInicial.setBounds(372, 80, 89, 23);
		panel.add(btnClienteInicial);
		
		btnClienteFinal = new JButton("Buscar");
		btnClienteFinal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnClienteFinal.setIcon(new ImageIcon(ClienteReports.class.getResource("/imagens/search.png")));
		btnClienteFinal.setBounds(372, 125, 89, 23);
		panel.add(btnClienteFinal);
		
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
		
		listClienteInicial = new JList<String>();
		listClienteInicial.setBounds(0, 0, 254, 73);
		panel_1.add(listClienteInicial);
		listClienteInicial.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_2 = new JPanel();
		panel_2.setBounds(99, 145, 254, 82);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		listClienteFinal = new JList<String>();
		listClienteFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		listClienteFinal.setBounds(0, 0, 254, 82);
		panel_2.add(listClienteFinal);
		
		
		listClienteFinal.setVisible(ProcessamentoDados.FALSO);
		listClienteInicial.setVisible(ProcessamentoDados.FALSO);
		
	}
}
