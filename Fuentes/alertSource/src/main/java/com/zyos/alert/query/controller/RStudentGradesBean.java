package com.zyos.alert.query.controller;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.AcademicPeriod;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;

@ManagedBean
@ViewScoped
@URLMapping(id = "rStudentGradesBean", pattern = "/portal/reporteAcademico", viewId ="/pages/studentReportGRA/report.jspx")
public class RStudentGradesBean extends ZyosBackingBean {

	/**
	 * SIAT TUNJA
	 */
	private static final long serialVersionUID = 1L;
	private boolean showStudentList = true;
	private boolean showFindStudentList;
	private boolean showReportByStudent;
	
	private String FacultyNameList, DocentePAAINameList;

	private List<Student> userList;
	private List<Faculty> FacultiesList;
	private List<AcademicPeriod> PeriodList;

	private Long Period;
	private Student selectedStudent;
	private String Fecha="";
	private RStudentGradesController controller = new RStudentGradesController();

	public RStudentGradesBean() throws Exception {
		this.PeriodList=controller.getAcademicPeriodList();
		showFindStudentList=false;
	}
	
	public void loadStudentList() throws Exception {
		Fecha = ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD);
		Date FechaNow = new Date(System.currentTimeMillis());
		AcademicPeriod SelectedPeriod = controller.searchAcademicPeriod(Period);
		@SuppressWarnings("deprecation")
		Date FechaStart = new Date(SelectedPeriod.getStartDate());
		@SuppressWarnings("deprecation")
		Date FechaEnd = new Date(SelectedPeriod.getEndDate());
		System.out.println("ID PERIOD "+Period+"- PeriodList: "+SelectedPeriod.getId());
		System.out.println(SelectedPeriod.getStartDate()+" - "+Fecha+" - "+SelectedPeriod.getEndDate());
		System.out.println(FechaStart+" - "+FechaNow+" - "+FechaEnd);
		System.out.println(FechaStart.compareTo(FechaNow)+" <-> "+FechaNow.compareTo(FechaEnd));
		
		if((FechaStart.compareTo(FechaNow) <= 0) && (FechaNow.compareTo(FechaEnd) <= 0)) {
			//buscar los estudiantes por el corte de las materias
			userList = controller.getStudentList(this.getUserSession().getDefaultEnterprise(),Period,true);
		} else {
			//buscar los estudiantes por las notas finales del periodo
			userList = controller.getStudentList(this.getUserSession().getDefaultEnterprise(),Period,false);
		}
		showFindStudentList=true;
	}

	public void goShowInfo(Student zu) {
		Fecha = ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD);
		//setObservationList(controller.lodInfoObservationToShow(zu)); set para las notas del estudiante		
		setShowReportByStudent(true);
		setPanelView("showStudent", "titulo", "RStudentGradeBean");
		
	}
	
	public void goBack(){
		setShowStudentList(true);
		setPanelView("showStudent", "titulo", "RStudentGradeBean");
	}

	
	public List<Student> getUserList() {
		return userList;
	}

	public void setUserList(List<Student> userList) {
		this.userList = userList;
	}

	public RStudentGradesController getController() {
		return controller;
	}

	public void setController(RStudentGradesController controller) {
		this.controller = controller;
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

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

	public boolean isShowStudentList() {
		return showStudentList;
	}

	public void setShowStudentList(boolean showStudentList) {
		this.showStudentList = showStudentList;
		this.showReportByStudent = !showStudentList;
	}

	public boolean isShowReportByStudent() {
		return showReportByStudent;
	}

	public void setShowReportByStudent(boolean showReportByStudent) {
		this.showReportByStudent = showReportByStudent;
		this.showStudentList =!showReportByStudent;
	}

	public List<AcademicPeriod> getPeriodList() {
		return PeriodList;
	}

	public void setPeriodList(List<AcademicPeriod> periodList) {
		PeriodList = periodList;
	}

	public Long getPeriod() {
		return Period;
	}

	public void setPeriod(Long period) {
		Period = period;
	}

	public boolean isShowFindStudentList() {
		return showFindStudentList;
	}

	public void setShowFindStudentList(boolean showFindStudentList) {
		this.showFindStudentList = showFindStudentList;
	}

}