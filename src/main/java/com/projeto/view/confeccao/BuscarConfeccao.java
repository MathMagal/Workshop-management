package com.projeto.view.confeccao;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Confeccao;
import com.projeto.model.service.ConfeccaoService;
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

public class BuscarConfeccao extends JDialog {

	
	
	private static final long serialVersionUID = 3277907702980939499L;
	
	private static final int CODIGO 	= 0;
	private static final int ESTADO		= 1;
	private static final int INICIO  	= 2;
	private static final int FIM 		= 3;
	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldBuscarConfeccaoPorNome;
	
	private JButton okButton; 
	private JButton cancelButton;
	private JButton btnCadastrarConfeccao;
	
	private JTable tableConfeccao;
	private JScrollPane scrollPane;
	
	private TabelaConfeccaoModel tabelaConfeccaoModel;
	private TableRowSorter<TabelaConfeccaoModel> sortTabelaConfeccao;
	private List<RowSorter.SortKey> sortKeys;
	
	private List<Confeccao> listaConfeccao;
	private Confeccao confeccao;
	private ConfeccaoService confeccaoService;
	
	private Integer codigoConfeccao = 0;
	private Integer row=0;
	private String nomeConfeccao;
	private boolean isConfirmado;
	
	

	public BuscarConfeccao(JFrame frame, boolean modal ) {
		
		super(frame, modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BuscarConfeccao.class.getResource("/imagens/search.png")));
		initComponents();
		createEvents();
		iniciarDados();
		setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	private void iniciarDados() {
		listaConfeccao = new ArrayList<Confeccao>();
	}
	
	protected void inserirConfeccao() {
		ConfeccaoFrame ConfeccaoFrame = new ConfeccaoFrame(new JFrame(), true, ProcessamentoDados.INCLUIR, tableConfeccao, tabelaConfeccaoModel, 0);
		ConfeccaoFrame.setLocationRelativeTo(null);
		ConfeccaoFrame.setVisible(true);
	    tabelaConfeccaoModel.fireTableDataChanged();	
	}

