package com.projeto.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.projeto.menu.MenuPrincipal;
import com.projeto.model.model.Cadastro;
import com.projeto.model.service.CadastroService;
import com.projeto.util.ProcessamentoDados;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;


public class Login extends JFrame {

	private static final long serialVersionUID = 3753814699293514031L;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JPasswordField passwordFieldSenha;
	private JLabel lblShowPassword;
	private JButton btnNewButton;
	
	private CadastroService cadastroService;
	
	private boolean clickPassword = ProcessamentoDados.FALSO;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public Login() {
		
		cadastroService = new CadastroService();
		
		setTitle("Efetuar Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagens/usuario.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 372, 435);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(new Color(255, 250, 250));
		panel.setBounds(0, 0, 356, 396);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(Login.class.getResource("/imagens/towel (1).png")));
		lblNewLabel_6.setBounds(231, 188, 38, 33);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/imagens/logo.png")));
		lblNewLabel.setBounds(46, 11, 269, 127);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_9 = new JLabel("Atelier De Costura");
		lblNewLabel_9.setFont(new Font("Kunstler Script", Font.PLAIN, 36));
		lblNewLabel_9.setBounds(76, 149, 218, 33);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/imagens/medical-mask (3).png")));
		lblNewLabel_1.setBounds(98, 188, 38, 31);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/imagens/handbag.png")));
		lblNewLabel_2.setBounds(146, 186, 38, 32);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(Login.class.getResource("/imagens/make-up-bag.png")));
		lblNewLabel_4.setBounds(194, 187, 38, 32);
		panel.add(lblNewLabel_4);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setBorder(null);
		textFieldLogin.setBackground(new Color(255, 250, 250));
		textFieldLogin.setBounds(92, 249, 187, 20);
		panel.add(textFieldLogin);
		textFieldLogin.setColumns(10);
		
		lblShowPassword = new JLabel("");
		lblShowPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					showPasswordStatus();
				}
			}

			
		});
		lblShowPassword.setIcon(new ImageIcon(Login.class.getResource("/imagens/show.png")));
		lblShowPassword.setBounds(258, 291, 21, 22);
		panel.add(lblShowPassword);
		
		JLabel lblNewLabel_3 = new JLabel("Login:");
		lblNewLabel_3.setForeground(Color.DARK_GRAY);
		lblNewLabel_3.setBounds(92, 234, 46, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("Senha:");
		lblNewLabel_5.setForeground(Color.DARK_GRAY);
		lblNewLabel_5.setBounds(92, 279, 46, 14);
		panel.add(lblNewLabel_5);
		
		btnNewButton = new JButton("Acessar");
		
		btnNewButton.setBackground(new Color(240, 128, 128));
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AcessarSistema();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 11));
		
		btnNewButton.setBounds(92, 334, 187, 23);
		panel.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(192, 192, 192));
		separator.setBounds(92, 269, 187, 5);
		panel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(new Color(192, 192, 192));
		separator_1.setBounds(92, 311, 187, 4);
		panel.add(separator_1);
		
		passwordFieldSenha = new JPasswordField();
		passwordFieldSenha.setBackground(new Color(255, 250, 250));
		passwordFieldSenha.setBorder(null);
		passwordFieldSenha.setBounds(92, 291, 156, 20);
		panel.add(passwordFieldSenha);
		
		fecharTelaLogin();
		FazerLogin(this);
		
	}
	
	private void fecharTelaLogin() {
		ProcessamentoDados.FehcarJanela(this);
	}
	
	private void AcessarSistema() {
		
		List<Cadastro> listaCadastro = new ArrayList<Cadastro>();
		
		String senha = new String(passwordFieldSenha.getPassword());
		
		listaCadastro = cadastroService.loginUsuario(textFieldLogin.getText(), senha);
		
		if(listaCadastro.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Usuario ou senha incorreto", "Login invalido", JOptionPane.ERROR_MESSAGE);
			limparCampos();
			textFieldLogin.requestFocus();
		}
		else {
			MenuPrincipal menuPrincipal = new MenuPrincipal(this);
			menuPrincipal.setLocationRelativeTo(null);
			menuPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
			menuPrincipal.setVisible(true);
			dispose();
		}
	}
	
	private void showPasswordStatus() {
		if(clickPassword == ProcessamentoDados.FALSO) {
			clickPassword = ProcessamentoDados.VERDADEIRO;
			lblShowPassword.setIcon(new ImageIcon(Login.class.getResource("/imagens/hiden.png")));
			passwordFieldSenha.setEchoChar((char) 0);
		}else {
			clickPassword = ProcessamentoDados.FALSO;
			lblShowPassword.setIcon(new ImageIcon(Login.class.getResource("/imagens/show.png")));
			passwordFieldSenha.setEchoChar('\u25cf');
		}
	}
	
	private void FazerLogin(JFrame frame){
		JRootPane rootPane = frame.getRootPane();
		
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "VK_ENTER");
		rootPane.getRootPane().getActionMap().put("VK_ENTER", 
												  new AbstractAction("VK_ENTER") 
			{
			private static final long serialVersionUID = 6972049647801888687L;

			@Override
			public void actionPerformed(ActionEvent e) {
				AcessarSistema();
			}
		});
	}
	
	

	public void limparCampos() {
		textFieldLogin.setText("");
		passwordFieldSenha.setText("");
	}

	public JTextField getTextFieldLogin() {
		return textFieldLogin;
	}

	public void setTextFieldLogin(JTextField textFieldLogin) {
		this.textFieldLogin = textFieldLogin;
	}

	public JPasswordField getPasswordFieldSenha() {
		return passwordFieldSenha;
	}

	public void setPasswordFieldSenha(JPasswordField passwordFieldSenha) {
		this.passwordFieldSenha = passwordFieldSenha;
	}
	
	
	
}
