package com.projeto.model.service;



import java.io.InputStream;

import javax.swing.JFrame;


import com.projeto.persistencia.DataSource;
import com.projeto.util.PrintJasperReports;
import com.projeto.util.ProcessamentoDados;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class JasperReportsService extends JFrame{
	
	
	private static final long serialVersionUID = 7962367834473320157L;
	private DataSource dataSource;

	public JasperReportsService() {
		dataSource = new DataSource();
	}
	
	public JasperPrint gerarRelatorioPorSQL(PrintJasperReports printJasperReports) {
		try {
			InputStream fileInput = openFile(printJasperReports);
										  
			JasperPrint jasperPrint = JasperFillManager.fillReport(fileInput, printJasperReports.getParams(), dataSource.getConnection());
			return jasperPrint;
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public JasperPrint gerarRelatorioPorLista(PrintJasperReports printJasperReports) {
		try {
			InputStream fileInput = openFile(printJasperReports);							  
			JasperPrint jasperPrint = JasperFillManager.fillReport(fileInput, 
																   printJasperReports.getParams(), 
																   new JRBeanCollectionDataSource(printJasperReports.getCollection()));
			return jasperPrint;
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private InputStream openFile(PrintJasperReports printJasperReports) {
		InputStream fileInput = getClass().getClassLoader()
									  	  .getResourceAsStream(ProcessamentoDados.RELATORIO+
															   printJasperReports.getFile()+
															   ProcessamentoDados.SUFIXO_RELATORIO_COMPILADO);
		return fileInput;
	}
	
	
}
