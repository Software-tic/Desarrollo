package com.zyos.core.common.util.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 * Class for creating reports
 * 
 * @author Zyos-Home
 * 
 */
public class ZyosReport {

	private JasperPrint jasperPrint;

	/**
	 * Default constructor for creating a report easily
	 * 
	 * @param internalPath
	 *            server URL when is located the jasper file (XML file created
	 *            using iReport)
	 * @param params
	 *            Hashmap with the report parameters
	 * @param tableList
	 *            List with the array object
	 * @throws JRException
	 */
	public ZyosReport(String internalPath, HashMap<String, Object> params,
			List tableList) throws JRException {
		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(internalPath);
		JRBeanCollectionDataSource dataList = new JRBeanCollectionDataSource(
				tableList);
		jasperPrint = JasperFillManager
				.fillReport(reportPath, params, dataList);
	}

	public void PDF(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".pdf");
		ServletOutputStream sos = httpsr.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, sos);
		FacesContext.getCurrentInstance().responseComplete();

	}

	public void DOCX(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".docx");
		ServletOutputStream sos = httpsr.getOutputStream();
		JRDocxExporter docx = new JRDocxExporter();
		docx.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docx.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
		docx.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void XLSX(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".xlsx");
		ServletOutputStream sos = httpsr.getOutputStream();
		JRXlsxExporter xlsx = new JRXlsxExporter();
		xlsx.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		xlsx.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
		xlsx.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void ODT(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".odt");
		ServletOutputStream sos = httpsr.getOutputStream();
		JROdtExporter odt = new JROdtExporter();
		odt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		odt.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
		odt.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void PPTX(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".pptx");
		ServletOutputStream sos = httpsr.getOutputStream();
		JRPptxExporter ppt = new JRPptxExporter();
		ppt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		ppt.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
		ppt.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void TXT(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".txt");
		ServletOutputStream sos = httpsr.getOutputStream();
		JRTextExporter txt = new JRTextExporter();
		txt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		txt.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
		txt.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void RTF(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".rtf");
		ServletOutputStream sos = httpsr.getOutputStream();
		JRRtfExporter rtf = new JRRtfExporter();
		rtf.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		rtf.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
		rtf.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void CSV(String reportName) throws JRException, IOException {
		HttpServletResponse httpsr = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpsr.addHeader("Content-disposition", "attachment; filename="
				+ reportName + ".csv");
		ServletOutputStream sos = httpsr.getOutputStream();
		JRCsvExporter csv = new JRCsvExporter();
		csv.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		csv.setParameter(JRExporterParameter.OUTPUT_STREAM, sos);
		csv.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}
}
