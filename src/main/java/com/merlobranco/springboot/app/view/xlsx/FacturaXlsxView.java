package com.merlobranco.springboot.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.merlobranco.springboot.app.models.entity.Factura;
import com.merlobranco.springboot.app.models.entity.ItemFactura;

@Component("/factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura)model.get("factura");
		
		Sheet sheet = workbook.createSheet("Factura Spring");
		
		// Customer data
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Datos del Cliente");
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " "+ factura.getCliente().getApellido());
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		// Invoice data
		// Better and simpler approach
		sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
		row = sheet.createRow(5);
		row.createCell(0).setCellValue("Folio: ");
		row.createCell(1).setCellValue(factura.getId());
		row = sheet.createRow(6);
		row.createCell(0).setCellValue("Descripción:");
		row.createCell(1).setCellValue(factura.getDescripcion());
		row = sheet.createRow(7);
		row.createCell(0).setCellValue("Fecha:");
		row.createCell(1).setCellValue(factura.getCreateAt());
		
		// Invoice lines data
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue("Producto");
		header.createCell(1).setCellValue("Precio");
		header.createCell(2).setCellValue("Cantidad");
		header.createCell(3).setCellValue("Total");
		
		int cont = 10;
		Row line;
		for (ItemFactura item : factura.getItems()) {
			line = sheet.createRow(cont);
			line.createCell(0).setCellValue(item.getProducto().getNombre());
			line.createCell(1).setCellValue(item.getProducto().getPrecio());
			line.createCell(2).setCellValue(item.getCantidad());
			line.createCell(3).setCellValue(item.calcularImporte());
			cont++;
		}
		
		// Invoice total amount
		line = sheet.createRow(cont);
		line.createCell(2).setCellValue("Gran Total");
		line.createCell(3).setCellValue(factura.getTotal());
		
	}

}
