package com.zyos.alert.studentReport.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.zyos.alert.absent.model.DayClass;
import com.zyos.alert.calification.model.CalificationItem;
import com.zyos.alert.calification.model.Evaluation;
import com.zyos.alert.monitorstudentsubject.controller.MonitorStudentSubjectController;
import com.zyos.alert.monitorstudentsubject.model.MonitorStudentSubject;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {@URLMapping(id = "subjectBean", pattern = "/portal/asignaturas", viewId = "/pages/subject/subject.jspx"),
	@URLMapping(id = "subjectBeanInClass", pattern = "/portal/reporteEnClase", viewId = "/pages/reportStudentInClass/reportStudentInClass.jspx")})
public class SubjectBean extends ZyosBackingBean implements Serializable {

	private SubjectController controller;
	private MonitorStudentSubjectController controllerMonitor;
	private CalificationBean calificationBean;
	private AssistanceBean assistanceBean;
	private ReportBean reportBean;
	private HistoricalBean historicalBean;
	private CalificationItem calificationItem;
	private Long activeIndex;
	private int monitorActive = 0;
	private int idCategory = 0;

	private ReportStudent reportStudent;
	private Subject subjectSelected;
	private Student studentSelected;
	private Student studentToAsignedMonitor;
	private Student studentCurrent;
	private MonitorStudentSubject monitorStudent;
	private DayClass dayClass;
	private Evaluation evaluation;
	private Evaluation evaluationSelected;
	private Evaluation evaluationToEdit;

	private boolean showCalification, showReport, showHistorical, showAssistance, showMain, showCalificationItem, showObservation,
		showDeleteMonitorButton;

	private String nameMonitor;

	private List<Subject> subjectList;
	private List<Student> studentList;
	private List<DayClass> dayClassList;
	private List<DayClass> dayClassListTeacher;
	private List<DayClass> dayClassListTeacherTmp;
	private List<RiskFactor> riskFactorListByCategory;

