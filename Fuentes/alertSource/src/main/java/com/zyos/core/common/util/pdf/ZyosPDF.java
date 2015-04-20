package com.zyos.core.common.util.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.zyos.core.common.controller.ErrorNotificacion;

public class ZyosPDF implements Serializable{

	public static void main(String[] args) {
		try {
			new ZyosPDF();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ZyosPDF() {
		String[] files = { "C://result.pdf", "C://result.pdf" };
		// joinPDF(files, "C://resultConcat.pdf" );
	}

	public void joinPDF(ArrayList<String> files, String pahtOut)
			throws Exception {
		Document document = new Document();
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(pahtOut));
		try {
			document.open();
			if (document.isOpen()) {
				PdfReader reader;
				int n;
				for (int i = 0; i < files.size(); i++) {
					reader = new PdfReader(files.get(i));
					n = reader.getNumberOfPages();
					for (int page = 0; page < n;) {
						copy.addPage(copy.getImportedPage(reader, ++page));
					}
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			document.close();
		}
	}

	public static void splitPDF(InputStream inputStream,
			OutputStream outputStream, int fromPage, int toPage) {
		Document document = new Document();
		try {
			PdfReader inputPDF = new PdfReader(inputStream);

			int totalPages = inputPDF.getNumberOfPages();

			// make fromPage equals to toPage if it is greater
			if (fromPage > toPage) {
				fromPage = toPage;
			}
			if (toPage > totalPages) {
				toPage = totalPages;
			}

			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data
			PdfImportedPage page;

			while (fromPage <= toPage) {
				document.setPageSize(inputPDF.getPageSize(fromPage));
				document.newPage();
				page = writer.getImportedPage(inputPDF, fromPage);
				cb.addTemplate(page, 0, 0);
				fromPage++;
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "splitPDF");
		} finally {
			if (document.isOpen())
				document.close();
			try {
				if (inputStream != null)
					inputStream.close();
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				ErrorNotificacion.handleErrorMailNotification(e, "splitPDF");
			}
		}
	}
}
