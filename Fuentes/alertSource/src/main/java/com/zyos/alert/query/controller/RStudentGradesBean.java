package com.zyos.alert.query.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.AcademicPeriod;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.controller.ZyosBackingBean;

@ManagedBean
@ViewScoped
@URLMapping(id = "rstudentgradesBean", pattern = "/portal/reporteAcademico", viewId ="/pages/studentReportGRA/report.jspx")
public class RStudentGradesBean extends ZyosBackingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean showStudentList = false;
	private boolean showTableList = false;
	private boolean showReportByStudent;
	
	private String FacultyNameList, DocentePAAINameList;

	private List<Student> userList;
	private List<Faculty> FacultiesList;
	private List<AcademicPeriod> PeriodList;
	
	private Student selectedStudent;
	private String Fecha="";
	private RStudentGradesController controller = new RStudentGradesController();

	public RStudentGradesBean() throws Exception {
		PeriodList = controller.getAcademicPeriodList();
		System.out.print("aqui");
		/*for (AcademicPeriod ap : PeriodList) {
			System.out.println("-----"+ap.getDescription());
		}*/
	}
	
	public void goShowPeriodInfo(String Periodo) throws Exception {
		userList = controller.getStudentList(this.getUserSession().getDefaultEnterprise());
		setShowTableList(true);
	}

	public void goShowInfo(Student zu) {
		try {
			//setObservationList(controller.lodInfoObservationToShow(zu)); set para las notas del estudiante		
			setshowReportByStudent(true);
			setPanelView("showStudent", "titulo", "RStudentGradeBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void goBack(){
		setShowStudentList(true);
		setPanelView("showStudent", "titulo", "RStudentGradeBean");
	}

	public boolean isShowUserList() {
		return showStudentList;
	}

	public void setShowStudentList(boolean showStudentList) {
		this.showStudentList = showStudentList;
		this.showReportByStudent = !showStudentList;
		this.showTableList = !showStudentList;
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

	public boolean isshowReportByStudent() {
		return showReportByStudent;
	}

	public void setshowReportByStudent(boolean showReportByStudent) {
		this.showReportByStudent = showReportByStudent;
		this.showStudentList = !showReportByStudent;
		this.showTableList = !showReportByStudent;
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

	public boolean isShowTableList() {
		return showTableList;
	}

	public void setShowTableList(boolean showTableList) {
		this.showTableList = showTableList;
	}

	public List<AcademicPeriod> getPeriodList() {
		return PeriodList;
	}

	public void setPeriodList(List<AcademicPeriod> periodList) {
		PeriodList = periodList;
	}

}