	protected void selecionaConfeccao(MouseEvent e) {
		row = tableConfeccao.getSelectedRow();
		
		 if ( tableConfeccao.getRowSorter() != null ) {
			row =  tableConfeccao.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionaConfeccao() {
		if ( tableConfeccao.getSelectedRow() != -1 && 
			 tableConfeccao.getSelectedRow() < tabelaConfeccaoModel.getRowCount() ) {
			 confeccao = new Confeccao();
			 //setCodigoConfeccao(Integer.valueOf(tableConfeccao.getValueAt(tableConfeccao.getSelectedRow(), CODIGO).toString()));
			 //setNomeConfeccao(tableConfeccao.getValueAt(tableConfeccao.getSelectedRow(), NOME).toString());
			 //row = tableConfeccao.getSelectedRow();
			 //if ( tableConfeccao.getRowSorter() != null ) {
			 //	 row =  tableConfeccao.getRowSorter().convertRowIndexToModel(row);
			 //}
			 setConfirmado(ProcessamentoDados.VERDADEIRO);
			 confeccao = tabelaConfeccaoModel.getConfeccao(row);
			 dispose();
		} else {
			setConfirmado(ProcessamentoDados.FALSO);
		}
		
	}


	private List<Confeccao> carregarListaConfeccao() {
		
		ConfeccaoService ConfeccaoService = new ConfeccaoService();
		
		return ConfeccaoService.carregarListaConfeccao();

	}
	
	
	
	

	private void createEvents() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableConfeccao.getSelectedRow() != -1 && tableConfeccao.getSelectedRow()< tabelaConfeccaoModel.getRowCount()) {
					selecionaConfeccao();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnCadastrarConfeccao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirConfeccao();
			}
		});
		textFieldBuscarConfeccaoPorNome.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				pesquisarConfeccao();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pesquisarConfeccao();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pesquisarConfeccao();
			}

			
		});
		
	}
	
	
	private void pesquisarConfeccao() {
		
		List<Confeccao> listaConfeccao = new ArrayList<Confeccao>();
		confeccaoService = new ConfeccaoService();
		
		listaConfeccao = confeccaoService.mostrarConfeccao(textFieldBuscarConfeccaoPorNome.getText());
		tabelaConfeccaoModel.setListaConfeccao(listaConfeccao);
		
		sortTabelaConfeccao = new TableRowSorter<TabelaConfeccaoModel>(tabelaConfeccaoModel);
		tableConfeccao.setRowSorter(sortTabelaConfeccao);
	}
	

	private void initComponents() {
		
		setTitle("Buscar Confeccao");
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
				okButton.setToolTipText("Confirmar selecao de Confeccao");
				okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				okButton.setIcon(new ImageIcon(BuscarConfeccao.class.getResource("/imagens/ok.png")));
				okButton.setBounds(10, 5, 100, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setToolTipText("Cancelar selecao de Confeccao");
				cancelButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				cancelButton.setIcon(new ImageIcon(BuscarConfeccao.class.getResource("/imagens/iconFechar.png")));
				cancelButton.setBounds(120, 5, 100, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			tabelaConfeccaoModel = new TabelaConfeccaoModel();
			
			listaConfeccao = carregarListaConfeccao();
			tabelaConfeccaoModel.setListaConfeccao(listaConfeccao);
			
			sortTabelaConfeccao = new TableRowSorter<TabelaConfeccaoModel>(tabelaConfeccaoModel);
			JPanel panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			panel.setBounds(10, 11, 630, 408);
			contentPanel.add(panel);
			contentPanel.setLayout(null);
			panel.setLayout(null);
			{
				textFieldBuscarConfeccaoPorNome = new JTextField();
				textFieldBuscarConfeccaoPorNome.setBounds(106, 24, 469, 20);
				panel.add(textFieldBuscarConfeccaoPorNome);
				textFieldBuscarConfeccaoPorNome.setColumns(10);
			}
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 55, 609, 328);
			panel.add(scrollPane);
			{
				tableConfeccao = new JTable();
				scrollPane.setViewportView(tableConfeccao);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			}
			
			tableConfeccao.setModel(tabelaConfeccaoModel);
			tableConfeccao.setFillsViewportHeight(true);
			tableConfeccao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableConfeccao.setRowSorter(sortTabelaConfeccao);
			
			tableConfeccao.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					selecionaConfeccao(e);
				}
			});
			sortKeys = new ArrayList<RowSorter.SortKey>();
			sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
			sortKeys.add(new RowSorter.SortKey(ESTADO, SortOrder.ASCENDING));	
			
			tableConfeccao.setAutoCreateRowSorter(true);
			tableConfeccao.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			{
				JLabel lblBuscarConfeccao = new JLabel("Buscar Confeccao:");
				lblBuscarConfeccao.setBounds(10, 27, 92, 14);
				panel.add(lblBuscarConfeccao);
				lblBuscarConfeccao.setHorizontalAlignment(SwingConstants.LEFT);
			}
			
			tableConfeccao.getColumnModel().getColumn(CODIGO).setPreferredWidth(5);
			tableConfeccao.getColumnModel().getColumn(ESTADO).setPreferredWidth(30);
			tableConfeccao.getColumnModel().getColumn(INICIO).setPreferredWidth(20);
			tableConfeccao.getColumnModel().getColumn(FIM).setPreferredWidth(20);
			tabelaConfeccaoModel.fireTableDataChanged();
			
			
			
			btnCadastrarConfeccao = new JButton("Cadastrar");
			btnCadastrarConfeccao.setToolTipText("Cadastrar Confeccao");
			btnCadastrarConfeccao.setIcon(new ImageIcon(BuscarConfeccao.class.getResource("/imagens/evasion.png")));
			btnCadastrarConfeccao.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			btnCadastrarConfeccao.setBounds(230, 5, 100, 23);
			buttonPane.add(btnCadastrarConfeccao);
		}
		
	}

	public List<Confeccao> getListaConfeccao() {
		return listaConfeccao;
	}

	public void setListaConfeccao(List<Confeccao> listaConfeccao) {
		this.listaConfeccao = listaConfeccao;
	}
	
	
	public Integer getCodigoConfeccao() {
		return codigoConfeccao;
	}

	public void setCodigoConfeccao(Integer codigoConfeccao) {
		this.codigoConfeccao = codigoConfeccao;
	}

	public String getNomeConfeccao() {
		return nomeConfeccao;
	}

	public void setNomeConfeccao(String nomeConfeccao) {
		this.nomeConfeccao = nomeConfeccao;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}
	
	public Confeccao getConfeccao() {
		return confeccao;
	}

	public void setConfeccao(Confeccao confeccao) {
		this.confeccao = confeccao;
	}
	
	
}
