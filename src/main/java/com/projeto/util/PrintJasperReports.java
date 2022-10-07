package com.projeto.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import net.sf.jasperreports.engine.JRParameter;

public class PrintJasperReports {
	
	private String file;
	private Map<String, Object> params;
	private Collection<?> collection;
	
	public PrintJasperReports() {
		this.params = new HashMap<String, Object>();
		this.params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}

	public PrintJasperReports(Map<String, Object> params) {
		this.params = params;
	}

	public void addParametros(String key, Object value) {
		getParams().put(key, value);
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Collection<?> getCollection() {
		return collection;
	}
	public void setCollection(Collection<?> collection) {
		this.collection = collection;
	}
	
	
	
	
	
}
