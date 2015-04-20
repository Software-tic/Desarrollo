package com.zyos.core.common.util.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zyos.core.common.controller.ErrorNotificacion;

/**
 * This class contains all the procedures for create, write and read a Excel
 * File.
 * 
 */
public class ZyosExcel {

	public ZyosExcel() {
	}

	/**
	 * It creates a workbook document with a sheet by default.
	 * 
	 * @param filePath
	 *            Path when the file will be created
	 * @param nameSheet
	 *            Name of Sheet by Default
	 * @return Workbook File in the path specified with the Sheet called as
	 *         nameSheet value
	 */
	public Workbook createExcelFile(String filePath, String nameSheet,
			boolean isXLSX) {
		try {
			Workbook wb = openFile(isXLSX);
			Sheet sh = wb.createSheet(nameSheet);
			addRow(sh, 0);
			return wb;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		}
	}

	/**
	 * It adds a row to a Sheet
	 * 
	 * @param sh
	 *            Sheet for add the row.
	 * @param indexRow
	 *            Number of row in the sheet
	 * @return Row added
	 */
	public Row addRow(Sheet sh, int indexRow) {
		try {
			return sh.createRow(indexRow);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		}
	}

	/**
	 * method that load a excel file about the file path
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public Workbook readXLSFile(String filePath) throws IOException {
		InputStream inp = null;
		try {
			inp = new FileInputStream(filePath);
			Workbook wb = WorkbookFactory.create(inp);
			return wb;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		} finally {
			if (inp != null) {
				inp.close();
			}
		}
	}

	/**
	 * Method for loading a excel file xls
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Workbook readXLSFile(InputStream is) throws IOException {
		InputStream inp = null;
		try {
			Workbook wb = WorkbookFactory.create(is);
			return wb;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		} finally {
			if (inp != null) {
				inp.close();
			}
		}
	}

	/**
	 * Method for load and read a excel file
	 * 
	 * @param file
	 *            File
	 * @return Workbook in indexSheet
	 * @throws IOException
	 */
	public static XSSFWorkbook readXLSXFile(InputStream is) throws IOException {
		InputStream inp = null;
		try {
			XSSFWorkbook wb = new XSSFWorkbook(is);
			return wb;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		} finally {
			if (inp != null) {
				inp.close();
			}
		}
	}

	/**
	 * It gets a Sheet from a Workbook
	 * 
	 * @param wb
	 *            Workbook which contains the sheet.
	 * @param indexSheet
	 *            number of Sheet in Workbook
	 * @return Sheet of workbook in indexSheet
	 */
	public Sheet getSheet(Workbook wb, int indexSheet) {
		try {
			return wb.getSheetAt(indexSheet);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		}
	}

	/**
	 * It gets a row from a Sheet
	 * 
	 * @param sheet
	 *            Sheet which contains the row.
	 * @param indexRow
	 *            number of Row in Sheet
	 * @return Row of Sheet in indexRow
	 */
	public Row getRow(Sheet sheet, int indexRow) {
		try {
			return sheet.getRow(indexRow);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		}
	}

	/**
	 * It gets a cell from a row
	 * 
	 * @param row
	 *            Row which contains the row.
	 * @param indexCell
	 *            number of cell in Row
	 * @return Cell of Row in indexCell
	 */
	public Cell getCell(Row row, int indexCell) {
		try {
			return row.getCell(indexCell);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
			return null;
		}
	}

	/**
	 * It creates the object to handle the File.
	 * 
	 * @param isXLSX
	 * 
	 * @return
	 */
	public Workbook openFile(boolean isXLSX) {
		if (isXLSX)
			return new XSSFWorkbook();
		else
			return new HSSFWorkbook();
	}

	/**
	 * It closes the file.
	 * 
	 * @param wb
	 *            File Workbook
	 * @param filePath
	 *            Path where is the file open.
	 */
	public void closeFile(Workbook wb, String filePath) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			// ErrorNotificacion.handleErrorMailNotification(e, "excel");
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * It adds a Sheet to a Workbook
	 * 
	 * @param nameSheet
	 *            Name of new Sheet
	 * @param wb
	 *            File Workbook
	 */
	public void addSheet(String nameSheet, Workbook wb) {
		try {
			wb.createSheet(nameSheet);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
		}
	}

	/**
	 * It adds a Cell to a Row
	 * 
	 * @param row
	 *            Row Row will be affected
	 * @param rowCellIndex
	 *            position where the cell value will be inserted
	 * @param value
	 *            value of cell
	 * @param xssfCellStyle
	 */
	public void addCell(Row row, int rowCellIndex, String value) {
		try {
			row.createCell(rowCellIndex).setCellValue(value);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
		}
	}

	/**
	 * Method for adding a cell with a specificated style
	 * 
	 * @param row
	 * @param rowCellIndex
	 * @param value
	 * @param xssfCellStyle
	 */
	public void addCell(Row row, int rowCellIndex, String value,
			XSSFCellStyle xssfCellStyle) {
		try {
			Cell c = row.createCell(rowCellIndex);
			c.setCellValue(value);
			if (xssfCellStyle != null) {
				c.setCellStyle(xssfCellStyle);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "excel");
		}
	}

}
