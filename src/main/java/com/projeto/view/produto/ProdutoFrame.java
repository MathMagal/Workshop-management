package com.projeto.view.produto;


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
import com.projeto.model.model.Produto;
import com.projeto.model.service.ClienteService;
//import com.projeto.model.service.ClienteService;
import com.projeto.model.service.ProdutoService;
import com.projeto.util.ProcessamentoDados;

import com.projeto.view.cliente.BuscarCliente;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProdutoFrame extends JDialog {

	private static final long serialVersionUID = 888398158197428640L;
	private JPanel contentPane;
	
	Produto produto;
	ProdutoService produtoService;
	
	private JButton btnEncerrar;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnDeletar;
	private JButton btnBuscarCliente;
	
	private JLabel lblShowErroProduto;
	private JLabel lblShowErroQuantidade;
	private JLabel lblShowErroCliente;
	
	private JList<String> listCliente;
	private DefaultListModel<String> listClienteModel;
	private Integer clienteSelecionado[] = {};
	
	private Cliente cliente;
	private JTextField textFieldPreco;
	private JComboBox<String> comboBoxProduto;
	private JComboBox<String> comboBoxQuantidade;
	
	private int acao = 0;
	private int linha = 0;
	private double preco = 0;
	private double precoTotal = 0;
	
	
	private JTable tabelaProdutos;
	private TabelaProdutoModel tabelaProdutoModel;
	private JTextField textFieldBuscarCliente;
	private JTextField textFieldPreçoTotal;
	
	
	
	public ProdutoFrame(JFrame frame, boolean modal, int acao, JTable tabelaProdutos, TabelaProdutoModel tabelaProdutoModel, int linha) {
		
		super(frame, modal);
		setTitle("Produto");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProdutoFrame.class.getResource("/imagens/basket.png")));
		this.acao = acao;
		this.tabelaProdutos = tabelaProdutos;
		this.tabelaProdutoModel = tabelaProdutoModel;
		this.linha = linha;
		
		produtoService = new ProdutoService();
		produto = new Produto();
		
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
			buscarProduto();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(true);
			btnDeletar.setEnabled(false);
		}
		else if(this.acao == ProcessamentoDados.EXCLUIR) {
			buscarProduto();
			btnInserir.setEnabled(false);
			btnAlterar.setEnabled(false);
			btnDeletar.setEnabled(true);
		}
		else if(this.acao == ProcessamentoDados.CONSULTAR) {
			buscarProduto();
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
		
		btnBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblShowErroCliente.setVisible(false);
				BuscarCliente();
			}

		});
		btnBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_B) {
					lblShowErroCliente.setVisible(false);
					BuscarCliente();
				}
			}
		});
		comboBoxProduto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				listCliente.setVisible(ProcessamentoDados.FALSO);
			}
		});
		
		comboBoxProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblShowErroProduto.setVisible(false);
				
				escolhaProduto();
				escolhaQuantidade();
			}

		});
		
		
		comboBoxQuantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblShowErroQuantidade.setVisible(false);
				escolhaProduto();
				escolhaQuantidade();
			}
		});
		
		textFieldBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listCliente.setVisible(ProcessamentoDados.FALSO);
					comboBoxProduto.requestFocus();
				}else pesquisarCliente();
			}

			
		});
		
		listCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listCliente.getSelectedIndex();
				Integer id = clienteSelecionado[item];
				
				cliente = new Cliente();
				ClienteService clienteService = new ClienteService();
				
				cliente = clienteService.FindByID(id);
				textFieldBuscarCliente.setText(cliente.getNome());
				
				listCliente.setVisible(false);
				comboBoxProduto.requestFocus();
			}
		});
	}
	
	
	private void escolhaProduto() {
		
		if(comboBoxProduto.getSelectedItem() == "Selecione um item") {
			preco = 0;
			textFieldPreco.setText("R$ "+ProcessamentoDados.formatarDouble(preco));
		}
		
		if(comboBoxProduto.getSelectedItem() == "Máscara") {
			preco = 6.25;
			textFieldPreco.setText("R$ "+ProcessamentoDados.formatarDouble(preco));
		}
		else if(comboBoxProduto.getSelectedItem() == "Bolsa"){
			preco = 40;
			textFieldPreco.setText("R$ "+ProcessamentoDados.formatarDouble(preco));
		}
		
		else if(comboBoxProduto.getSelectedItem() == "Necessaire"){
			preco = 20;
			textFieldPreco.setText("R$ "+ProcessamentoDados.formatarDouble(preco));
		}
		else if(comboBoxProduto.getSelectedItem() == "Toalha") {
			preco = 10;
			textFieldPreco.setText("R$ "+ProcessamentoDados.formatarDouble(preco));	
		}
	}
	
	
	private void escolhaQuantidade() {
		precoTotal = preco*Float.valueOf(comboBoxQuantidade.getSelectedItem().toString());
		textFieldPreçoTotal.setText("R$ "+ProcessamentoDados.formatarDouble(precoTotal));
	}
	
	
	
	private boolean verificarEscolha() {
		if(comboBoxProduto.getSelectedItem() == "Selecione um item") {
			JOptionPane.showMessageDialog(null, "Selecione um item", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroProduto.setVisible(true);
			comboBoxProduto.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		if(Integer.valueOf(comboBoxQuantidade.getSelectedItem().toString()) == 0) {
			JOptionPane.showMessageDialog(null, "Selecione uma quantidade", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroQuantidade.setVisible(true);
			comboBoxProduto.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		if(ProcessamentoDados.digitacaoCampo(textFieldBuscarCliente.getText())) {
			JOptionPane.showMessageDialog(null, "Selecione um cliente", "erro de digitação", JOptionPane.ERROR_MESSAGE);
			lblShowErroCliente.setVisible(true);
			btnBuscarCliente.requestFocus();
			return ProcessamentoDados.VERDADEIRO;
		}
		
		
		return ProcessamentoDados.FALSO;
	}
	
	private void pesquisarCliente() {
		
		listClienteModel.removeAllElements();
		listCliente.setVisible(ProcessamentoDados.VERDADEIRO);
		
		ClienteService clienteService = new ClienteService();
		
		List<Cliente> listaCliente = clienteService.carregarListaCliente(textFieldBuscarCliente.getText());
		
		clienteSelecionado = new Integer[listaCliente.size()];
		
		for(int i = 0; i < listaCliente.size(); i++) {
			listClienteModel.addElement(listaCliente.get(i).getNome());
			clienteSelecionado[i] = listaCliente.get(i).getId();
		}
		
		listCliente.setModel(listClienteModel);
		
	}
	
	protected void BuscarCliente() {
		BuscarCliente buscarCliente = new BuscarCliente(new JFrame(), ProcessamentoDados.VERDADEIRO);
		buscarCliente.setLocationRelativeTo(null);
		buscarCliente.setVisible(ProcessamentoDados.VERDADEIRO);
		
		if(buscarCliente.isConfirmado()) {
			cliente = new Cliente();
			cliente = buscarCliente.getCliente();
			textFieldBuscarCliente.setText(cliente.getNome());
		}
		
	}
	
	
	public void pegarDados(){
		System.out.println("pegando dados....");
		produto.setTipo_produto(comboBoxProduto.getSelectedItem().toString());
		produto.setQuantidade(Integer.valueOf(comboBoxQuantidade.getSelectedItem().toString()));
		produto.setPreço_produto(textFieldPreco.getText());
		produto.setPreco_total(textFieldPreçoTotal.getText());
		produto.setCliente(cliente);
		//System.out.println(cliente.toString());
		System.out.println(produto.toString());
	}
	
	public void inserirProduto(){
		produtoService.save(produto);
		limparDadosDigitacao();
		
		tabelaProdutoModel.saveProduto(produto);
		tabelaProdutos.setModel(tabelaProdutoModel);
		tabelaProdutoModel.fireTableDataChanged();
	}
	
	public void alterarPedido(){
		produtoService.update(produto);
		limparDadosDigitacao();
	
		tabelaProdutoModel.updateProduto(produto, this.linha);
		tabelaProdutos.setModel(tabelaProdutoModel);
		tabelaProdutoModel.fireTableDataChanged();
		
	}
	
	private void excluirPedido() {
		produtoService.delete(produto);
		limparDadosDigitacao();
		
		tabelaProdutoModel.removeProduto(this.linha);
		tabelaProdutos.setModel(tabelaProdutoModel);
		tabelaProdutoModel.fireTableDataChanged();
	}
	
	
	private void buscarProduto(){
		
		produto = new Produto();
		produto = this.tabelaProdutoModel.getProduto(this.linha);
		cliente = new Cliente();
		cliente = produto.getCliente();
		
		comboBoxProduto.setSelectedItem(produto.getTipo_produto());
		comboBoxQuantidade.setSelectedItem(String.valueOf(produto.getQuantidade()));
		textFieldPreco.setText(produto.getPreço_produto());	
		textFieldBuscarCliente.setText(cliente.getNome());		
	}
	
	
	
	private void limparDadosDigitacao() {
		comboBoxProduto.setSelectedItem("Selecione um item");
		comboBoxQuantidade.setSelectedItem("0");
		textFieldPreco.setText("");
		textFieldPreçoTotal.setText("");
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void intComponents() {
		
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 692, 183);
		contentPane.add(panel);
		panel.setLayout(null);
		
		listClienteModel = new DefaultListModel<String>();
		
		
		listCliente = new JList();
		listCliente.setBorder(new LineBorder(new Color(0, 0, 0)));
		listCliente.setBounds(109, 24, 277, 103);
		panel.add(listCliente);
		
		listCliente.setVisible(ProcessamentoDados.FALSO);
		
		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(10, 57, 68, 14);
		panel.add(lblProduto);
		
		JLabel lblPreco = new JLabel("Pre\u00E7o Unitario");
		lblPreco.setBounds(10, 98, 89, 14);
		panel.add(lblPreco);
		
		textFieldPreco = new JTextField();
		textFieldPreco.setEditable(false);
		textFieldPreco.setBounds(109, 95, 277, 20);
		panel.add(textFieldPreco);
		textFieldPreco.setColumns(10);
		
		lblShowErroProduto = new JLabel("");
		lblShowErroProduto.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroProduto.setBounds(392, 53, 24, 23);
		panel.add(lblShowErroProduto);
		
		JLabel lblBuscarCliente = new JLabel("Cliente");
		lblBuscarCliente.setBounds(10, 11, 57, 14);
		panel.add(lblBuscarCliente);
		
		textFieldBuscarCliente = new JTextField();
		textFieldBuscarCliente.setBounds(109, 8, 277, 20);
		panel.add(textFieldBuscarCliente);
		textFieldBuscarCliente.setColumns(10);
		
		btnBuscarCliente = new JButton("Buscar");
		
		btnBuscarCliente.setMnemonic(KeyEvent.VK_B);
		
		btnBuscarCliente.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/search.png")));
		btnBuscarCliente.setBounds(439, 7, 107, 23);
		panel.add(btnBuscarCliente);
		
		comboBoxProduto = new JComboBox<String>();
		
		comboBoxProduto.setModel(new DefaultComboBoxModel(new String[] {"Selecione um item", "M\u00E1scara", "Bolsa", "Necessaire", "Toalha"}));
		comboBoxProduto.setBounds(110, 53, 276, 22);
		panel.add(comboBoxProduto);
		
		JLabel lblNewLabel = new JLabel("Quantidade");
		lblNewLabel.setBounds(442, 57, 71, 14);
		panel.add(lblNewLabel);
		
		comboBoxQuantidade = new JComboBox<String>();
		
		comboBoxQuantidade.setModel(new DefaultComboBoxModel<String>(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboBoxQuantidade.setBounds(523, 53, 57, 22);
		panel.add(comboBoxQuantidade);
		
		lblShowErroQuantidade = new JLabel("");
		lblShowErroQuantidade.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroQuantidade.setBounds(590, 53, 46, 24);
		panel.add(lblShowErroQuantidade);
		
		lblShowErroCliente = new JLabel("");
		lblShowErroCliente.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/iconFechar.png")));
		lblShowErroCliente.setBounds(392, 8, 24, 20);
		panel.add(lblShowErroCliente);
		
		JLabel lblNewLabel_1 = new JLabel("Pre\u00E7o Total");
		lblNewLabel_1.setBounds(10, 141, 68, 14);
		panel.add(lblNewLabel_1);
		
		textFieldPreçoTotal = new JTextField();
		textFieldPreçoTotal.setEditable(false);
		textFieldPreçoTotal.setBounds(109, 138, 277, 20);
		panel.add(textFieldPreçoTotal);
		textFieldPreçoTotal.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 205, 692, 43);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/book_add.png")));
		btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/sair.png")));
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/book_edit.png")));
		
		btnInserir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnInserir.setBackground(UIManager.getColor("Button.background"));
		btnInserir.setBounds(10, 11, 102, 21);
		panel_1.add(btnInserir);
		
		
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(122, 11, 102, 21);
		panel_1.add(btnAlterar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/book_delete.png")));
		btnDeletar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDeletar.setBounds(234, 11, 102, 21);
		panel_1.add(btnDeletar);
		
		
		btnEncerrar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnEncerrar.setBounds(580, 11, 102, 21);
		panel_1.add(btnEncerrar);
		
		lblShowErroProduto.setVisible(false);
		lblShowErroQuantidade.setVisible(false);
		lblShowErroCliente.setVisible(false);
	}
}
