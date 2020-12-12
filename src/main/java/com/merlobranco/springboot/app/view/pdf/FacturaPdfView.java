package com.merlobranco.springboot.app.view.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.merlobranco.springboot.app.models.entity.Factura;

@Component("/factura/ver")
public class FacturaPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura)model.get("factura");
		
		PdfPTable tCliente = new PdfPTable(1);
		tCliente.setSpacingAfter(20);
		tCliente.addCell("Datos del Cliente");
		tCliente.addCell(factura.getCliente().getNombre() + " "+ factura.getCliente().getApellido());
		tCliente.addCell(factura.getCliente().getEmail());
		
		PdfPTable tFactura = new PdfPTable(1);
		tFactura.setSpacingAfter(20);
		tFactura.addCell("Datos de la Factura");
		tFactura.addCell("Folio: " + factura.getId());
		tFactura.addCell("Descripción: " + factura.getDescripcion());
		tFactura.addCell("Fecha: " + factura.getCreateAt());
		
		document.add(tCliente);
		document.add(tFactura);
		
	}

}
