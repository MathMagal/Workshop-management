package com.projeto.view.entrega;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Entrega;
import com.projeto.model.service.EntregaService;
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

public class BuscarEntrega extends JDialog {

	
	
	private static final long serialVersionUID = 3277907702980939499L;
	
	private static final int CODIGO 		= 0;
	private static final int ESTADO			= 1;
	private static final int DATA 			= 2;
	private static final int ENTREGADOR 	= 3;
	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldBuscarEntregaPorNome;
	
	private JButton okButton; 
	private JButton cancelButton;
	private JButton btnCadastrarEntrega;
	
	private JTable tableEntrega;
	private JScrollPane scrollPane;
	
	private TabelaEntregaModel tabelaEntregaModel;
	private TableRowSorter<TabelaEntregaModel> sortTabelaEntrega;
	private List<RowSorter.SortKey> sortKeys;
	
	private List<Entrega> listaEntrega;
	private Entrega entrega;
	private EntregaService entregaService;
	
	private Integer codigoEntrega = 0;
	private Integer row=0;
	private String nomeEntrega;
	private boolean isConfirmado;
	
	

	public BuscarEntrega(JFrame frame, boolean modal ) {
		
		super(frame, modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarEntrega.class.getResource("/imagens/search.png")));
		initComponents();
		createEvents();
		iniciarDados();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	private void iniciarDados() {
		listaEntrega = new ArrayList<Entrega>();
	}
	
