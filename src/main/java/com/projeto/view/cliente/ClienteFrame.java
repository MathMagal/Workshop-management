package com.projeto.view.cliente;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

import com.projeto.model.model.Cliente;
import com.projeto.model.service.ClienteService;
import com.projeto.util.ProcessamentoDados;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClienteFrame extends JDialog {

	private static final long serialVersionUID = 888398158197428640L;
	private JPanel contentPane;
	
	Cliente cliente;
	ClienteService clienteService;
	
	private JButton btnEncerrar;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnDeletar;
	
	private JLabel lblShowErroNome;
	private JLabel lblShowErroCPF;
	private JLabel lblShowErroBairro;
	private JLabel lblShowErroNumero;
	private JLabel lblShowErroRua;
	private JLabel lblShowErroCEP;
	private JLabel lblShowErroCidade;
	private JLabel lblShowErroEmail;
	private JLabel lblShowErroCelular;
	
	
	
	private JTextField textFieldNome;
	private JTextField textFieldCPF;
	private JTextField textFieldRua;
	private JTextField textFieldNumero;
	private JTextField textFieldBairro;
	private JTextField textFieldCEP;
	private JTextField textFieldCelular;
	private JTextField textFieldEmail;
	private JTextField textFieldCidade;
	
	private int acao=0;
	private int linha = 0;
	private int codigo = 0;
	
	private JTable tabelaClientes;
	private TabelaClienteModel tabelaClienteModel;
	
	
	
	public ClienteFrame(JFrame frame, boolean modal, int acao, JTable tabelaClientes, TabelaClienteModel tabelaClienteModel, int linha) {
		
		super(frame, modal);
		setTitle("Cliente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteFrame.class.getResource("/imagens/useravatar.png")));
		this.acao = acao;
		this.tabelaClientes = tabelaClientes;
		this.tabelaClienteModel = tabelaClienteModel;
		this.linha = linha;
		
		clienteService = new ClienteService();
		cliente = new Cliente();
		
		intComponents();
		createEvents();
		configuraAcao();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				textFieldNome.requestFocus();
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
			buscarCliente();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(true);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.EXCLUIR) {
			buscarCliente();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(true);
		}
		else if(this.acao == ProcessamentoDados.CONSULTAR) {
			buscarCliente();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(false);
		}
		
	}



	private void createEvents() {
		
		textFieldNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNome.setVisible(false);
			}
		});
		
		textFieldCPF.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCPF.setVisible(false);
			}
		});
		textFieldRua.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroRua.setVisible(false);
			}
		});
		textFieldBairro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroBairro.setVisible(false);
			}
		});
		textFieldCEP.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCEP.setVisible(false);
			}
		});
		textFieldCidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCidade.setVisible(false);
			}
		});
		textFieldNumero.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNumero.setVisible(false);
			}
		});
		textFieldCelular.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCelular.setVisible(false);
			}
		});
		textFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEmail.setVisible(false);
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
					inserirCliente();
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
					alterarCliente();
					dispose();
				}
				
			}
		});
		
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
				dispose();
			}
		});
		
		textFieldNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldCPF.requestFocus();
				}
			}
		});
		
		textFieldCPF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldRua.requestFocus();
				}
			}
		});
		textFieldRua.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldNumero.requestFocus();
				}
			}
		});
		
		textFieldNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldBairro.requestFocus();
				}
			}
		});

		textFieldBairro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldCEP.requestFocus();
				}
			}
		});
		
		textFieldCEP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldCidade.requestFocus();
				}
			}
		});
		
		textFieldCidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldCelular.requestFocus();
				}
			}
		});
		
		textFieldCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					textFieldEmail.requestFocus();
				}
			}
		});
		
		
	}
	
	
	private boolean verificarDigitacao(){
		if(ProcessamentoDados.digitacaoCampo(textFieldNome.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de nome", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(true);
			textFieldNome.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldNome.getText().length() > 80) {
			JOptionPane.showMessageDialog(null, "erro na digitação de nome", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNome.setVisible(true);
			textFieldNome.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		
		if(ProcessamentoDados.digitacaoCampo(textFieldCPF.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de CPF", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCPF.setVisible(true);
			textFieldCPF.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldCPF.getText().length() > 20 ) {
			JOptionPane.showMessageDialog(null, "erro na digitação de CPF", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCPF.setVisible(true);
			textFieldCPF.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldRua.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Rua", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroRua.setVisible(true);
			textFieldRua.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldRua.getText().length() > 80) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Rua", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroRua.setVisible(true);
			textFieldRua.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldNumero.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Numero", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNumero.setVisible(true);
			textFieldNumero.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldNumero.getText().length() > 10) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Numero", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroNumero.setVisible(true);
			textFieldNumero.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldBairro.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Bairro", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroBairro.setVisible(true);
			textFieldBairro.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldBairro.getText().length() > 80) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Bairro", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroBairro.setVisible(true);
			textFieldBairro.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldCidade.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Cidade", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCidade.setVisible(true);
			textFieldCidade.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldCidade.getText().length() > 80) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Cidade", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCidade.setVisible(true);
			textFieldCidade.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldCEP.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de CEP", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCEP.setVisible(true);
			textFieldCEP.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldCEP.getText().length() > 20) {
			JOptionPane.showMessageDialog(null, "erro na digitação de CEP", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCEP.setVisible(true);
			textFieldCEP.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldCelular.getText())) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Celular", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCelular.setVisible(true);
			textFieldCelular.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(textFieldCelular.getText().length() > 30) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Celular", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCelular.setVisible(true);
			textFieldCelular.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		
		if(textFieldEmail.getText().length() > 80) {
			JOptionPane.showMessageDialog(null, "erro na digitação de Email", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroEmail.setVisible(true);
			textFieldEmail.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		return ProcessamentoDados.FALSO;
	}
	
	public void incluirDados(){
		cliente = new Cliente();
		if (codigo !=0 ) {
			cliente.setId(codigo);
		}
		cliente.setNome(textFieldNome.getText());
		cliente.setCpf(textFieldCPF.getText());
		cliente.setRua(textFieldRua.getText());
		cliente.setNumero(textFieldNumero.getText());
		cliente.setCep(textFieldCEP.getText());
		cliente.setCidade(textFieldCidade.getText());
		cliente.setBairro(textFieldBairro.getText());
		cliente.setCelular(textFieldCelular.getText());
		cliente.setEmail(textFieldEmail.getText());
		
		System.out.println(cliente.toString());
	}
	
	
	public void inserirCliente(){
		clienteService.save(cliente);
		limparDadosDigitacao();
		
		tabelaClienteModel.saveCliente(cliente);
		tabelaClientes.setModel(tabelaClienteModel);
		tabelaClienteModel.fireTableDataChanged();
	}
	
	public void alterarCliente(){
		clienteService.update(cliente);
		limparDadosDigitacao();
		
		tabelaClienteModel.updateCliente(cliente, this.linha);
		tabelaClientes.setModel(tabelaClienteModel);
		tabelaClienteModel.fireTableDataChanged();
		
	}
	
	private void excluirCliente() {
		clienteService.delete(cliente);
		limparDadosDigitacao();
		
		tabelaClienteModel.removeCliente(this.linha);
		tabelaClientes.setModel(tabelaClienteModel);
		tabelaClienteModel.fireTableDataChanged();
		
	}
	
	
	private void buscarCliente(){
		
		cliente = new Cliente();
		cliente = this.tabelaClienteModel.getCliente(this.linha);
		textFieldNome.setText(cliente.getNome());
		textFieldCPF.setText(cliente.getCpf());
		textFieldRua.setText(cliente.getRua());
		textFieldNumero.setText(cliente.getNumero());
		textFieldCEP.setText(cliente.getCep());
		textFieldCidade.setText(cliente.getCidade());
		textFieldBairro.setText(cliente.getBairro());
		textFieldCelular.setText(cliente.getCelular());
		textFieldEmail.setText(cliente.getEmail());
		
	}
	
	
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");
		textFieldCPF.setText("");
		textFieldRua.setText("");
		textFieldNumero.setText("");
		textFieldCEP.setText("");
		textFieldCidade.setText("");
		textFieldBairro.setText("");
		textFieldCelular.setText("");
		textFieldEmail.setText("");
	}
	

	private void intComponents() {
		
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 692, 334);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(10, 36, 46, 14);
		panel.add(lblNewLabel);
		
		textFieldNome = new JTextField();
		
		
		textFieldNome.setBounds(64, 34, 277, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblCPF = new JLabel("CPF");
		lblCPF.setBounds(401, 36, 30, 14);
		panel.add(lblCPF);
		
		textFieldCPF = new JTextField();
		textFieldCPF.setBounds(441, 34, 182, 20);
		panel.add(textFieldCPF);
		textFieldCPF.setColumns(10);
		
		JLabel lblRua = new JLabel("Rua");
		lblRua.setBounds(10, 118, 46, 14);
		panel.add(lblRua);
		
		textFieldRua = new JTextField();
		textFieldRua.setBounds(64, 115, 277, 20);
		panel.add(textFieldRua);
		textFieldRua.setColumns(10);
		
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setBounds(401, 118, 53, 14);
		panel.add(lblNumero);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setBounds(464, 116, 159, 20);
		panel.add(textFieldNumero);
		textFieldNumero.setColumns(10);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(10, 157, 46, 14);
		panel.add(lblBairro);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setBounds(64, 157, 277, 20);
		panel.add(textFieldBairro);
		textFieldBairro.setColumns(10);
		
		JLabel lblCEP = new JLabel("CEP");
		lblCEP.setBounds(401, 157, 36, 14);
		panel.add(lblCEP);
		
		textFieldCEP = new JTextField();
		textFieldCEP.setBounds(464, 155, 159, 20);
		panel.add(textFieldCEP);
		textFieldCEP.setColumns(10);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(10, 288, 46, 14);
		panel.add(lblCelular);
		
		textFieldCelular = new JTextField();
		textFieldCelular.setBounds(64, 285, 165, 20);
		panel.add(textFieldCelular);
		textFieldCelular.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(330, 288, 46, 14);
		panel.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(379, 286, 244, 20);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(10, 80, 672, 20);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setBounds(322, 0, 65, 20);
		panel_3.add(lblEndereco);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBounds(10, 246, 672, 20);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblContato = new JLabel("Contato");
		lblContato.setBounds(324, 0, 46, 20);
		panel_4.add(lblContato);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(10, 199, 46, 14);
		panel.add(lblCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setBounds(64, 196, 277, 20);
		panel.add(textFieldCidade);
		textFieldCidade.setColumns(10);
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroNome.setBounds(346, 32, 24, 18);
		panel.add(lblShowErroNome);
		
		lblShowErroCPF = new JLabel("");
		lblShowErroCPF.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroCPF.setBounds(633, 34, 24, 18);
		panel.add(lblShowErroCPF);
		
		lblShowErroRua = new JLabel("");
		lblShowErroRua.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroRua.setBounds(346, 115, 24, 18);
		panel.add(lblShowErroRua);
		
		lblShowErroBairro = new JLabel("");
		lblShowErroBairro.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroBairro.setBounds(346, 158, 24, 18);
		panel.add(lblShowErroBairro);
		
		lblShowErroNumero = new JLabel("");
		lblShowErroNumero.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroNumero.setBounds(633, 115, 24, 18);
		panel.add(lblShowErroNumero);
		
		lblShowErroCidade = new JLabel("");
		lblShowErroCidade.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroCidade.setBounds(346, 195, 24, 18);
		panel.add(lblShowErroCidade);
		
		lblShowErroCEP = new JLabel("");
		lblShowErroCEP.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroCEP.setBounds(633, 155, 24, 18);
		panel.add(lblShowErroCEP);
		
		lblShowErroCelular = new JLabel("");
		lblShowErroCelular.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroCelular.setBounds(233, 285, 24, 18);
		panel.add(lblShowErroCelular);
		
		lblShowErroEmail = new JLabel("");
		lblShowErroEmail.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroEmail.setBounds(630, 285, 24, 18);
		panel.add(lblShowErroEmail);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 356, 692, 40);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/book_add.png")));
		btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/sair.png")));
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/book_edit.png")));
		
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInserir.setBackground(UIManager.getColor("Button.background"));
		btnInserir.setBounds(10, 11, 102, 21);
		panel_1.add(btnInserir);
		
		
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(122, 11, 102, 21);
		panel_1.add(btnAlterar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/book_delete.png")));
		btnDeletar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDeletar.setBounds(234, 11, 102, 21);
		panel_1.add(btnDeletar);
		
		
		btnEncerrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEncerrar.setBounds(580, 11, 102, 21);
		panel_1.add(btnEncerrar);
		
		lblShowErroNome.setVisible(false);
		lblShowErroCPF.setVisible(false);
		lblShowErroRua.setVisible(false);
		lblShowErroBairro.setVisible(false);
		lblShowErroCEP.setVisible(false);
		lblShowErroCidade.setVisible(false);
		lblShowErroNumero.setVisible(false);
		lblShowErroEmail.setVisible(false);
		lblShowErroCelular.setVisible(false);
		
	}
}
