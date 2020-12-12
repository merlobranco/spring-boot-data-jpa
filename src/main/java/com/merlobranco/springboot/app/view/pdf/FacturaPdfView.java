package com.merlobranco.springboot.app.view.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.merlobranco.springboot.app.models.entity.Factura;
import com.merlobranco.springboot.app.models.entity.ItemFactura;

@Component("/factura/ver")
public class FacturaPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura)model.get("factura");
		
		// Table with customer data
		PdfPTable tCliente = new PdfPTable(1);
		tCliente.setSpacingAfter(20);
		tCliente.addCell("Datos del Cliente");
		tCliente.addCell(factura.getCliente().getNombre() + " "+ factura.getCliente().getApellido());
		tCliente.addCell(factura.getCliente().getEmail());
		
		// Table with invoice data
		PdfPTable tFactura = new PdfPTable(1);
		tFactura.setSpacingAfter(20);
		tFactura.addCell("Datos de la Factura");
		tFactura.addCell("Folio: " + factura.getId());
		tFactura.addCell("Descripción: " + factura.getDescripcion());
		tFactura.addCell("Fecha: " + factura.getCreateAt());
		
		// Table with invoice lines data
		PdfPTable tDetalleFactura = new PdfPTable(4);
		tDetalleFactura.addCell("Producto");
		tDetalleFactura.addCell("Precio");
		tDetalleFactura.addCell("Cantidad");
		tDetalleFactura.addCell("Total");
		
		for (ItemFactura item : factura.getItems()) {
			tDetalleFactura.addCell(item.getProducto().getNombre());
			tDetalleFactura.addCell(item.getProducto().getPrecio().toString());
			tDetalleFactura.addCell(item.getCantidad().toString());
			tDetalleFactura.addCell(item.calcularImporte().toString());
		}
		
		PdfPCell cTotal = new PdfPCell(new Phrase("Total: "));
		cTotal.setColspan(3);
		cTotal.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tDetalleFactura.addCell(cTotal);
		tDetalleFactura.addCell(factura.getTotal().toString());
		
		document.add(tCliente);
		document.add(tFactura);
		document.add(tDetalleFactura);
		
	}

}
