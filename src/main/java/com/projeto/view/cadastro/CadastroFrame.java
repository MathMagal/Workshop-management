package com.projeto.view.cadastro;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

import com.projeto.model.model.Cadastro;
import com.projeto.model.service.CadastroService;
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

public class CadastroFrame extends JDialog {

	private static final long serialVersionUID = 888398158197428640L;
	private JPanel contentPane;
	
	Cadastro cadastro;
	CadastroService cadastroService;
	
	private JButton btnEncerrar;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JTextField textFieldUsuario;
	private JTextField textFieldSenha;
	
	private int acao=0;
	private int linha = 0;
	private int codigo = 0;
	
	private JTable tabelaCadastros;
	private TabelaCadastroModel tabelaCadastroModel;
	private JLabel lblShowErroUsuario;
	private JLabel lblShowErroSenha;
	
	
	
	public CadastroFrame(JFrame frame, boolean modal, int acao, JTable tabelaCadastros, TabelaCadastroModel tabelaCadastroModel, int linha) {
		
		super(frame, modal);
		setTitle("Cadastro");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CadastroFrame.class.getResource("/imagens/customer-service.png")));
		this.acao = acao;
		this.tabelaCadastros = tabelaCadastros;
		this.tabelaCadastroModel = tabelaCadastroModel;
		this.linha = linha;
		
		cadastroService = new CadastroService();
		cadastro = new Cadastro();
		
		intComponents();
		createEvents();
		configuraAcao();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				textFieldUsuario.requestFocus();
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
			buscarCadastro();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(true);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.EXCLUIR) {
			buscarCadastro();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(true);
		}
		else if(this.acao == ProcessamentoDados.CONSULTAR) {
			buscarCadastro();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(false);
		}
		
	}



	private void createEvents() {
		
		textFieldUsuario.addFocusListener(new FocusAdapter() {
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
					inserirCadastro();
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
					alterarCadastro();
					dispose();
				}
				
			}
		});
		
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCadastro();
				dispose();
			}
		});
		
		textFieldUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldSenha.requestFocus();
				}
			}
		});
	}
	
	
	private boolean verificarDigitacao(){
		
		if(ProcessamentoDados.digitacaoCampo(textFieldUsuario.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação da data", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroUsuario.setVisible(true);
			textFieldUsuario.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldUsuario.getText().length() > 20 ) {
			JOptionPane.showMessageDialog(null, "erro na digitação da data", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroUsuario.setVisible(true);
			textFieldUsuario.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		if(ProcessamentoDados.digitacaoCampo(textFieldSenha.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação do Cadastrodor", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroUsuario.setVisible(true);
			textFieldUsuario.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldSenha.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "erro na digitação do Cadastrodo", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroSenha.setVisible(true);
			textFieldSenha.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		return ProcessamentoDados.FALSO;
	}
	
	public void incluirDados(){
		cadastro = new Cadastro();
		if (codigo !=0 ) {
			cadastro.setId(codigo);
		}
		cadastro.setUsuario(textFieldUsuario.getText());
		cadastro.setSenha(textFieldSenha.getText());
		System.out.println(cadastro.toString());
	}
	
	
	public void inserirCadastro(){
		cadastroService.save(cadastro);
		limparDadosDigitacao();
		
		tabelaCadastroModel.saveCadastro(cadastro);
		tabelaCadastros.setModel(tabelaCadastroModel);
		tabelaCadastroModel.fireTableDataChanged();
	}
	
	public void alterarCadastro(){
		cadastroService.update(cadastro);
		limparDadosDigitacao();
		
		tabelaCadastroModel.updateCadastro(cadastro, this.linha);
		tabelaCadastros.setModel(tabelaCadastroModel);
		tabelaCadastroModel.fireTableDataChanged();
		
	}
	
	private void excluirCadastro() {
		cadastroService.delete(cadastro);
		limparDadosDigitacao();
		
		tabelaCadastroModel.removeCadastro(this.linha);
		tabelaCadastros.setModel(tabelaCadastroModel);
		tabelaCadastroModel.fireTableDataChanged();
		
	}
	
	
	private void buscarCadastro(){
		
		cadastro = new Cadastro();
		cadastro = this.tabelaCadastroModel.getCadastro(this.linha);
		textFieldUsuario.setText(cadastro.getUsuario());
		textFieldSenha.setText(cadastro.getSenha());
	}
	
	
	private void limparDadosDigitacao() {
		textFieldUsuario.setText("");
		textFieldSenha.setText("");
	}
	

	private void intComponents() {
		
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 233);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 538, 126);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 73, 76, 14);
		panel.add(lblSenha);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(92, 24, 222, 20);
		panel.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(10, 288, 46, 14);
		panel.add(lblCelular);
		
		JLabel lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setBounds(10, 27, 83, 14);
		panel.add(lblUsuario);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setBounds(92, 70, 222, 20);
		panel.add(textFieldSenha);
		textFieldSenha.setColumns(10);
		
		lblShowErroUsuario = new JLabel("");
		lblShowErroUsuario.setIcon(new ImageIcon(CadastroFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroUsuario.setBounds(324, 24, 24, 20);
		panel.add(lblShowErroUsuario);
		
		lblShowErroSenha = new JLabel("");
		lblShowErroSenha.setIcon(new ImageIcon(CadastroFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroSenha.setBounds(324, 70, 24, 20);
		panel.add(lblShowErroSenha);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 148, 538, 40);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(CadastroFrame.class.getResource("/imagens/book_add.png")));
		btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setIcon(new ImageIcon(CadastroFrame.class.getResource("/imagens/sair.png")));
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(CadastroFrame.class.getResource("/imagens/book_edit.png")));
		
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInserir.setBackground(UIManager.getColor("Button.background"));
		btnInserir.setBounds(10, 11, 102, 21);
		panel_1.add(btnInserir);
		
		
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(122, 11, 102, 21);
		panel_1.add(btnAlterar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setIcon(new ImageIcon(CadastroFrame.class.getResource("/imagens/book_delete.png")));
		btnDeletar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDeletar.setBounds(234, 11, 102, 21);
		panel_1.add(btnDeletar);
		
		
		btnEncerrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEncerrar.setBounds(425, 11, 102, 21);
		panel_1.add(btnEncerrar);
		lblShowErroUsuario.setVisible(false);
		lblShowErroSenha.setVisible(false);
	}
}
