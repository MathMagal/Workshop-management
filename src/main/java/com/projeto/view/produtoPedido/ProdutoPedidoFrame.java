package com.projeto.view.produtoPedido;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

import com.projeto.model.model.ProdutoPedido;
import com.projeto.model.model.ProdutoPedidoPK;
import com.projeto.model.model.Pedido;
import com.projeto.model.model.Produto;
import com.projeto.model.service.ProdutoPedidoService;
//import com.projeto.model.service.ClienteService;
import com.projeto.util.ProcessamentoDados;

import com.projeto.view.produto.BuscarProduto;
import com.projeto.view.pedido.BuscarPedido;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import javax.swing.border.TitledBorder;

public class ProdutoPedidoFrame extends JDialog {

	private static final long serialVersionUID = 888398158197428640L;
	private JPanel contentPane;
	
	ProdutoPedidoService produtoPedidoService;
	ProdutoPedido produtoPedido;
	
	private ProdutoPedidoPK produtoPedidoPK;
	private Produto produto;
	private Pedido pedido;
	
	private JButton btnEncerrar;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JButton btnBuscarProduto;
	private JButton btnBuscarPedido;
	
	private JLabel lblShowErroProduto;
	private JLabel lblShowErroPedido;
	
	
	private JTextField textFieldPedido;
	
	private int acao = 0;
	private int linha = 0;
	
	
	private JTable tabelaProdutoPedidos;
	private TabelaProdutoPedidoModel tabelaProdutoPedidoModel;
	private JTextField textFieldProduto;
	private JTextField textFieldPrecoTotal;
	private JTextField textFieldCliente;
	private JTextField textFieldStatus;
	
	
	
