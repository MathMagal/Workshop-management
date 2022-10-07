package com.projeto.model.service.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.projeto.model.model.Cliente;
import com.projeto.util.ProcessamentoDados;

public class RelatorioCliente {
	
	private static String FILE = "/temp/relatorio_cliente.pdf";
	
	private static String IMAGEM = "/imagens/logo.png";
	
	private static Integer TOTAL_LINHA_POR_PAGINA = 38;
	
	private Document documento;
	private DateFormat dateFormat;
	
	public RelatorioCliente() {
		documento = new Document();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	
	
	
	public void generateReports(List<Cliente> listaCliente) {
		documento.setPageSize(PageSize.A4);
		documento.setMargins(10, 10, 36, 36);
		
		Integer totalRegistros = 0;
		Integer pagina= 0;
		Integer linha = 0;
		
		try {
			FileOutputStream out = new FileOutputStream(FILE);
			PdfWriter.getInstance(documento, out);
			
			documento.open();
			
			while(totalRegistros < listaCliente.size()) {
				
				documento.add(createTitle());
				
				Paragraph paragrafo = new Paragraph();
				LineSeparator line = new LineSeparator();
				paragrafo.add(line);
				documento.add(paragrafo);
				
				paragrafo = new Paragraph();
				paragrafo.add(Chunk.NEWLINE);
				documento.add(paragrafo);
				
				documento.add(createHeader());
				
				linha = 0;
				
				while(totalRegistros < listaCliente.size() && linha < TOTAL_LINHA_POR_PAGINA  ) {				
					
					Cliente cliente = listaCliente.get(totalRegistros);
					
					documento.add(createContent(cliente));
					
					
					linha ++;
					totalRegistros++;
				}
				
				if(linha < TOTAL_LINHA_POR_PAGINA) {
					while(linha < TOTAL_LINHA_POR_PAGINA  ) {				

						documento.add(Chunk.NEWLINE);
						linha ++;
					}
				}
				
				pagina++;
				
				
				paragrafo = new Paragraph();
				line = new LineSeparator();
				paragrafo.add(line);
				documento.add(paragrafo);
				
				documento.add(Chunk.NEWLINE);
				
				PdfPTable table = new PdfPTable(3);
				
				
				
				PdfPCell column;
				column = new PdfPCell(new Paragraph("Emissao: "+ dateFormat.format(new Date())));
				column.setBorder(PdfPCell.NO_BORDER);
				column.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				table.addCell(column);
				
				column = new PdfPCell(new Paragraph(""));
				column.setBorder(PdfPCell.NO_BORDER);
				column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				table.addCell(column);
				
				column = new PdfPCell(new Phrase(new Paragraph("Pagina: "+ pagina.toString())));
				column.setBorder(PdfPCell.NO_BORDER);
				column.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				table.addCell(column);
				
				documento.add(table);
				
				documento.newPage();
				
			}
			
			documento.close();

		}catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private PdfPTable createTitle() {
		
		float[] columnsWiths = {1}; 
		
		PdfPTable table = new PdfPTable(columnsWiths);
		
		table.setWidthPercentage(100);
		table.getDefaultCell().setUseAscender(ProcessamentoDados.VERDADEIRO);
		table.getDefaultCell().setUseDescender(ProcessamentoDados.VERDADEIRO);
		
		URL url = RelatorioCliente.class.getResource(IMAGEM);
		
		Image imagem = null;
		
		
	   try {
		imagem = Image.getInstance(url);
		imagem.setAlignment(Image.MIDDLE);
		imagem.scaleToFit(120f, 150f);
		imagem.setAbsolutePosition(78, 770);
		imagem.scaleAbsolute(100,40);
		} catch (BadElementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

		PdfPCell column;
		column = new PdfPCell(imagem);
		column.setBorder(PdfPCell.NO_BORDER);
	    column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    table.addCell(column);
	    
		column = new PdfPCell(new Paragraph("Sistema de Controle de Clientes"));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		
		table.addCell(column);
		
		column = new PdfPCell(new Paragraph("Relatorio de Clientes"));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		
		table.addCell(column);
		
		return table;
	}
	
	private PdfPTable createHeader() {
		
		PdfPTable table = new PdfPTable(4);
		
		float[] columnsWiths = new float[] {20f, 60f, 50f, 50f};
		
		try {
			table.setWidths(columnsWiths);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PdfPCell column;
		column = new PdfPCell(new Phrase("Codigo"));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase("Nome"));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase("CPF"));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase("Telefone"));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		Paragraph paragrafo = new Paragraph();
		LineSeparator line = new LineSeparator();
		paragrafo.add(line);
		try {
			documento.add(paragrafo);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return table;
	}
	
	private PdfPTable createContent(Cliente cliente) {
		
		PdfPTable table = new PdfPTable(4);
		
		float[] columnsWiths = new float[] {20f, 60f, 50f, 50f};
		
		try {
			table.setWidths(columnsWiths);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PdfPCell column;
		column = new PdfPCell(new Phrase(cliente.getId().toString()));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase(cliente.getNome()));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase(cliente.getCpf()));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase(cliente.getCelular()));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(column);
		
		
		return table;
	}

	
}
