package com.zyos.core.common.util.excel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zyos.core.common.controller.ErrorNotificacion;

/**
 * This class contains a example of how use ZyosExcel class
 * 
 */

public class ExampleZyosExcel {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			ExampleZyosExcel ze = new ExampleZyosExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExampleZyosExcel() {
		try {
			Workbook wb = null;
			System.out
					.print("Type xlsx i.e 2007+ if u wan2 create xlsx file (default xls) => ");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String xlsx = br.readLine();
			if (xlsx.equalsIgnoreCase("xlsx"))
				wb = new XSSFWorkbook();
			else
				wb = new HSSFWorkbook();

			Sheet sheet = wb.createSheet("MY_SHEET");

			// row numbering starts from 0
			Row row = sheet.createRow((short) 1);
			Cell cell = row.createCell(0);
			cell.setCellValue("SHIVA");

			String excelFileName = "MY_EXCEL.xls";
			if (wb instanceof XSSFWorkbook)
				excelFileName += "x";

			FileOutputStream fos = new FileOutputStream(excelFileName);
			wb.write(fos);
			fos.flush();
			fos.close();
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * It reads and writes a File.
	 */
	public void readWriteExample() {
		try {
			ZyosExcel ze = new ZyosExcel();
			Workbook wb = ze.readXLSFile("");
			ze.addSheet("testAdd1", wb);
			ze.closeFile(wb, "paola.xls");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
		}
	}

	/**
	 * It reads a File and print the first cell.
	 */
	public void readFileExample() {
		try {
			ZyosExcel ze = new ZyosExcel();
			Workbook wb = ze.readXLSFile("C://globotech.xlsx");

			// Option 1
			Sheet sh = ze.getSheet(wb, 0);
			Row row = ze.getRow(sh, 15);
			System.out.println("Celda:" + row.getCell(1));
			System.out.println("Celda:" + row.getCell(2));
			System.out.println("Celda:" + row.getCell(3));
			System.out.println("Celda:" + row.getCell(4));
			System.out.println("Celda:" + row.getCell(5));
			System.out.println("Celda:" + row.getCell(6));
			System.out.println("Celda:" + row.getCell(7));
			System.out.println("Celda:" + row.getCell(8));
			System.out.println("Celda:" + row.getCell(9));
			System.out.println("Celda:" + row.getCell(10));
			System.out.println("Celda:" + row.getCell(11));
			System.out.println("Celda:" + row.getCell(12));
			System.out.println("Celda:" + row.getCell(13));
			System.out.println("Celda:" + row.getCell(14));
			// row.createCell(1).setCellValue("Probando Escribir archivo");

			// add picture data to this workbook.
			InputStream is = new FileInputStream("C://certificado.jpg");
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();

			CreationHelper helper = wb.getCreationHelper();

			// Create the drawing patriarch. This is the top level container for
			// all shapes.
			Drawing drawing = sh.createDrawingPatriarch();

			// add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner of the picture,
			// subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(3);
			anchor.setRow1(2);
			Picture pict = drawing.createPicture(anchor, pictureIdx);

			// auto-size picture relative to its top-left corner
			pict.resize();

			ze.closeFile(wb, "C://globotech.xlsx");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
		}
	}

	/**
	 * It creates a Excel File
	 */
	public void createExcelFile() {
		try {
			ZyosExcel ze = new ZyosExcel();
			Workbook wb = ze.createExcelFile("test.xls", "test", true);
			ze.closeFile(wb, "test.xls");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
		}
	}

	private static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setFontName("Trebuchet MS");
		style = wb.createCellStyle();
		style.setFont(titleFont);
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		styles.put("title", style);

		Font monthFont = wb.createFont();
		monthFont.setFontHeightInPoints((short) 11);
		monthFont.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(monthFont);
		style.setWrapText(true);
		styles.put("header", style);

		Font cellTextBig = wb.createFont();
		cellTextBig.setFontHeightInPoints((short) 11);
		cellTextBig.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(cellTextBig);
		style.setWrapText(true);
		styles.put("cellTextBig", style);

		Font cellTextBlue = wb.createFont();
		cellTextBlue.setFontHeightInPoints((short) 11);
		cellTextBlue.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(cellTextBig);
		style.setWrapText(true);
		styles.put("cellTextBlue", style);

		Font itemFont = wb.createFont();
		itemFont.setFontHeightInPoints((short) 9);
		itemFont.setFontName("Trebuchet MS");
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setFont(itemFont);
		styles.put("item_left", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(itemFont);
		styles.put("item_right", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(itemFont);
		style.setBorderRight(CellStyle.BORDER_DOTTED);
		style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_DOTTED);
		style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_DOTTED);
		style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setDataFormat(wb.createDataFormat().getFormat(
				"_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);_(@_)"));
		styles.put("input_$", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(itemFont);
		style.setBorderRight(CellStyle.BORDER_DOTTED);
		style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_DOTTED);
		style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_DOTTED);
		style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setDataFormat(wb.createDataFormat().getFormat("0.000%"));
		styles.put("input_%", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(itemFont);
		style.setBorderRight(CellStyle.BORDER_DOTTED);
		style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_DOTTED);
		style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_DOTTED);
		style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setDataFormat(wb.createDataFormat().getFormat("0"));
		styles.put("input_i", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(itemFont);
		style.setDataFormat(wb.createDataFormat().getFormat("m/d/yy"));
		styles.put("input_d", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(itemFont);
		style.setBorderRight(CellStyle.BORDER_DOTTED);
		style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_DOTTED);
		style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_DOTTED);
		style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setDataFormat(wb.createDataFormat().getFormat("$##,##0.00"));
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("formula_$", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setFont(itemFont);
		style.setBorderRight(CellStyle.BORDER_DOTTED);
		style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_DOTTED);
		style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_DOTTED);
		style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setDataFormat(wb.createDataFormat().getFormat("0"));
		style.setBorderBottom(CellStyle.BORDER_DOTTED);
		style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put("formula_i", style);

		return styles;
	}

}
