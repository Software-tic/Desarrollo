package com.zyos.alert.query.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.common.util.excel.ZyosExcel;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean
@ViewScoped
@URLMapping(id = "queryBean", pattern = "/portal/consulta", viewId = "/pages/query/query.jspx")
public class QueryBean extends ZyosBackingBean {

	/**
	 * SIAT TUNJA
	 */
	private static final long serialVersionUID = 1L;
	private boolean showUserList = true;
	private boolean showSubjectByUserList;
	
	private String FacultyNameList, DocentePAAINameList;

	private List<ZyosUser> userList;
	private List<Observation> observationList;
	private List<Faculty> FacultiesList;
	
	private Observation observation;
	private Date dateFrom;
	private Date dateTo;
	private String total;
	private StreamedContent file;
	
	private ZyosUser selectedZyosUser;
	private String Fecha="";
	private QueryController controller = new QueryController();

	public QueryBean() throws Exception {
		userList = controller.getUserPAAIList();
	}

	public void goShowInfo() {
		try {
			Fecha = ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD);	
			this.observationList = null;
			this.observationList = controller.lodInfoObservationToShow(selectedZyosUser);
			totalHours();
			setShowSubjectByUserList(true);
			setPanelView("showSubject", "titulo", "QueryBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void searchReportStudentList() {
		String dF = null, dT= null;
		try {
				if (dateFrom != null) {
					dF = ManageDate.formatDate(dateFrom,ManageDate.YYYY_MM_DD_HH_MM_SS);
				}
				if (dateTo != null) {
					dT = ManageDate.formatDate(dateTo,ManageDate.YYYY_MM_DD_HH_MM_SS);
				}
				
				dateFrom = null;
				dateTo = null;
				if ((dF != null) || (dT != null)) {
					observationList = controller.loadSearchObservationList(selectedZyosUser.getIdZyosUser(),dF,dT);

					if (observationList == null || observationList.size() == 0) {
						addWarn("Filtrar Casos ","No se encontrarón resultados con los criterios de búsqueda.");
					}
				} else {
					addWarn("Filtrar Casos ","No ha ingresado ningun criterio de busqueda.");
				}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		totalHours();
	}
	
	public void totalHours(){
		this.total="";
		double aux = 0;
		for (Observation obs : this.observationList) {
			aux+=Double.valueOf(obs.getTimeintervention());
		}
		this.total="Total: "+aux+" min(s)";
	}
	
	public void cleanSearch() throws Exception {
		this.observationList = controller.lodInfoObservationToShow(selectedZyosUser);
		setDateFrom(null);
		setDateTo(null);
		totalHours();
	}
	
	public List<ZyosUser> onChangeFaculty(){
		return userList;		
	}
	
	public void goDetailReport () {
		ZyosBackingBean.update("riskStudentPanel:DataObservationDialog");
		ZyosBackingBean.getRequestContext().execute("dataObservationDialogWV.show();");
	}
	
	public void goBack(){
		setShowUserList(true);
		setPanelView("showSubject", "titulo", "QueryBean");
	}

	public boolean isShowUserList() {
		return showUserList;
	}

	public void setShowUserList(boolean showUserList) {
		this.showUserList = showUserList;
		this.showSubjectByUserList = !showUserList;
	}

	public List<ZyosUser> getUserList() {
		return userList;
	}

	public void setUserList(List<ZyosUser> userList) {
		this.userList = userList;
	}

	public QueryController getController() {
		return controller;
	}

	public void setController(QueryController controller) {
		this.controller = controller;
	}

	public boolean isShowSubjectByUserList() {
		return showSubjectByUserList;
	}

	public void setShowSubjectByUserList(boolean showSubjectByUserList) {
		this.showSubjectByUserList = showSubjectByUserList;
		this.showUserList = !showSubjectByUserList;
	}

	public List<Faculty> getFacultiesList() {
		return FacultiesList;
	}

	public void setFacultiesList(List<Faculty> facultiesList) {
		FacultiesList = facultiesList;
	}

	public String getFacultyNameList() {
		return FacultyNameList;
	}

	public void setFacultyNameList(String facultyNameList) {
		FacultyNameList = facultyNameList;
	}

	public String getDocentePAAINameList() {
		return DocentePAAINameList;
	}

	public void setDocentePAAINameList(String docentePAAINameList) {
		DocentePAAINameList = docentePAAINameList;
	}

	public ZyosUser getSelectedZyosUser() {
		return selectedZyosUser;
	}

	public void setSelectedZyosUser(ZyosUser selectedZyosUser) {
		this.selectedZyosUser = selectedZyosUser;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

	public List<Observation> getObservationList() {
		return observationList;
	}

	public void setObservationList(List<Observation> observationList) {
		this.observationList = observationList;
	}

	public Observation getObservation() {
		return observation;
	}

	public void setObservation(Observation observation) {
		this.observation = observation;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	public void downLoadFile(String fileurl) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(fileurl);
			String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileurl);
			file = new DefaultStreamedContent (stream, contentType,"reporte.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void generateReportStudentList() {
		try {

			if (this.observationList != null && this.observationList.size() > 0) {

				ZyosExcel ze = new ZyosExcel();

				XSSFWorkbook wb = new XSSFWorkbook();
				Sheet sheet = wb.createSheet();

				CellStyle headerStyle = wb.createCellStyle();
				headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

				int i = 0;
				// header
				Row r = sheet.createRow(i);
				Cell cell = r.createCell(0);
				cell.setCellValue("Fecha");
				
				cell = r.createCell(1);
				cell.setCellValue(Fecha);
				
				i++;
				
				r = sheet.createRow(i);
				cell = r.createCell(0);
				cell.setCellValue("Identificación");

				cell = r.createCell(1);
				cell.setCellValue(selectedZyosUser.getDocumentNumber());
				
				i++;
				
				r = sheet.createRow(i);
				cell = r.createCell(0);
				cell.setCellValue("Nombres");
				
				cell = r.createCell(1);
				cell.setCellValue(selectedZyosUser.getName());
				
				i++;
				
				r = sheet.createRow(i);
				cell = r.createCell(0);
				cell.setCellValue("Apellidos");
				
				cell = r.createCell(1);
				cell.setCellValue(selectedZyosUser.getLastName());
				
				i++;
				
				r = sheet.createRow(i);
				cell = r.createCell(0);
				cell.setCellValue("Email");
				
				cell = r.createCell(1);
				cell.setCellValue(selectedZyosUser.getEmail());
				
				i++;
				
				if (dateFrom != null ){
					r = sheet.createRow(i);
					cell = r.createCell(0);
					cell.setCellValue("Fecha (Desde)");
					
					cell = r.createCell(1);
					cell.setCellValue(ManageDate.formatDate(dateFrom,ManageDate.YYYY_MM_DD));
				
					i++;
				}
				
				if (dateTo != null ){
					r = sheet.createRow(i);
					cell = r.createCell(0);
					cell.setCellValue("Fecha (Hasta)");
					
					cell = r.createCell(1);
					cell.setCellValue(ManageDate.formatDate(dateTo,ManageDate.YYYY_MM_DD));

					i++;
				}
				
				r = sheet.createRow(i);
				cell = r.createCell(0);
				i++;
				
				r = sheet.createRow(i);
				cell = r.createCell(0);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Fecha");

				cell = r.createCell(1);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Código Estudiante");

				cell = r.createCell(2);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Nombre Estudiante");

				cell = r.createCell(3);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Observación");

				cell = r.createCell(4);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Tiempo de Atención");

				i++;

				for (Observation c : observationList) {
					r = sheet.createRow(i);
					cell = r.createCell(0);
					cell.setCellValue(c.getDateIntervention());

					cell = r.createCell(1);
					cell.setCellValue(c.getCodeStudent());

					cell = r.createCell(2);
					cell.setCellValue(c.getNameStudent()+" "+c.getLastNameStudent());

					cell = r.createCell(3);
					cell.setCellValue(c.getDetailObservation());

					cell = r.createCell(4);
					cell.setCellValue(c.getTimeintervention());

					i++;
				}

				wb.getSheetAt(0).autoSizeColumn(0);
				wb.getSheetAt(0).autoSizeColumn(1);
				wb.getSheetAt(0).autoSizeColumn(2);
				wb.getSheetAt(0).autoSizeColumn(3);
				wb.getSheetAt(0).autoSizeColumn(4);
				wb.getSheetAt(0).autoSizeColumn(5);
				wb.getSheetAt(0).autoSizeColumn(6);
				wb.getSheetAt(0).autoSizeColumn(7);
				wb.getSheetAt(0).autoSizeColumn(8);
				wb.getSheetAt(0).autoSizeColumn(9);
				wb.getSheetAt(0).autoSizeColumn(10);
				wb.getSheetAt(0).autoSizeColumn(11);
				wb.getSheetAt(0).autoSizeColumn(12);
				wb.getSheetAt(0).autoSizeColumn(13);
				wb.getSheetAt(0).autoSizeColumn(14);

				File filea = new File(".");
				ze.closeFile(wb, filea.getAbsolutePath()	+ "/reporteIntervenciones.xlsx");
				downLoadFile(filea.getAbsolutePath()	+ "/reporteIntervenciones.xlsx");
				
			} else {
				ZyosBackingBean.addWarn("Generar reporte"," No intervenciones en el listado para generar reporte");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		ZyosBackingBean.update("observationForm:downLoadDialog");
		ZyosBackingBean.getRequestContext().execute("downLoadDialogWV.show();");
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}
	
}
