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

import com.merlobranco.springboot.app.models.entity.Factura;

@Component("/factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura)model.get("factura");
		
		Sheet sheet = workbook.createSheet();
		
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
		
	}

}
