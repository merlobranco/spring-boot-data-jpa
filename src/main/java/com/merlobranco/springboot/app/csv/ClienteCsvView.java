package com.merlobranco.springboot.app.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component("/listar")
public class ClienteCsvView extends AbstractView {
	
	public ClienteCsvView() {
		setContentType("text/csv");
	}


	// Since it is a downloadable content the bellow method should be override
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Providing a custom name to the file
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		response.setContentType(getContentType());
	}
}