	protected void inserirEntrega() {
		EntregaFrame EntregaFrame = new EntregaFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tableEntrega, tabelaEntregaModel, 0);
		EntregaFrame.setLocationRelativeTo(null);
		EntregaFrame.setVisible(true);
	    tabelaEntregaModel.fireTableDataChanged();	
	}

	protected void selecionaEntrega(MouseEvent e) {
		row = tableEntrega.getSelectedRow();
		
		 if ( tableEntrega.getRowSorter() != null ) {
			row =  tableEntrega.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaEntrega() {
		if ( tableEntrega.getSelectedRow() != -1 && 
			 tableEntrega.getSelectedRow() < tabelaEntregaModel.getRowCount() ) {
			 entrega = new Entrega();
			 //setCodigoEntrega(Integer.valueOf(tableEntrega.getValueAt(tableEntrega.getSelectedRow(), CODIGO).toString()));
			 //setNomeEntrega(tableEntrega.getValueAt(tableEntrega.getSelectedRow(), NOME).toString());
			 //row = tableEntrega.getSelectedRow();
			 //if ( tableEntrega.getRowSorter() != null ) {
			 //	 row =  tableEntrega.getRowSorter().convertRowIndexToModel(row);
			 //}
			 setConfirmado(ProcessamentoDados.VERDADEIRO);
			 entrega = tabelaEntregaModel.getEntrega(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}


	private List<Entrega> carregarListaEntrega() {
		
		EntregaService entregaService = new EntregaService();
		
		return entregaService.carregarListaEntrega();

	}
	
	
	
	

	private void createEvents() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableEntrega.getSelectedRow() != -1 && tableEntrega.getSelectedRow()< tabelaEntregaModel.getRowCount()) {
					selecionaEntrega();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnCadastrarEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirEntrega();
			}
		});
		textFieldBuscarEntregaPorNome.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				pesquisarEntrega();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pesquisarEntrega();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pesquisarEntrega();
			}

			
		});
		
	}
	
	
	private void pesquisarEntrega() {
		
		List<Entrega> listaEntrega = new ArrayList<Entrega>();
		entregaService = new EntregaService();
		
		listaEntrega = entregaService.mostrarEntrega(textFieldBuscarEntregaPorNome.getText());
		tabelaEntregaModel.setListaEntrega(listaEntrega);
		
		sortTabelaEntrega = new TableRowSorter<TabelaEntregaModel>(tabelaEntregaModel);
		tableEntrega.setRowSorter(sortTabelaEntrega);
	}
	

	private void initComponents() {
		
		setTitle("Buscar Entrega");
		setBounds(100, 100, 667, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonPane.setBounds(10, 430, 630, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				okButton = new JButton("Confirmar");
				okButton.setToolTipText("Confirmar selecao de Entrega");
				okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				okButton.setIcon(new ImageIcon(BuscarEntrega.class.getResource("/imagens/ok.png")));
				okButton.setBounds(10, 5, 100, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setToolTipText("Cancelar selecao de Entrega");
				cancelButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				cancelButton.setIcon(new ImageIcon(BuscarEntrega.class.getResource("/imagens/iconFechar.png")));
				cancelButton.setBounds(120, 5, 100, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			tabelaEntregaModel = new TabelaEntregaModel();
			
			listaEntrega = carregarListaEntrega();
			tabelaEntregaModel.setListaEntrega(listaEntrega);
			
			sortTabelaEntrega = new TableRowSorter<TabelaEntregaModel>(tabelaEntregaModel);
			JPanel panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel.setBounds(10, 11, 630, 408);
			contentPanel.add(panel);
			contentPanel.setLayout(null);
			panel.setLayout(null);
			{
				textFieldBuscarEntregaPorNome = new JTextField();
				textFieldBuscarEntregaPorNome.setBounds(106, 24, 469, 20);
				panel.add(textFieldBuscarEntregaPorNome);
				textFieldBuscarEntregaPorNome.setColumns(10);
			}
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 55, 609, 328);
			panel.add(scrollPane);
			{
				tableEntrega = new JTable();
				scrollPane.setViewportView(tableEntrega);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			}
			
			tableEntrega.setModel(tabelaEntregaModel);
			tableEntrega.setFillsViewportHeight(true);
			tableEntrega.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableEntrega.setRowSorter(sortTabelaEntrega);
			
			tableEntrega.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selecionaEntrega(e);
				}
			});
			sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(ESTADO, SortOrder.ASCENDING));	
			
			tableEntrega.setAutoCreateRowSorter(true);
			tableEntrega.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			{
				JLabel lblBuscarEntrega = new JLabel("Buscar Entrega:");
				lblBuscarEntrega.setBounds(10, 27, 92, 14);
				panel.add(lblBuscarEntrega);
				lblBuscarEntrega.setHorizontalAlignment(SwingConstants.LEFT);
			}
			
			tableEntrega.getColumnModel().getColumn(CODIGO).setPreferredWidth(5);
			tableEntrega.getColumnModel().getColumn(ESTADO).setPreferredWidth(30);
			tableEntrega.getColumnModel().getColumn(DATA).setPreferredWidth(20);
			tableEntrega.getColumnModel().getColumn(ENTREGADOR).setPreferredWidth(50);
			tabelaEntregaModel.fireTableDataChanged();
			
			
			
			btnCadastrarEntrega = new JButton("Cadastrar");
			btnCadastrarEntrega.setToolTipText("Cadastrar Entrega");
			btnCadastrarEntrega.setIcon(new ImageIcon(BuscarEntrega.class.getResource("/imagens/evasion.png")));
			btnCadastrarEntrega.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			btnCadastrarEntrega.setBounds(230, 5, 100, 23);
			buttonPane.add(btnCadastrarEntrega);
		}
		
	}

	public List<Entrega> getListaEntrega() {
		return listaEntrega;
	}

	public void setListaEntrega(List<Entrega> listaEntrega) {
		this.listaEntrega = listaEntrega;
	}
	
	
	public Integer getCodigoEntrega() {
		return codigoEntrega;
	}

	public void setCodigoEntrega(Integer codigoEntrega) {
		this.codigoEntrega = codigoEntrega;
	}

	public String getNomeEntrega() {
		return nomeEntrega;
	}

	public void setNomeEntrega(String nomeEntrega) {
		this.nomeEntrega = nomeEntrega;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}
	
	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}
	
	
}
