package com.projeto.view.confeccao;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

import com.projeto.model.model.Confeccao;
import com.projeto.model.model.Entrega;
import com.projeto.model.service.ConfeccaoService;
import com.projeto.util.ProcessamentoDados;

import com.projeto.view.entrega.BuscarEntrega;

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

public class ConfeccaoFrame extends JDialog {

	private static final long serialVersionUID = 888398158197428640L;
	private JPanel contentPane;
	
	Confeccao confeccao;
	ConfeccaoService confeccaoService;
	
	private JButton btnEncerrar;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JLabel lblShowErroEstado;
	private JTextField textFieldDataInicio;
	private JTextField textFieldTermino;
	
	private Entrega entrega;
	
	private int acao=0;
	private int linha = 0;
	private int codigo = 0;
	
	private JTable tabelaConfeccaos;
	private TabelaConfeccaoModel tabelaConfeccaoModel;
	private JComboBox<String> comboBoxEstado;
	private JLabel lblShowErroDataInicio;
	private JLabel lblShowErroDataFinal;
	private JLabel lblBuscarEntrega;
	private JTextField textFieldBuscarEntrega;
	private JButton btnBuscarEntrega;
	
	
	
	public ConfeccaoFrame(JFrame frame, boolean modal, int acao, JTable tabelaConfeccaos, TabelaConfeccaoModel tabelaConfeccaoModel, int linha) {
		
		super(frame, modal);
		setTitle("Confec\u00E7\u00E3o");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfeccaoFrame.class.getResource("/imagens/sewing.png")));
		this.acao = acao;
		this.tabelaConfeccaos = tabelaConfeccaos;
		this.tabelaConfeccaoModel = tabelaConfeccaoModel;
		this.linha = linha;
		
		confeccaoService = new ConfeccaoService();
		confeccao = new Confeccao();
		
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
			buscarConfeccao();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(true);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.EXCLUIR) {
			buscarConfeccao();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(true);
		}
		else if(this.acao == ProcessamentoDados.CONSULTAR) {
			buscarConfeccao();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(false);
		}
		
	}



	private void createEvents() {
		
		textFieldDataInicio.addFocusListener(new FocusAdapter() {
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
					inserirConfeccao();
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
					alterarConfeccao();
					dispose();
				}
				
			}
		});
		
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirConfeccao();
				dispose();
			}
		});
		
		textFieldDataInicio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldTermino.requestFocus();
				}
			}
		});
		btnBuscarEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarEntrega();
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
		
		
		if(ProcessamentoDados.digitacaoCampo(textFieldDataInicio.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de CPF", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataInicio.setVisible(true);
			textFieldDataInicio.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldDataInicio.getText().length() > 20 ) {
			JOptionPane.showMessageDialog(null, "erro na digitação da data inicial", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataInicio.setVisible(true);
			textFieldDataInicio.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldTermino.getText().length() > 20) {
			JOptionPane.showMessageDialog(null, "erro na digitação da data final", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroDataFinal.setVisible(true);
			textFieldTermino.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		return ProcessamentoDados.FALSO;
	}
	
	public void incluirDados(){
		confeccao = new Confeccao();
		if (codigo !=0 ) {
			confeccao.setId(codigo);
		}
		confeccao.setEstado_confeccao(comboBoxEstado.getSelectedItem().toString());
		confeccao.setDataInicio(textFieldDataInicio.getText());
		confeccao.setDataFim(textFieldTermino.getText());
		confeccao.setEntrega(entrega);
		System.out.println(confeccao.toString());
	}
	
	
	public void inserirConfeccao(){
		confeccaoService.save(confeccao);
		limparDadosDigitacao();
		
		tabelaConfeccaoModel.saveConfeccao(confeccao);
		tabelaConfeccaos.setModel(tabelaConfeccaoModel);
		tabelaConfeccaoModel.fireTableDataChanged();
	}
	
	public void alterarConfeccao(){
		confeccaoService.update(confeccao);
		limparDadosDigitacao();
		
		tabelaConfeccaoModel.updateConfeccao(confeccao, this.linha);
		tabelaConfeccaos.setModel(tabelaConfeccaoModel);
		tabelaConfeccaoModel.fireTableDataChanged();
		
	}
	
	private void excluirConfeccao() {
		confeccaoService.delete(confeccao);
		limparDadosDigitacao();
		
		tabelaConfeccaoModel.removeConfeccao(this.linha);
		tabelaConfeccaos.setModel(tabelaConfeccaoModel);
		tabelaConfeccaoModel.fireTableDataChanged();
		
	}
	
	
	private void buscarConfeccao(){
		
		confeccao = new Confeccao();
		confeccao = this.tabelaConfeccaoModel.getConfeccao(this.linha);
		entrega = new Entrega();
		entrega = confeccao.getEntrega();
		
		comboBoxEstado.setSelectedItem(confeccao.getEstado_confeccao());
		textFieldDataInicio.setText(confeccao.getDataInicio());
		textFieldTermino.setText(confeccao.getDataFim());
		textFieldBuscarEntrega.setText(entrega.getEstadoEntrega());
	}
	
	protected void BuscarEntrega() {
		BuscarEntrega buscarEntrega = new BuscarEntrega(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarEntrega.setLocationRelativeTo(null);
		buscarEntrega.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarEntrega.isConfirmado()) {
			entrega = new Entrega();
			entrega = buscarEntrega.getEntrega();
			textFieldBuscarEntrega.setText(entrega.getEstadoEntrega());
		}
		
	}
	
	
	
	private void limparDadosDigitacao() {
		comboBoxEstado.setSelectedItem("Selecione um estado");
		textFieldDataInicio.setText("");
		textFieldTermino.setText("");
	}
	

	private void intComponents() {
		
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 595, 161);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(10, 36, 54, 14);
		panel.add(lblEstado);
		
		JLabel lblDataInicio = new JLabel("Data Inicio:");
		lblDataInicio.setBounds(10, 85, 76, 14);
		panel.add(lblDataInicio);
		
		textFieldDataInicio = new JTextField();
		textFieldDataInicio.setBounds(94, 82, 158, 20);
		panel.add(textFieldDataInicio);
		textFieldDataInicio.setColumns(10);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(10, 288, 46, 14);
		panel.add(lblCelular);
		
		JLabel lblDataTermino = new JLabel("Data Termino:");
		lblDataTermino.setBounds(300, 85, 83, 14);
		panel.add(lblDataTermino);
		
		textFieldTermino = new JTextField();
		textFieldTermino.setBounds(389, 82, 158, 20);
		panel.add(textFieldTermino);
		textFieldTermino.setColumns(10);
		
		lblShowErroEstado = new JLabel("");
		lblShowErroEstado.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroEstado.setBounds(270, 32, 24, 18);
		panel.add(lblShowErroEstado);
		
		comboBoxEstado = new JComboBox<String>();
		comboBoxEstado.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecione um estado", "Nao iniciado", "Em Processo", "Pausado", "Cancelado", "Terminado"}));
		comboBoxEstado.setBounds(94, 32, 158, 22);
		panel.add(comboBoxEstado);
		
		lblShowErroDataInicio = new JLabel("");
		lblShowErroDataInicio.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroDataInicio.setBounds(270, 82, 24, 20);
		panel.add(lblShowErroDataInicio);
		
		lblShowErroDataFinal = new JLabel("");
		lblShowErroDataFinal.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroDataFinal.setBounds(561, 82, 24, 20);
		panel.add(lblShowErroDataFinal);
		
		lblBuscarEntrega = new JLabel("Entrega:");
		lblBuscarEntrega.setBounds(10, 124, 64, 14);
		panel.add(lblBuscarEntrega);
		
		textFieldBuscarEntrega = new JTextField();
		textFieldBuscarEntrega.setEditable(false);
		textFieldBuscarEntrega.setBounds(94, 121, 158, 20);
		panel.add(textFieldBuscarEntrega);
		textFieldBuscarEntrega.setColumns(10);
		
		btnBuscarEntrega = new JButton("Buscar Entrega");
		btnBuscarEntrega.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBuscarEntrega.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/search.png")));
		btnBuscarEntrega.setBounds(300, 120, 125, 23);
		panel.add(btnBuscarEntrega);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 183, 595, 40);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/book_add.png")));
		btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/sair.png")));
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/book_edit.png")));
		
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInserir.setBackground(UIManager.getColor("Button.background"));
		btnInserir.setBounds(10, 11, 102, 21);
		panel_1.add(btnInserir);
		
		
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(122, 11, 102, 21);
		panel_1.add(btnAlterar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setIcon(new ImageIcon(ConfeccaoFrame.class.getResource("/imagens/book_delete.png")));
		btnDeletar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDeletar.setBounds(234, 11, 102, 21);
		panel_1.add(btnDeletar);
		
		
		btnEncerrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEncerrar.setBounds(483, 11, 102, 21);
		panel_1.add(btnEncerrar);
		
		lblShowErroEstado.setVisible(false);
		lblShowErroDataInicio.setVisible(false);
		lblShowErroDataFinal.setVisible(false);
	}
}
