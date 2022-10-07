package com.projeto.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;


public class ProcessamentoDados {
	public static final boolean FALSO = false;
	public static final boolean VERDADEIRO = true;
	
	
	public static final String RELATORIO = "reports/";
	public static final String SUFIXO_RELATORIO_COMPILADO = ".jasper";
	public static final String SUFIXO_RELATORIO_JRXML = ".jrxml";
	
	
	public static final Integer INCLUIR = 0;
	public static final Integer ALTERAR = 1;
	public static final Integer EXCLUIR = 2;
	public static final Integer CONSULTAR = 3;
	public static final Color VERMELHO = new Color(204,0,0);
	public static final Color VERDE = new Color(0,153,0);
	public static final Color AMARELO = new Color(255,123,21);
	
	
	
	public static boolean digitacaoCampo(String texto){
		
		if(Objects.isNull(texto)) {
			return VERDADEIRO;
		}
		
		if ("".equals(texto.trim())){
			return VERDADEIRO;
		}
		
		return FALSO;
	}
	
	public static Integer converterParaInteiro(String texto) {
		
		return Integer.parseInt(texto);
	}
	
	
	public static String converterInteiroParaString(Integer texto) {
		return texto.toString();
	}
	
	public static void showMensagem(String mensagem, String status, int option) {
		JOptionPane.showMessageDialog(null, mensagem, status, option);
	}
	
	
	public static DefaultTableCellRenderer alinharColunaCentro() {
		DefaultTableCellRenderer alinharCentro = new DefaultTableCellRenderer();
		alinharCentro.setHorizontalAlignment(SwingConstants.CENTER);
		return alinharCentro;
	}
	
	public static  DefaultTableCellRenderer alinharColunaDireta() {
		DefaultTableCellRenderer alinharDireita = new DefaultTableCellRenderer();
		alinharDireita.setHorizontalAlignment(SwingConstants.RIGHT);
		return alinharDireita;
	}
	
	public static DefaultTableCellRenderer alinharColunaEsquerda() {
		DefaultTableCellRenderer alinharEsquerda = new DefaultTableCellRenderer();
		alinharEsquerda.setHorizontalAlignment(SwingConstants.LEFT);
		return alinharEsquerda;
	}
	
	
	
	public static String formatarDouble(double numero){
		  String retorno = "";
		  DecimalFormat formatter = new DecimalFormat("#.00");
		  try{
		    retorno = formatter.format(numero);
		  }catch(Exception ex){
		    System.err.println("Erro ao formatar numero: " + ex);
		  }
		  return retorno;
		}
	
	public static void FehcarJanela(JFrame frame){
		JRootPane rootPane = frame.getRootPane();
		
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11,0), "VK_F11");
		rootPane.getRootPane().getActionMap().put("VK_F11", 
												  new AbstractAction("VK_F11") 
			{
			private static final long serialVersionUID = 6972049647801888687L;

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	public static void FehcarJanela(JInternalFrame frame){
		JRootPane rootPane = frame.getRootPane();
		
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11,0), "VK_F11");
		rootPane.getRootPane().getActionMap().put("VK_F11", 
												  new AbstractAction("VK_F11") 
			{
			private static final long serialVersionUID = 6972049647801888687L;

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
	}
	public static void FehcarJanela(JDialog frame){
		JRootPane rootPane = frame.getRootPane();
		
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11,0), "VK_F11");
		rootPane.getRootPane().getActionMap().put("VK_F11", 
												  new AbstractAction("VK_F11") 
			{
			private static final long serialVersionUID = 6972049647801888687L;

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	
	public static BasicInternalFrameUI removeMouseListener(JInternalFrame frame) {
		BasicInternalFrameUI bif =  (BasicInternalFrameUI) frame.getUI();
		for(MouseListener ml : bif.getNorthPane().getMouseListeners()) {
			bif.getNorthPane().removeMouseListener(ml);
		}
		return bif;	
	}
	
	
	
}
