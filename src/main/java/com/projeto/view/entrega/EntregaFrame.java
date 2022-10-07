package com.projeto.view.entrega;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

import com.projeto.model.model.Entrega;
import com.projeto.model.service.EntregaService;
import com.projeto.util.ProcessamentoDados;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class EntregaFrame extends JDialog {

	private static final long serialVersionUID = 888398158197428640L;
	private JPanel contentPane;
	
	Entrega entrega;
	EntregaService entregaService;
	
	private JButton btnEncerrar;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JLabel lblShowErroEstado;
	private JTextField textFieldDataEntrega;
	private JTextField textFieldEntregador;
	
	private int acao=0;
	private int linha = 0;
	private int codigo = 0;
	
	private JTable tabelaEntregas;
	private TabelaEntregaModel tabelaEntregaModel;
	private JComboBox<String> comboBoxEstado;
	private JLabel lblShowErroDataInicio;
	private JLabel lblShowErroDataFinal;
	
	
	
	public EntregaFrame(JFrame frame, boolean modal, int acao, JTable tabelaEntregas, TabelaEntregaModel tabelaEntregaModel, int linha) {
		
		super(frame, modal);
		setTitle("Entrega");
		setIconImage(Toolkit.getDefaultToolkit().getImage(EntregaFrame.class.getResource("/imagens/helmet.png")));
		this.acao = acao;
		this.tabelaEntregas = tabelaEntregas;
		this.tabelaEntregaModel = tabelaEntregaModel;
		this.linha = linha;
		
		entregaService = new EntregaService();
		entrega = new Entrega();
		
		intComponents();
		createEvents();
		configuraAcao();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				comboBoxEstado.requestFocus();
			}
		});
		
		
		
	}



	private void configuraAcao() {
		if(this.acao == ProcessamentoDados.INCLUIR) {
			btnInserir.setEnabled(true);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.ALTERAR) {
			buscarEntrega();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(true);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.EXCLUIR) {
			buscarEntrega();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(true);
		}
		else if(this.acao == ProcessamentoDados.CONSULTAR) {
			buscarEntrega();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(false);
		}
		
	}



	private void createEvents() {
		
		textFieldDataEntrega.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
			}
		});
		
		btnEncerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					incluirDados();
					inserirEntrega();
					dispose();
				}
				
			}
		});
		
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn = verificarDigitacao();
				if (toReturn == ProcessamentoDados.FALSO) {
					incluirDados();
					alterarEntrega();
					dispose();
				}
				
			}
		});
		
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirEntrega();
				dispose();
			}
		});
		
		textFieldDataEntrega.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldEntregador.requestFocus();
				}
			}
		});
	}
	
	
	private boolean verificarDigitacao(){
		if(comboBoxEstado.getSelectedItem() == "Selecione um estado") {
			JOptionPane.showMessageDialog(null, "Selecione um estado", "erro de seleção", JOptionPane.ERROR_MESSAGE);
			lblShowErroEstado.setVisible(true);
			comboBoxEstado.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		
		if(ProcessamentoDados.digitacaoCampo(textFieldDataEntrega.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação da data", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataInicio.setVisible(true);
			textFieldDataEntrega.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldDataEntrega.getText().length() > 20 ) {
			JOptionPane.showMessageDialog(null, "erro na digitação da data", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataInicio.setVisible(true);
			textFieldDataEntrega.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		if(ProcessamentoDados.digitacaoCampo(textFieldEntregador.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação do entregador", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataInicio.setVisible(true);
			textFieldDataEntrega.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldEntregador.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "erro na digitação do entregado", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataFinal.setVisible(true);
			textFieldEntregador.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		return ProcessamentoDados.FALSO;
	}
	
	public void incluirDados(){
		entrega = new Entrega();
		if (codigo !=0 ) {
			entrega.setId(codigo);
		}
		entrega.setEstadoEntrega(comboBoxEstado.getSelectedItem().toString());
		entrega.setData_entrega(textFieldDataEntrega.getText());
		entrega.setNome_entregador(textFieldEntregador.getText());
		System.out.println(entrega.toString());
	}
	
	
	public void inserirEntrega(){
		entregaService.save(entrega);
		limparDadosDigitacao();
		
		tabelaEntregaModel.saveEntrega(entrega);
		tabelaEntregas.setModel(tabelaEntregaModel);
		tabelaEntregaModel.fireTableDataChanged();
	}
	
	public void alterarEntrega(){
		entregaService.update(entrega);
		limparDadosDigitacao();
		
		tabelaEntregaModel.updateEntrega(entrega, this.linha);
		tabelaEntregas.setModel(tabelaEntregaModel);
		tabelaEntregaModel.fireTableDataChanged();
		
	}
	
	private void excluirEntrega() {
		entregaService.delete(entrega);
		limparDadosDigitacao();
		
		tabelaEntregaModel.removeEntrega(this.linha);
		tabelaEntregas.setModel(tabelaEntregaModel);
		tabelaEntregaModel.fireTableDataChanged();
		
	}
	
	
	private void buscarEntrega(){
		
		entrega = new Entrega();
		entrega = this.tabelaEntregaModel.getEntrega(this.linha);
		comboBoxEstado.setSelectedItem(entrega.getEstadoEntrega());
		textFieldDataEntrega.setText(entrega.getData_entrega());
		textFieldEntregador.setText(entrega.getNome_entregador());
	}
	
	
	
	private void limparDadosDigitacao() {
		comboBoxEstado.setSelectedItem("Selecione um estado");
		textFieldDataEntrega.setText("");
		textFieldEntregador.setText("");
	}
	

	private void intComponents() {
		
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 538, 183);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(10, 36, 54, 14);
		panel.add(lblEstado);
		
		JLabel lblEntregador = new JLabel("Entregador:");
		lblEntregador.setBounds(10, 137, 76, 14);
		panel.add(lblEntregador);
		
		textFieldDataEntrega = new JTextField();
		textFieldDataEntrega.setBounds(94, 82, 158, 20);
		panel.add(textFieldDataEntrega);
		textFieldDataEntrega.setColumns(10);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(10, 288, 46, 14);
		panel.add(lblCelular);
		
		JLabel lblDataTermino = new JLabel("Data Entrega:");
		lblDataTermino.setBounds(10, 85, 83, 14);
		panel.add(lblDataTermino);
		
		textFieldEntregador = new JTextField();
		textFieldEntregador.setBounds(94, 134, 222, 20);
		panel.add(textFieldEntregador);
		textFieldEntregador.setColumns(10);
		
		lblShowErroEstado = new JLabel("");
		lblShowErroEstado.setIcon(new ImageIcon(EntregaFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroEstado.setBounds(270, 32, 24, 18);
		panel.add(lblShowErroEstado);
		
		comboBoxEstado = new JComboBox<String>();
		comboBoxEstado.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecione um estado", "Entregue", "Nao Entregue"}));
		comboBoxEstado.setBounds(94, 32, 158, 22);
		panel.add(comboBoxEstado);
		
		lblShowErroDataInicio = new JLabel("");
		lblShowErroDataInicio.setIcon(new ImageIcon(EntregaFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroDataInicio.setBounds(270, 82, 24, 20);
		panel.add(lblShowErroDataInicio);
		
		lblShowErroDataFinal = new JLabel("");
		lblShowErroDataFinal.setIcon(new ImageIcon(EntregaFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroDataFinal.setBounds(326, 134, 24, 20);
		panel.add(lblShowErroDataFinal);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 205, 538, 40);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(EntregaFrame.class.getResource("/imagens/book_add.png")));
		btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setIcon(new ImageIcon(EntregaFrame.class.getResource("/imagens/sair.png")));
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(EntregaFrame.class.getResource("/imagens/book_edit.png")));
		
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInserir.setBackground(UIManager.getColor("Button.background"));
		btnInserir.setBounds(10, 11, 102, 21);
		panel_1.add(btnInserir);
		
		
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(122, 11, 102, 21);
		panel_1.add(btnAlterar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setIcon(new ImageIcon(EntregaFrame.class.getResource("/imagens/book_delete.png")));
		btnDeletar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDeletar.setBounds(234, 11, 102, 21);
		panel_1.add(btnDeletar);
		
		
		btnEncerrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEncerrar.setBounds(425, 11, 102, 21);
		panel_1.add(btnEncerrar);
		
		lblShowErroEstado.setVisible(false);
		lblShowErroDataInicio.setVisible(false);
		lblShowErroDataFinal.setVisible(false);
	}
}
