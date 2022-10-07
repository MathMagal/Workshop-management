package com.projeto.view.pedido;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Pedido;
import com.projeto.model.service.PedidoService;
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

public class BuscarPedido extends JDialog {

	
	
	private static final long serialVersionUID = 3277907702980939499L;
	
	private static final int CODIGO 	 = 0;
	private static final int PAGAMENTO   = 1;
	private static final int STATUS      = 2;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldBuscarPedidoPorNome;
	
	private JButton okButton; 
	private JButton cancelButton;
	private JButton btnCadastrarPedido;
	
	private JTable tablePedido;
	private JScrollPane scrollPane;
	
	private TabelaPedidoModel tabelaPedidoModel;
	private TableRowSorter<TabelaPedidoModel> sortTabelaPedido;
	private List<RowSorter.SortKey> sortKeys;
	
	private List<Pedido> listaPedido;
	private Pedido pedido;
	private PedidoService pedidoService;
	
	private Integer codigoPedido = 0;
	private Integer row=0;
	private String nomePedido;
	private boolean isConfirmado;
	
	

	public BuscarPedido(JFrame frame, boolean modal ) {
		
		super(frame, modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarPedido.class.getResource("/imagens/search.png")));
		initComponents();
		createEvents();
		iniciarDados();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	private void iniciarDados() {
		listaPedido = new ArrayList<Pedido>();
	}
	
	protected void inserirPedido() {
		PedidoFrame PedidoFrame = new PedidoFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tablePedido, tabelaPedidoModel, 0);
		PedidoFrame.setLocationRelativeTo(null);
		PedidoFrame.setVisible(true);
	    tabelaPedidoModel.fireTableDataChanged();	
	}

	protected void selecionaPedido(MouseEvent e) {
		row = tablePedido.getSelectedRow();
		
		 if ( tablePedido.getRowSorter() != null ) {
			row =  tablePedido.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaPedido() {
		if ( tablePedido.getSelectedRow() != -1 && 
			 tablePedido.getSelectedRow() < tabelaPedidoModel.getRowCount() ) {
			 pedido = new Pedido();
			 //setCodigoPedido(Integer.valueOf(tablePedido.getValueAt(tablePedido.getSelectedRow(), CODIGO).toString()));
			 //setNomePedido(tablePedido.getValueAt(tablePedido.getSelectedRow(), NOME).toString());
			 //row = tablePedido.getSelectedRow();
			 //if ( tablePedido.getRowSorter() != null ) {
			 //	 row =  tablePedido.getRowSorter().convertRowIndexToModel(row);
			 //}
			 setConfirmado(ProcessamentoDados.VERDADEIRO);
			 pedido = tabelaPedidoModel.getPedido(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}


	private List<Pedido> carregarListaPedido() {
		
		PedidoService pedidoService = new PedidoService();
		
		return pedidoService.carregarListaPedido();

	}
	
	
	
	

	private void createEvents() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tablePedido.getSelectedRow() != -1 && tablePedido.getSelectedRow()< tabelaPedidoModel.getRowCount()) {
					selecionaPedido();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnCadastrarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirPedido();
			}
		});
		textFieldBuscarPedidoPorNome.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				pesquisarPedido();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pesquisarPedido();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pesquisarPedido();
			}

			
		});
		
	}
	
	
	private void pesquisarPedido() {
		
		List<Pedido> listaPedido = new ArrayList<Pedido>();
		pedidoService = new PedidoService();
		
		listaPedido = pedidoService.BuscarPedido(textFieldBuscarPedidoPorNome.getText());
		tabelaPedidoModel.setListaPedido(listaPedido);
		
		sortTabelaPedido = new TableRowSorter<TabelaPedidoModel>(tabelaPedidoModel);
		tablePedido.setRowSorter(sortTabelaPedido);
	}
	

	private void initComponents() {
		
		setTitle("Buscar Pedido");
		setBounds(100, 100, 853, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonPane.setBounds(10, 430, 818, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				okButton = new JButton("Confirmar");
				okButton.setToolTipText("Confirmar selecao de Pedido");
				okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				okButton.setIcon(new ImageIcon(BuscarPedido.class.getResource("/imagens/ok.png")));
				okButton.setBounds(10, 5, 100, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setToolTipText("Cancelar selecao de Pedido");
				cancelButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				cancelButton.setIcon(new ImageIcon(BuscarPedido.class.getResource("/imagens/iconFechar.png")));
				cancelButton.setBounds(120, 5, 100, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			tabelaPedidoModel = new TabelaPedidoModel();
			
			listaPedido = carregarListaPedido();
			tabelaPedidoModel.setListaPedido(listaPedido);
			
			sortTabelaPedido = new TableRowSorter<TabelaPedidoModel>(tabelaPedidoModel);
			JPanel panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel.setBounds(10, 11, 818, 408);
			contentPanel.add(panel);
			contentPanel.setLayout(null);
			panel.setLayout(null);
			{
				textFieldBuscarPedidoPorNome = new JTextField();
				textFieldBuscarPedidoPorNome.setBounds(106, 24, 469, 20);
				panel.add(textFieldBuscarPedidoPorNome);
				textFieldBuscarPedidoPorNome.setColumns(10);
			}
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 55, 775, 328);
			panel.add(scrollPane);
			{
				tablePedido = new JTable();
				scrollPane.setViewportView(tablePedido);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			}
			
			tablePedido.setModel(tabelaPedidoModel);
			tablePedido.setFillsViewportHeight(true);
			tablePedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablePedido.setRowSorter(sortTabelaPedido);
			
			tablePedido.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selecionaPedido(e);
				}
			});
			sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(PAGAMENTO, SortOrder.ASCENDING));	
			
			tablePedido.setAutoCreateRowSorter(true);
			tablePedido.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			{
				JLabel lblBuscarPedido = new JLabel("Buscar Pedido:");
				lblBuscarPedido.setBounds(10, 27, 92, 14);
				panel.add(lblBuscarPedido);
				lblBuscarPedido.setHorizontalAlignment(SwingConstants.LEFT);
			}
			
			tablePedido.getColumnModel().getColumn(CODIGO).setPreferredWidth(20);
			tablePedido.getColumnModel().getColumn(PAGAMENTO).setPreferredWidth(20);
			tablePedido.getColumnModel().getColumn(STATUS).setPreferredWidth(20);
			tabelaPedidoModel.fireTableDataChanged();
			
			
			
			btnCadastrarPedido = new JButton("Cadastrar");
			btnCadastrarPedido.setToolTipText("Cadastrar Pedido");
			btnCadastrarPedido.setIcon(new ImageIcon(BuscarPedido.class.getResource("/imagens/evasion.png")));
			btnCadastrarPedido.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			btnCadastrarPedido.setBounds(230, 5, 100, 23);
			buttonPane.add(btnCadastrarPedido);
		}
		
	}

	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}
	
	
	public Integer getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(Integer codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public String getNomePedido() {
		return nomePedido;
	}

	public void setNomePedido(String nomePedido) {
		this.nomePedido = nomePedido;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}