package com.projeto.menu;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.projeto.main.Login;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.cadastro.TabelaCadastro;
import com.projeto.view.cliente.TabelaCliente;
import com.projeto.view.confeccao.TabelaConfeccao;
import com.projeto.view.entrega.TabelaEntrega;
import com.projeto.view.pedido.TabelaPedido;
import com.projeto.view.produto.TabelaProduto;
import com.projeto.view.produtoPedido.TabelaProdutoPedido;

import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Color;


public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = -1148994778944871500L;
	private JPanel contentPane;
	
	private Login login;
	
	public MenuPrincipal(Login login) {
		
		this.login = login;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagens/computer.png")));
		setTitle("Menu Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 832, 490);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuCadastro = new JMenu("Cadastro");
		menuCadastro.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/application_add.png")));
		menuBar.add(menuCadastro);
		
		JMenuItem menuCliente = new JMenuItem("Clientes");
		menuCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TabelaCliente tabelaCliente = TabelaCliente.getInstancia();
				tabelaCliente.setFrameIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/useravatar.png")));
				centralizaFormulario(tabelaCliente);
			}
		});
		menuCliente.setMnemonic(KeyEvent.VK_A);
		menuCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaCliente tabelaCliente = TabelaCliente.getInstancia();
				tabelaCliente.setFrameIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/useravatar.png")));
				centralizaFormulario(tabelaCliente);
			}
		});
		menuCliente.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/useravatar.png")));
		menuCadastro.add(menuCliente);
		
		JMenuItem menuProdutos = new JMenuItem("Produtos");
		menuProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaProduto tabelaProduto = TabelaProduto.getInstancia();
				tabelaProduto.setFrameIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/basket.png")));
				centralizaFormulario(tabelaProduto);
			}
		});
		menuProdutos.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/basket.png")));
		menuCadastro.add(menuProdutos);
		
		JMenuItem menuPedidos = new JMenuItem("Pedidos");
		menuPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaPedido tabelaPedido = TabelaPedido.getInstancia();
				tabelaPedido.setFrameIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/basket_put.png")));
				centralizaFormulario(tabelaPedido);
			}
		});
		menuPedidos.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/basket_put.png")));
		menuCadastro.add(menuPedidos);
		
		JMenuItem menuProdutoPedido = new JMenuItem("Produto - Pedido");
		menuProdutoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaProdutoPedido tabelaProdutoPedido = TabelaProdutoPedido.getInstancia();
				tabelaProdutoPedido.setFrameIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/basket_edit.png")));
				centralizaFormulario(tabelaProdutoPedido);
			}
		});
		menuProdutoPedido.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/basket_edit.png")));
		menuCadastro.add(menuProdutoPedido);
		
		JMenuItem menuConfeccao = new JMenuItem("Confec\u00E7\u00E3o");
		menuConfeccao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaConfeccao tabelaConfeccao = TabelaConfeccao.getInstancia();
				tabelaConfeccao.setFrameIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/sewing.png")));
				centralizaFormulario(tabelaConfeccao);
			}
		});
		menuConfeccao.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/sewing.png")));
		menuCadastro.add(menuConfeccao);
		
		JMenuItem menuEntrega = new JMenuItem("Entrega");
		menuEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaEntrega tabelaEntrega = TabelaEntrega.getInstancia();
				tabelaEntrega.setFrameIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/helmet.png")));
				centralizaFormulario(tabelaEntrega);
			}
		});
		menuEntrega.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/helmet.png")));
		menuCadastro.add(menuEntrega);
		
		JMenu mnRelatorio = new JMenu("Relatorio");
		mnRelatorio.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/pdf.png")));
		menuBar.add(mnRelatorio);
		
		JMenu mnNewMenu = new JMenu("Op\u00E7\u00F5es");
		mnNewMenu.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/settings.png")));
		menuBar.add(mnNewMenu);
		
		JMenuItem menuUsuario = new JMenuItem("Cadastrar Usuario");
		mnNewMenu.add(menuUsuario);
		menuUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaCadastro tabelaCadastro = TabelaCadastro.getInstancia();
				centralizaFormulario(tabelaCadastro);
			}
		});
		menuUsuario.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/customer-service.png")));
		
		JMenuItem menuSair = new JMenuItem("Sair");
		mnNewMenu.add(menuSair);
		menuSair.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/saida.png")));
		menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeMenu();
			}
		});
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		fecharMenuPrincipal();
	}
	
	private void closeMenu() {
		dispose();
		login.setVisible(ProcessamentoDados.VERDADEIRO);
		login.limparCampos();
		login.getTextFieldLogin().requestFocus();
	}
	
	private void centralizaFormulario(JInternalFrame frame) {
		Dimension desktop = this.getSize();
		Dimension janelaInterna = frame.getSize();
		frame.setLocation((desktop.width - janelaInterna.width)/2,
						  (desktop.height - janelaInterna.height)/2);
		
		if(frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
		}else {
			contentPane.add(frame);
			frame.setVisible(ProcessamentoDados.VERDADEIRO);
		}
	}
	
	public void fecharMenuPrincipal() {
		ProcessamentoDados.FehcarJanela(this);
	}
}
