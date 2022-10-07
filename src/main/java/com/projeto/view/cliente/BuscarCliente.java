package com.projeto.view.cliente;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Cliente;
import com.projeto.model.service.ClienteService;
import com.projeto.util.ProcessamentoDados;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuscarCliente extends JDialog {

	
	
	private static final long serialVersionUID = 3277907702980939499L;
	
	private static final int CODIGO 	= 0;
	private static final int NOME 		= 1;
	private static final int CPF  		= 2;
	private static final int CELULAR 	= 3;
	private static final int EMAIL 		= 4;
	private static final int RUA 		= 5;
	private static final int BAIRRO 	= 6;
	private static final int NUMERO 	= 7;
	private static final int CEP 		= 8;
	private static final int CIDADE 	= 9;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldBuscarClientePorNome;
	
	private JButton okButton; 
	private JButton cancelButton;
	private JButton btnCadastrarCliente;
	
	private JTable tableCliente;
	private JScrollPane scrollPane;
	
	private TabelaClienteModel tabelaClienteModel;
	private TableRowSorter<TabelaClienteModel> sortTabelaCliente;
	private List<RowSorter.SortKey> sortKeys;
	
	private List<Cliente> listaCliente;
	private Cliente cliente;
	private ClienteService clienteService;
	
	private Integer codigoCliente = 0;
	private Integer row=0;
	private String nomeCliente;
	private boolean isConfirmado;
	
	

	public BuscarCliente(JFrame frame, boolean modal ) {
		
		super(frame, modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarCliente.class.getResource("/imagens/search.png")));
		initComponents();
		createEvents();
		iniciarDados();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	private void iniciarDados() {
		listaCliente = new ArrayList<Cliente>();
	}
	
	protected void inserirCliente() {
		ClienteFrame clienteFrame = new ClienteFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tableCliente, tabelaClienteModel, 0);
		clienteFrame.setLocationRelativeTo(null);
		clienteFrame.setVisible(true);
	    tabelaClienteModel.fireTableDataChanged();	
	}

	protected void selecionaCliente(MouseEvent e) {
		row = tableCliente.getSelectedRow();
		
		 if ( tableCliente.getRowSorter() != null ) {
			row =  tableCliente.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaCliente() {
		if ( tableCliente.getSelectedRow() != -1 && 
			 tableCliente.getSelectedRow() < tabelaClienteModel.getRowCount() ) {
			 cliente = new Cliente();
			 //setCodigoCliente(Integer.valueOf(tableCliente.getValueAt(tableCliente.getSelectedRow(), CODIGO).toString()));
			 //setNomeCliente(tableCliente.getValueAt(tableCliente.getSelectedRow(), NOME).toString());
			 //row = tableCliente.getSelectedRow();
			 //if ( tableCliente.getRowSorter() != null ) {
			 //	 row =  tableCliente.getRowSorter().convertRowIndexToModel(row);
			 //}
			 setConfirmado(ProcessamentoDados.VERDADEIRO);
			 cliente = tabelaClienteModel.getCliente(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}


	private List<Cliente> carregarListaCliente() {
		
		ClienteService clienteService = new ClienteService();
		
		return clienteService.carregarListaCliente();

	}
	
	
	
	

	private void createEvents() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableCliente.getSelectedRow() != -1 && tableCliente.getSelectedRow()< tabelaClienteModel.getRowCount()) {
					selecionaCliente();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirCliente();
			}
		});
		textFieldBuscarClientePorNome.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				pesquisarCliente();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pesquisarCliente();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pesquisarCliente();
			}

			
		});
		
	}
	
	
	private void pesquisarCliente() {
		
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		clienteService = new ClienteService();
		
		listaCliente = clienteService.mostrarCliente(textFieldBuscarClientePorNome.getText());
		tabelaClienteModel.setListaCliente(listaCliente);
		
		sortTabelaCliente = new TableRowSorter<TabelaClienteModel>(tabelaClienteModel);
		tableCliente.setRowSorter(sortTabelaCliente);
	}
	

	private void initComponents() {
		
		setTitle("Buscar Cliente");
		setBounds(100, 100, 1148, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonPane.setBounds(10, 430, 1122, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				okButton = new JButton("Confirmar");
				okButton.setToolTipText("Confirmar selecao de cliente");
				okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				okButton.setIcon(new ImageIcon(BuscarCliente.class.getResource("/imagens/ok.png")));
				okButton.setBounds(10, 5, 100, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setToolTipText("Cancelar selecao de cliente");
				cancelButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				cancelButton.setIcon(new ImageIcon(BuscarCliente.class.getResource("/imagens/iconFechar.png")));
				cancelButton.setBounds(120, 5, 100, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			tabelaClienteModel = new TabelaClienteModel();
			
			listaCliente = carregarListaCliente();
			tabelaClienteModel.setListaCliente(listaCliente);
			
			sortTabelaCliente = new TableRowSorter<TabelaClienteModel>(tabelaClienteModel);
			JPanel panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel.setBounds(10, 11, 1122, 408);
			contentPanel.add(panel);
			contentPanel.setLayout(null);
			panel.setLayout(null);
			{
				textFieldBuscarClientePorNome = new JTextField();
				textFieldBuscarClientePorNome.setBounds(106, 24, 469, 20);
				panel.add(textFieldBuscarClientePorNome);
				textFieldBuscarClientePorNome.setColumns(10);
			}
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 55, 1098, 328);
			panel.add(scrollPane);
			{
				tableCliente = new JTable();
				scrollPane.setViewportView(tableCliente);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			}
			
			tableCliente.setModel(tabelaClienteModel);
			tableCliente.setFillsViewportHeight(true);
			tableCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableCliente.setRowSorter(sortTabelaCliente);
			
			tableCliente.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selecionaCliente(e);
				}
			});
			sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(NOME, SortOrder.ASCENDING));	
			
			tableCliente.setAutoCreateRowSorter(true);
			tableCliente.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			{
				JLabel lblBuscarCliente = new JLabel("Buscar Cliente:");
				lblBuscarCliente.setBounds(10, 27, 92, 14);
				panel.add(lblBuscarCliente);
				lblBuscarCliente.setHorizontalAlignment(SwingConstants.LEFT);
			}
			
			tableCliente.getColumnModel().getColumn(CODIGO).setPreferredWidth(20);
			tableCliente.getColumnModel().getColumn(NOME).setPreferredWidth(150);
			tableCliente.getColumnModel().getColumn(CPF).setPreferredWidth(50);
			tableCliente.getColumnModel().getColumn(CELULAR).setPreferredWidth(50);
			tableCliente.getColumnModel().getColumn(EMAIL).setPreferredWidth(120);
			tableCliente.getColumnModel().getColumn(RUA).setPreferredWidth(90);
			tableCliente.getColumnModel().getColumn(BAIRRO).setPreferredWidth(100);
			tableCliente.getColumnModel().getColumn(NUMERO).setPreferredWidth(10);
			tableCliente.getColumnModel().getColumn(CEP).setPreferredWidth(30);
			tableCliente.getColumnModel().getColumn(CIDADE).setPreferredWidth(100);
			tabelaClienteModel.fireTableDataChanged();
			
			
			
			btnCadastrarCliente = new JButton("Cadastrar");
			btnCadastrarCliente.setToolTipText("Cadastrar Cliente");
			btnCadastrarCliente.setIcon(new ImageIcon(BuscarCliente.class.getResource("/imagens/evasion.png")));
			btnCadastrarCliente.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			btnCadastrarCliente.setBounds(230, 5, 100, 23);
			buttonPane.add(btnCadastrarCliente);
		}
		
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
	
	
	public Integer getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Integer codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