	public SubjectBean() throws Exception {
		try {
			if (getValidateUserRole()) {				
				controller = new SubjectController();
				controllerMonitor = new MonitorStudentSubjectController();			
				loadSubjectListByAcademicPeriod();

				showMain = true;
				reportStudent = new ReportStudent();
				evaluation = new Evaluation();
				studentToAsignedMonitor = new Student();
				calificationItem = new CalificationItem();
				showObservation = false;
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public boolean getValidateUserRole() throws Exception {
		try {
			if (getUserSession().getDefaultGroup().equals(IZyosGroup.TEACHER) || getUserSession().getDefaultGroup().equals(IZyosGroup.STUDENT)
				|| getUserSession().getDefaultGroup().equals(IZyosGroup.CLASS_MATE)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Expired session - redirect login");
			return false;
		}
	}

	/** @autor jhernandez */
	public void loadSubjectListByAcademicPeriod() {
		try {
			subjectList =
				controller.loadSubjectListByAcademicPeriod(getUserSession().getId(), ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD),
					getUserSession().getDefaultGroup());
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void loadMonitorStudentSubject() {
		try {

			monitorStudent = controllerMonitor.validateMoreOneMonitor(subjectSelected);

			if (monitorStudent == null) {
				nameMonitor = "Sin asistente asignado.";
				showDeleteMonitorButton = false;
			} else {
				nameMonitor = monitorStudent.getName() + " " + monitorStudent.getLastName();
				showDeleteMonitorButton = true;
			}
			update("subjectForm:subjectMain", "subjectForm:subjectDetail");
		} catch (Exception e) {

			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void validateMonitor(Student studentCurrent) {
		try {

			if (monitorStudent != null) {
				if (monitorStudent.getIdStudentSubject().equals(studentCurrent.getIdStudentSubject())) {
					monitorActive = 1;
				} else {
					monitorActive = 0;
				}
			} else {
				monitorActive = 0;
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void handleLinkChange() {
		try {
			cleanView();
			switch (activeIndex.intValue()) {
				case 0:
					// Assist
					showAssistance = true;
					assistanceBean = new AssistanceBean(controller, subjectSelected, getUserSession(), dayClassList, dayClassListTeacherTmp);
					assistanceBean.clearStudentSelected();

				break;
				case 1:
					// Report
					showReport = true;
					if (reportBean == null) {

						reportBean = new ReportBean(controller, subjectSelected);
						reportBean = new ReportBean();
						assistanceBean.clearStudentSelected();

					} else {
						reportBean.setSubjectSelected(subjectSelected);
						reportBean.setController(controller);
					}

				break;
				case 2:
					// Historical
					assistanceBean = new AssistanceBean();
					historicalBean = new HistoricalBean(controller, subjectSelected, studentList, getUserSession());
					// assistanceBean.clearStudentSelected();

					showHistorical = true;
				break;
				case 3:
					// Calification
					evaluation = new Evaluation();
					calificationItem = new CalificationItem();
					calificationBean =
						new CalificationBean(controller, subjectSelected, getUserSession(), evaluation, evaluationToEdit, calificationItem);

					assistanceBean.clearStudentSelected();

					showCalification = true;
				break;
				default:
				break;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public static int getDayOfTheWeek(Date d) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public void deleteMonitorStudent() {
		try {
			monitorStudent = controllerMonitor.validateMoreOneMonitor(subjectSelected);
			controllerMonitor.deleteMonitorStudent(monitorStudent, getUserSession().getDocumentNumber());
			nameMonitor = "Sin asistente asignado.";
			showDeleteMonitorButton = false;
			goSubjectDetail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** @autor jhernandez */
	public void goSubjectDetail() {
		try {

			studentList = controller.loadStudentBySubjectList(this.subjectSelected.getIdSubject(), this.subjectSelected.getIdGroupSubject(), ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
			
			if (studentList == null || studentList.size() == 0) {
				ZyosBackingBean.addWarn("Listado Estudiantes ", "No existen estudiantes inscritos en la asignatura seleccionada.");
				return;
			}
			
			loadMonitorStudentSubject();
			Date dateToday = Calendar.getInstance().getTime();
			String today = ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD);
			int currentDay = getDayOfTheWeek(dateToday);			

			if (getUserSession().getDefaultGroup().equals(IZyosGroup.STUDENT) || getUserSession().getDefaultGroup().equals(IZyosGroup.CLASS_MATE)) {
								
				studentCurrent = new Student();
				studentCurrent = controller.loadStudentCurrentActive(getUserSession().getId(), subjectSelected);
				validateMonitor(studentCurrent);
				dayClassList = new ArrayList<DayClass>();
				
				dayClassList = controller.loadDayClassList(studentCurrent.getIdStudent(), subjectSelected.getIdSubject(), subjectSelected.getIdGroupSubject(), today);
				
				if (dayClassList.size() != 0) {

					setStringDay(dayClassList);
				}

				showMain = false;
				activeIndex = 0L;
				handleLinkChange();
				assistanceBean.clearStudentSelected();
			}

			if (getUserSession().getDefaultGroup().equals(IZyosGroup.TEACHER)) {

				dayClassListTeacher = new ArrayList<DayClass>();
				dayClassListTeacher = controller.loadDayClassListTeacher(subjectSelected.getIdSubject(), subjectSelected.getIdGroupSubject(), today);
				dayClassListTeacherTmp = new ArrayList<DayClass>();
				
				if (dayClassListTeacher.size() != 0) {
					
					Long idStudentForTeacher = dayClassListTeacher.get(0).getIdStudent();

					dayClassListTeacherTmp = controller.loadDayClassList(idStudentForTeacher, subjectSelected.getIdSubject(), subjectSelected.getIdGroupSubject(), today);

					dayClassListTeacherTmp = setStringDay(dayClassListTeacherTmp);
				}

				showMain = false;
				activeIndex = 0L;
				handleLinkChange();
				assistanceBean.clearStudentSelected();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	
	public List<DayClass> setStringDay (List <DayClass> dayClassStudentList)
	{
		for (DayClass d : dayClassStudentList) {

			if (d.getIdDayCalendar() == 2) {
				String stringDay = "Lunes";

				d.setDayClass(stringDay);
			} else if (d.getIdDayCalendar() == 3) {
				String stringDay = "Martes";

				d.setDayClass(stringDay);
			} else if (d.getIdDayCalendar() == 4) {
				String stringDay = "Miercoles";

				d.setDayClass(stringDay);
			} else if (d.getIdDayCalendar() == 5) {
				String stringDay = "Jueves";

				d.setDayClass(stringDay);
			} else if (d.getIdDayCalendar() == 6) {
				String stringDay = "Viernes";

				d.setDayClass(stringDay);
			} else if (d.getIdDayCalendar() == 7) {
				String stringDay = "Sabado";

				d.setDayClass(stringDay);
			} else if (d.getIdDayCalendar() == 1) {
				String stringDay = "Domingo";
				d.setDayClass(stringDay);
			}
		}
		return dayClassStudentList;
		
		
	}
	

	/** @autor jhernandez */
	public void goReportStudent() {
		try {
			showMain = false;
			showObservation = false;

			activeIndex = 1L;
			handleLinkChange();
			reportBean = new ReportBean();
			assistanceBean.clearStudentSelected();

			getRequestContext().update("subjectForm:subjectDetail");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void saveReportStudent() {
		try {
			reportBean = new ReportBean();

			if (reportStudent.getIdRiskFactor() == 0) {
				ZyosBackingBean.addWarn("Reportar Estudiante", "Debe seleccionar por lo menos un factor de riesgo.");
				return;
			} else {
				reportBean.saveReportStudent(reportStudent, studentSelected, getUserSession(), controller);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void goBackSubjectMain() {
		try {
			showMain = true;
			cleanView();
			update("subjectForm:subjectMain", "subjectForm:subjectDetail");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void goBackToAssistance() {
		try {
			cleanView();
			showMain = false;
			showAssistance = true;
			reportBean.clearDataForm();

			update("subjectForm:subjectMain", "subjectForm:subjectDetail");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void goCalificationItem(Evaluation evaluationSelected) {
		try {

			calificationBean = new CalificationBean();
			this.evaluationSelected = evaluationSelected;

			cleanView();
			showMain = false;
			showCalificationItem = true;

			calificationBean.loadStudentCalificationItemList(evaluationSelected);

			update("subjectForm:subjectMain", "subjectForm:subjectDetail");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void goRegisterCalification() {
		try {
			// calificationBean = new CalificationBean();
			if (calificationItem.getName() == null || calificationItem.getPercentage() == 0) {
				return;
			} else {

				calificationBean.registerCalificationItem(calificationItem, getUserSession().getDocumentNumber(), evaluationSelected);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void updateCalification() {
		try {
			calificationBean.updateCalification(getUserSession());
		} catch (Exception e) {

		}
	}

	/** @autor jhernandez */
	public void generateAlert() {
		try {
			calificationBean.generateAutomaticAlert(getUserSession());
		} catch (Exception e) {

		}
	}

	/** @autor jhernandez */
	public void deleteCalificationitem() {
		try {
			calificationBean.deleteCalificationItem(getUserSession());

		} catch (Exception e) {

		}
	}

	/** @autor jhernandez */
	public void goAsignedMonitorDialog() {
		try {
			ZyosBackingBean.update("subjectForm:monitorStudentDialog");
			ZyosBackingBean.getRequestContext().execute("monitorStudentDialog.show();");

		} catch (Exception e) {
		}
	}


	public void asignedMonitor() {
		try {

			if (studentToAsignedMonitor == null) {
				ZyosBackingBean.addError("Asignar asistente de clase", "Error al intentar asignar el estudiante asistente de clase.");
			} else {
				if (monitorStudent == null) {
					controllerMonitor.saveMonitorStudentSubject(subjectSelected, studentToAsignedMonitor, getUserSession().getDocumentNumber());
					ZyosBackingBean.addInfo("Asignar asistente de clase", "El estudiante ha sido asignado como asistente de clase con éxito!");
					showDeleteMonitorButton = true;
					goSubjectDetail();

				} else {
					controllerMonitor.saveAndDeleteMonitorStudentSubject(monitorStudent, subjectSelected, studentToAsignedMonitor, getUserSession()
						.getDocumentNumber());
					ZyosBackingBean.addInfo("Asignar asistente de clase", "El estudiante ha sido asignado como asistente de clase con éxito!");
					goSubjectDetail();
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void cleanView() {
		showAssistance = false;
		showCalification = false;
		showHistorical = false;
		showReport = false;
		showCalificationItem = false;
	}

	/** @author MTorres 19/06/2014 9:21:01 */
	private void loadTitleRiskList() throws Exception {
		try {
			List<String> titleList = new ArrayList<String>(riskFactorListByCategory.size());
			for (RiskFactor rf : riskFactorListByCategory) {
				titleList.add(rf.getDescription() + "¥");
			}
			ZyosBackingBean.getRequestContext().execute("addReportToolTip('" + titleList + "');");
		} catch (Exception e) {
			throw e;
		}
	}

	/** jhernandez */
	public void loadRiskFactorListByCategory() {
		try {
			showObservation = false;
			riskFactorListByCategory = controller.loadRiskFactorListByCategory(idCategory);
			loadTitleRiskList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** jhernandez */
	public void showObservation() {
		try {
			showObservation = true;
			loadTitleRiskList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public CalificationBean getCalificationBean() {
		return calificationBean;
	}

	public void setCalificationBean(CalificationBean calificationBean) {
		this.calificationBean = calificationBean;
	}

	public AssistanceBean getAssistanceBean() {
		return assistanceBean;
	}

	public void setAssitanceBean(AssistanceBean assistanceBean) {
		this.assistanceBean = assistanceBean;
	}

	public ReportBean getReportBean() {
		return reportBean;
	}

	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}

	public HistoricalBean getHistoricalBean() {
		return historicalBean;
	}

	public void setHistoricalBean(HistoricalBean historicalBean) {
		this.historicalBean = historicalBean;
	}

	public Long getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(Long activeIndex) {
		this.activeIndex = activeIndex;
	}

	public Subject getSubjectSelected() {
		return subjectSelected;
	}

	public void setSubjectSelected(Subject subjectSelected) {
		this.subjectSelected = subjectSelected;
	}

	public boolean isShowCalification() {
		return showCalification;
	}

	public void setShowCalification(boolean showCalification) {
		this.showCalification = showCalification;
	}

	public boolean isShowReport() {
		return showReport;
	}

	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}

	public boolean isShowHistorical() {
		return showHistorical;
	}

	public void setShowHistorical(boolean showHistorical) {
		this.showHistorical = showHistorical;
	}

	public boolean isShowAssistance() {
		return showAssistance;
	}

	public void setShowAssistance(boolean showAssistance) {
		this.showAssistance = showAssistance;
	}

	public boolean isShowMain() {
		return showMain;
	}

	public void setShowMain(boolean showMain) {
		this.showMain = showMain;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public SubjectController getController() {
		return controller;
	}

	public void setController(SubjectController controller) {
		this.controller = controller;
	}

	public void setAssistanceBean(AssistanceBean assistanceBean) {
		this.assistanceBean = assistanceBean;
	}

	public Student getStudentSelected() {
		return studentSelected;
	}

	public void setStudentSelected(Student studentSelected) {
		this.studentSelected = studentSelected;
	}

	public ReportStudent getReportStudent() {
		return reportStudent;
	}

	public void setReportStudent(ReportStudent reportStudent) {
		this.reportStudent = reportStudent;
	}

	public Student getStudentCurrent() {
		return studentCurrent;
	}

	public void setStudentCurrent(Student studentCurrent) {
		this.studentCurrent = studentCurrent;
	}

	public DayClass getDayClass() {
		return dayClass;
	}

	public void setDayClass(DayClass dayClass) {
		this.dayClass = dayClass;
	}

	public List<DayClass> getDayClassList() {
		return dayClassList;
	}

	public void setDayClassList(List<DayClass> dayClassList) {
		this.dayClassList = dayClassList;
	}

	public List<DayClass> getDayClassListTeacher() {
		return dayClassListTeacher;
	}

	public void setDayClassListTeacher(List<DayClass> dayClassListTeacher) {
		this.dayClassListTeacher = dayClassListTeacher;
	}

	public List<DayClass> getDayClassListTeacherTmp() {
		return dayClassListTeacherTmp;
	}

	public void setDayClassListTeacherTmp(List<DayClass> dayClassListTeacherTmp) {
		this.dayClassListTeacherTmp = dayClassListTeacherTmp;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Evaluation getEvaluationToEdit() {
		return evaluationToEdit;
	}

	public void setEvaluationToEdit(Evaluation evaluationToEdit) {
		this.evaluationToEdit = evaluationToEdit;
	}

	public boolean isShowCalificationItem() {
		return showCalificationItem;
	}

	public CalificationItem getCalificationItem() {
		return calificationItem;
	}

	public void setCalificationItem(CalificationItem calificationItem) {
		this.calificationItem = calificationItem;
	}

	public void setShowCalificationItem(boolean showCalificationItem) {
		this.showCalificationItem = showCalificationItem;
	}

	public Evaluation getEvaluationSelected() {
		return evaluationSelected;
	}

	public void setEvaluationSelected(Evaluation evaluationSelected) {
		this.evaluationSelected = evaluationSelected;
	}

	public Student getStudentToAsignedMonitor() {
		return studentToAsignedMonitor;
	}

	public void setStudentToAsignedMonitor(Student studentToAsignedMonitor) {
		this.studentToAsignedMonitor = studentToAsignedMonitor;
	}

	public MonitorStudentSubjectController getControllerMonitor() {
		return controllerMonitor;
	}

	public void setControllerMonitor(MonitorStudentSubjectController controllerMonitor) {
		this.controllerMonitor = controllerMonitor;
	}

	public MonitorStudentSubject getMonitorStudent() {
		return monitorStudent;
	}

	public void setMonitorStudent(MonitorStudentSubject monitorStudent) {
		this.monitorStudent = monitorStudent;
	}

	public String getNameMonitor() {
		return nameMonitor;
	}

	public void setNameMonitor(String nameMonitor) {
		this.nameMonitor = nameMonitor;
	}

	public int getMonitorActive() {
		return monitorActive;
	}

	public void setMonitorActive(int monitorActive) {
		this.monitorActive = monitorActive;
	}

	public boolean isShowObservation() {
		return showObservation;
	}

	public void setShowObservation(boolean showObservation) {
		this.showObservation = showObservation;
	}

	public List<RiskFactor> getRiskFactorListByCategory() {
		return riskFactorListByCategory;
	}

	public void setRiskFactorListByCategory(List<RiskFactor> riskFactorListByCategory) {
		this.riskFactorListByCategory = riskFactorListByCategory;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public boolean isShowDeleteMonitorButton() {
		return showDeleteMonitorButton;
	}

	public void setShowDeleteMonitorButton(boolean showDeleteMonitorButton) {
		this.showDeleteMonitorButton = showDeleteMonitorButton;
	}



}