	public ProdutoPedidoFrame(JFrame frame, boolean modal, int acao, JTable tabelaProdutoPedidos, TabelaProdutoPedidoModel tabelaProdutoPedidoModel, int linha) {
		
		super(frame, modal);
		setTitle("Produto - Pedido");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProdutoPedidoFrame.class.getResource("/imagens/basket.png")));
		this.acao = acao;
		this.tabelaProdutoPedidos = tabelaProdutoPedidos;
		this.tabelaProdutoPedidoModel = tabelaProdutoPedidoModel;
		this.linha = linha;
		
		produtoPedidoService = new ProdutoPedidoService();
		produtoPedido = new ProdutoPedido();
		
		intComponents();
		createEvents();
		configuraAcao();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
	}



	private void configuraAcao() {
		if(this.acao == ProcessamentoDados.INCLUIR) {
			btnInserir.setEnabled(true);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.ALTERAR) {
			buscarProdutoPedido();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(true);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.EXCLUIR) {
			buscarProdutoPedido();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(true);
		}
		else if(this.acao == ProcessamentoDados.CONSULTAR) {
			buscarProdutoPedido();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(false);
		}
		
	}



	private void createEvents() {
		
		btnEncerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn =  verificarEscolha();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDados();
					inserirProduto();
					dispose();
				}
				
			}
		});
		
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean toReturn = ProcessamentoDados.FALSO;
				toReturn =  verificarEscolha();
				if (toReturn == ProcessamentoDados.FALSO) {
					pegarDados();
					alterarPedido();
					dispose();
				}
				
			}
		});
		
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirPedido();
				dispose();
			}
		});
		
		btnBuscarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblShowErroPedido.setVisible(false);
				BuscarProduto();
			}

		});
		btnBuscarProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_B) {
					lblShowErroPedido.setVisible(false);
					BuscarProduto();
				}
			}
		});
		btnBuscarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblShowErroPedido.setVisible(false);
				BuscarPedido();
			}

		});
	}
	
	
	private boolean verificarEscolha() {
		
		if(ProcessamentoDados.digitacaoCampo(textFieldProduto.getText())) {
			JOptionPane.showMessageDialog(null, "Selecione um produto", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroProduto.setVisible(true);
			btnBuscarProduto.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldPedido.getText())) {
			JOptionPane.showMessageDialog(null, "Selecione um pedido", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroPedido.setVisible(true);
			btnBuscarPedido.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		return ProcessamentoDados.FALSO;
	}
	
	
	protected void BuscarProduto() {
		BuscarProduto buscarProduto = new BuscarProduto(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarProduto.setLocationRelativeTo(null);
		buscarProduto.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarProduto.isConfirmado()) {
			produto = new Produto();
			produto = buscarProduto.getProduto();
			textFieldProduto.setText(produto.getTipo_produto());
			textFieldCliente.setText(produto.getCliente().getNome());
			textFieldPrecoTotal.setText(produto.getPreco_total());
		}
		
	}
	
	protected void BuscarPedido() {
		BuscarPedido buscarPedido = new BuscarPedido(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarPedido.setLocationRelativeTo(null);
		buscarPedido.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarPedido.isConfirmado()) {
			pedido = new Pedido();
			pedido = buscarPedido.getPedido();
			textFieldPedido.setText(pedido.getNumeroPedido());
			textFieldStatus.setText(pedido.getStatus_pagamento());
		}
		
	}
	
	
	
	public void pegarDados(){
		produtoPedido = new ProdutoPedido();
		
		produtoPedido.setCliente(textFieldCliente.getText());
		produtoPedido.setPrecoTotal(textFieldPrecoTotal.getText());
		produtoPedido.setStatus(textFieldStatus.getText());
	}
	
	public void inserirProduto(){
		produtoPedidoPK = new ProdutoPedidoPK();
		produtoPedidoPK.setID_Produto(produto.getId());
		produtoPedidoPK.setID_Pedido(pedido.getId());
		
		produtoPedido.setProduto(produto);
		produtoPedido.setPedido(pedido);
		
		produtoPedido.setId(produtoPedidoPK);
		
		produtoPedidoService.save(produtoPedido);
		limparDadosDigitacao();
		tabelaProdutoPedidoModel.saveProdutoPedido(produtoPedido);
		tabelaProdutoPedidos.setModel(tabelaProdutoPedidoModel);
		tabelaProdutoPedidoModel.fireTableDataChanged();
	}
	
	public void alterarPedido(){
		produtoPedido.setId(produtoPedidoPK);
		produtoPedidoService.update(produtoPedido);
		limparDadosDigitacao();
	
		tabelaProdutoPedidoModel.updateProdutoPedido(produtoPedido, this.linha);
		tabelaProdutoPedidos.setModel(tabelaProdutoPedidoModel);
		tabelaProdutoPedidoModel.fireTableDataChanged();
		
	}
	
	private void excluirPedido() {
		produtoPedido.setId(produtoPedidoPK);
		produtoPedidoService.delete(produtoPedido);
		limparDadosDigitacao();
		
		tabelaProdutoPedidoModel.removeProdutoPedido(this.linha);
		tabelaProdutoPedidos.setModel(tabelaProdutoPedidoModel);
		tabelaProdutoPedidoModel.fireTableDataChanged();
	}
	
	
	private void buscarProdutoPedido(){
		produtoPedidoPK = new ProdutoPedidoPK();
		produtoPedido = new ProdutoPedido();
		produtoPedido = this.tabelaProdutoPedidoModel.getProdutoPedido(this.linha);
		
		produtoPedidoPK.setID_Produto(produtoPedido.getProduto().getId());
		produtoPedidoPK.setID_Pedido(produtoPedido.getPedido().getId());
		
		textFieldPedido.setText(produtoPedido.getPedido().getNumeroPedido());
		textFieldCliente.setText(produtoPedido.getProduto().getCliente().getNome());
		textFieldProduto.setText(produtoPedido.getProduto().getTipo_produto());	
		textFieldPrecoTotal.setText(produtoPedido.getProduto().getPreco_total());
		textFieldStatus.setText(produtoPedido.getPedido().getStatus_pagamento());
	}
	
	
	
	private void limparDadosDigitacao() {
		
		textFieldPedido.setText("");
		textFieldPrecoTotal.setText("");
		textFieldProduto.setText("");
		textFieldPedido.setText("");
		textFieldStatus.setText("");
	}
	

	private void intComponents() {
		
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 692, 214);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(10, 11, 68, 14);
		panel.add(lblProduto);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 98, 89, 14);
		panel.add(lblCliente);
		
		textFieldPedido = new JTextField();
		textFieldPedido.setEditable(false);
		textFieldPedido.setBounds(109, 53, 277, 20);
		panel.add(textFieldPedido);
		textFieldPedido.setColumns(10);
		
		lblShowErroProduto = new JLabel("");
		lblShowErroProduto.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroProduto.setBounds(392, 7, 24, 23);
		panel.add(lblShowErroProduto);
		
		JLabel lblPedido = new JLabel("Pedido");
		lblPedido.setBounds(10, 53, 57, 14);
		panel.add(lblPedido);
		
		textFieldProduto = new JTextField();
		textFieldProduto.setEditable(false);
		textFieldProduto.setBounds(109, 8, 277, 20);
		panel.add(textFieldProduto);
		textFieldProduto.setColumns(10);
		
		btnBuscarProduto = new JButton("Buscar Produto");
		
		btnBuscarProduto.setMnemonic(KeyEvent.VK_B);
		
		btnBuscarProduto.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/search.png")));
		btnBuscarProduto.setBounds(439, 7, 161, 23);
		panel.add(btnBuscarProduto);
		
		lblShowErroPedido = new JLabel("");
		lblShowErroPedido.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroPedido.setBounds(392, 53, 24, 20);
		panel.add(lblShowErroPedido);
		
		JLabel lblPrecoTotal = new JLabel("Pre\u00E7o Total:");
		lblPrecoTotal.setBounds(10, 141, 68, 14);
		panel.add(lblPrecoTotal);
		
		textFieldPrecoTotal = new JTextField();
		textFieldPrecoTotal.setEditable(false);
		textFieldPrecoTotal.setBounds(109, 138, 277, 20);
		panel.add(textFieldPrecoTotal);
		textFieldPrecoTotal.setColumns(10);
		
		btnBuscarPedido = new JButton("Buscar Pedido");
		btnBuscarPedido.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/search.png")));
		btnBuscarPedido.setMnemonic(KeyEvent.VK_B);
		btnBuscarPedido.setBounds(439, 52, 161, 23);
		panel.add(btnBuscarPedido);
		
		textFieldCliente = new JTextField();
		textFieldCliente.setEditable(false);
		textFieldCliente.setColumns(10);
		textFieldCliente.setBounds(109, 95, 277, 20);
		panel.add(textFieldCliente);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 179, 68, 14);
		panel.add(lblStatus);
		
		textFieldStatus = new JTextField();
		textFieldStatus.setEditable(false);
		textFieldStatus.setColumns(10);
		textFieldStatus.setBounds(109, 176, 277, 20);
		panel.add(textFieldStatus);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 237, 692, 43);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/book_add.png")));
		btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/sair.png")));
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/book_edit.png")));
		
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInserir.setBackground(UIManager.getColor("Button.background"));
		btnInserir.setBounds(10, 11, 102, 21);
		panel_1.add(btnInserir);
		
		
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(122, 11, 102, 21);
		panel_1.add(btnAlterar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setIcon(new ImageIcon(ProdutoPedidoFrame.class.getResource("/imagens/book_delete.png")));
		btnDeletar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDeletar.setBounds(234, 11, 102, 21);
		panel_1.add(btnDeletar);
		
		
		btnEncerrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEncerrar.setBounds(580, 11, 102, 21);
		panel_1.add(btnEncerrar);
		
		lblShowErroProduto.setVisible(false);
		lblShowErroPedido.setVisible(false);
	}
}
