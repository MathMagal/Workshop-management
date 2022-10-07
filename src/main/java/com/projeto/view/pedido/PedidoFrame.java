package com.projeto.view.pedido;


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
import com.projeto.model.model.Pedido;
import com.projeto.model.service.PedidoService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.confeccao.BuscarConfeccao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class PedidoFrame extends JDialog {

	private static final long serialVersionUID = 888398158197428640L;
	private JPanel contentPane;
	
	Pedido pedido;
	PedidoService pedidoService;
	
	private JButton btnEncerrar;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JButton btnNumeroPedido;
	private JButton btnBuscarConfeccao;
	private JLabel lblShowErroPagamento;
	private JLabel lblShowErroStatus;
	
	private Confeccao confeccao;
	
	private JComboBox<String> comboBoxPagamento;
	private JComboBox<String> comboBoxStatus;
	
	
	private int acao = 0;
	private int linha = 0;
	
	private JTable tabelaPedidos;
	private TabelaPedidoModel tabelaPedidoModel;
	private JLabel lblNewLabel;
	private JTextField textFieldPedido;
	private JTextField textFieldBuscarConfeccao;
	
	
	
	
	public PedidoFrame(JFrame frame, boolean modal, int acao, JTable tabelaPedido, TabelaPedidoModel tabelaPedidoModel, int linha) {
		
		super(frame, modal);
		setTitle("Pedido");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PedidoFrame.class.getResource("/imagens/basket_go.png")));
		this.acao = acao;
		this.tabelaPedidos = tabelaPedido;
		this.tabelaPedidoModel = tabelaPedidoModel;
		this.linha = linha;
		
		pedidoService = new PedidoService();
		pedido = new Pedido();
		
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
			buscarPedido();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(true);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.EXCLUIR) {
			buscarPedido();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(true);
		}
		else if(this.acao == ProcessamentoDados.CONSULTAR) {
			buscarPedido();
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
					inserirPedido();
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
		
		btnNumeroPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerarNumero();
			}
		});
		btnBuscarConfeccao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarConfeccao();
			}
		});
	}
	
	
	private boolean verificarEscolha() {
		if(comboBoxPagamento.getSelectedItem() == "Selecione uma forma de pagamento") {
			JOptionPane.showMessageDialog(null, "Selecione uma forma de pagamento", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			comboBoxPagamento.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		if(comboBoxStatus.getSelectedItem() == "Selecione um status") {
			JOptionPane.showMessageDialog(null, "Selecione um status", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroStatus.setVisible(true);
			comboBoxPagamento.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		
		return ProcessamentoDados.FALSO;
	}
	
	
	
	public void pegarDados(){
		
		System.out.println("pegando dados....");
		pedido.setPagamento(comboBoxPagamento.getSelectedItem().toString());
		pedido.setStatus_pagamento(comboBoxStatus.getSelectedItem().toString());
		pedido.setNumeroPedido(textFieldPedido.getText());
		pedido.setConfeccao(confeccao);
		System.out.println(pedido.toString());
	}
	
	public void inserirPedido(){
		pedidoService.save(pedido);
		limparDadosDigitacao();
		
		tabelaPedidoModel.savePedido(pedido);
		tabelaPedidos.setModel(tabelaPedidoModel);
		tabelaPedidoModel.fireTableDataChanged();
	}
	
	public void alterarPedido(){
		pedidoService.update(pedido);
		limparDadosDigitacao();
	
		tabelaPedidoModel.updatePedido(pedido, this.linha);
		tabelaPedidos.setModel(tabelaPedidoModel);
		tabelaPedidoModel.fireTableDataChanged();
		
	}
	
	private void excluirPedido() {
		pedidoService.delete(pedido);
		limparDadosDigitacao();
		
		tabelaPedidoModel.removePedido(this.linha);
		tabelaPedidos.setModel(tabelaPedidoModel);
		tabelaPedidoModel.fireTableDataChanged();
	}
	
	
	private void buscarPedido(){
		pedido = new Pedido();
		pedido = this.tabelaPedidoModel.getPedido(this.linha);
		confeccao = new Confeccao();
		confeccao = pedido.getConfeccao();
		
		comboBoxPagamento.setSelectedItem(pedido.getPagamento());
		comboBoxStatus.setSelectedItem(pedido.getStatus_pagamento());
		textFieldPedido.setText(pedido.getNumeroPedido());
		textFieldBuscarConfeccao.setText(confeccao.getEstado_confeccao());
	}
	
	private void GerarNumero() {
		int protocolo;
		protocolo = (int) Math.floor(10000000 + Math.random() * 9000000);
		textFieldPedido.setText(ProcessamentoDados.converterInteiroParaString(protocolo));
	}
	
	protected void BuscarConfeccao() {
		BuscarConfeccao buscarConfeccao = new BuscarConfeccao(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarConfeccao.setLocationRelativeTo(null);
		buscarConfeccao.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarConfeccao.isConfirmado()) {
			confeccao = new Confeccao();
			confeccao = buscarConfeccao.getConfeccao();
			textFieldBuscarConfeccao.setText(confeccao.getEstado_confeccao());
		}
		
	}
	
	
	
	private void limparDadosDigitacao() {
		
		comboBoxPagamento.setSelectedItem("Selecione uma forma de pagamento");
		comboBoxStatus.setSelectedItem("Selecione um status");
		textFieldPedido.setText("");
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void intComponents() {
		
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 692, 201);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPreco = new JLabel("Pagamento:");
		lblPreco.setBounds(10, 76, 89, 14);
		panel.add(lblPreco);
		
		lblShowErroPagamento = new JLabel("");
		lblShowErroPagamento.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroPagamento.setBounds(404, 72, 24, 23);
		panel.add(lblShowErroPagamento);
		
		comboBoxPagamento = new JComboBox<String>();
		comboBoxPagamento.setModel(new DefaultComboBoxModel(new String[] {"Selecione uma forma de pagamento", "D\u00E9bito", "Cr\u00E9dito", "Dinheiro"}));
		comboBoxPagamento.setBounds(118, 72, 276, 22);
		panel.add(comboBoxPagamento);
		
		JLabel lblNewLabel_1 = new JLabel("Status pagamento:");
		lblNewLabel_1.setBounds(10, 117, 100, 14);
		panel.add(lblNewLabel_1);
		
		comboBoxStatus = new JComboBox<String>();
		comboBoxStatus.setModel(new DefaultComboBoxModel(new String[] {"Selecione um status ", "Pago", "N\u00E3o pago"}));
		comboBoxStatus.setBounds(118, 113, 276, 22);
		panel.add(comboBoxStatus);
		
		lblShowErroStatus = new JLabel("");
		lblShowErroStatus.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroStatus.setBounds(404, 113, 30, 18);
		panel.add(lblShowErroStatus);
		
		lblNewLabel = new JLabel("Numero Pedido:");
		lblNewLabel.setBounds(10, 28, 89, 14);
		panel.add(lblNewLabel);
		
		textFieldPedido = new JTextField();
		textFieldPedido.setEditable(false);
		textFieldPedido.setBounds(116, 25, 276, 20);
		panel.add(textFieldPedido);
		textFieldPedido.setColumns(10);
		
		btnNumeroPedido = new JButton("Gerar Pedido");
		btnNumeroPedido.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNumeroPedido.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/attach.png")));
		btnNumeroPedido.setBounds(421, 24, 143, 23);
		panel.add(btnNumeroPedido);
		
		JLabel lblConfeccao = new JLabel("Confec\u00E7\u00E3o:");
		lblConfeccao.setBounds(10, 159, 89, 14);
		panel.add(lblConfeccao);
		
		textFieldBuscarConfeccao = new JTextField();
		textFieldBuscarConfeccao.setEditable(false);
		textFieldBuscarConfeccao.setColumns(10);
		textFieldBuscarConfeccao.setBounds(118, 156, 276, 20);
		panel.add(textFieldBuscarConfeccao);
		
		btnBuscarConfeccao = new JButton("Buscar Confec\u00E7\u00E3o");
		btnBuscarConfeccao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBuscarConfeccao.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/search.png")));
		btnBuscarConfeccao.setBounds(421, 155, 143, 23);
		panel.add(btnBuscarConfeccao);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 223, 692, 43);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/book_add.png")));
		btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/sair.png")));
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/book_edit.png")));
		
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInserir.setBackground(UIManager.getColor("Button.background"));
		btnInserir.setBounds(10, 11, 102, 21);
		panel_1.add(btnInserir);
		
		
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(122, 11, 102, 21);
		panel_1.add(btnAlterar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/book_delete.png")));
		btnDeletar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDeletar.setBounds(234, 11, 102, 21);
		panel_1.add(btnDeletar);
		
		
		btnEncerrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEncerrar.setBounds(580, 11, 102, 21);
		panel_1.add(btnEncerrar);
		lblShowErroPagamento.setVisible(false);
		lblShowErroStatus.setVisible(false);
	}
}
