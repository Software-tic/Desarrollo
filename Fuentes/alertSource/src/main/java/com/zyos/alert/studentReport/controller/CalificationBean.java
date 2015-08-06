package com.zyos.alert.studentReport.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderFormatting;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.zyos.alert.calification.model.CalificationItem;
import com.zyos.alert.calification.model.Evaluation;
import com.zyos.alert.controlPanel.controller.ControlPanelController;
import com.zyos.alert.controlPanel.model.ControlPanel;
import com.zyos.alert.executionsHistorical.api.IExecutionHistorialType;
import com.zyos.alert.executionsHistorical.controller.ExecutionsHistoricalController;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IRiskFactor;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;
import com.zyos.session.common.User;

public class CalificationBean {

	private double currentPercentage;
	private double currentPercentageCalification;
	private int fullPercentage;
	private int fullPercentageCalification;
	private int numberCalification;

	private List<Student> studentList;
	private List<Evaluation> evaluationList;

	private SubjectController controller;
	private Subject subjectSelected;
	private User userSession;
	private ControlPanel controlPanelRisk;
	private ControlPanelController controllerPanelControl;
	private StudentReportController studentReportController;
	private ReportStudentController controllerReport;
	private ExecutionsHistorical execution;
	private ExecutionsHistoricalController executionController;	
	private Evaluation evaluation;
	private Evaluation evaluationToEdit;
	private Evaluation evaluationToDelete;
	private Evaluation evaluationSelected;
	private CalificationItem calificationItem;
	private CalificationItem calificationItemToDelete;
	private CalificationItem calification;
	private List<Student> studentCalificationItemList;
	private List<CalificationItem> calificationItemNameList;
	private List<Double> finalCalificationList;
	List<Long> idsCalificationItem;

	public CalificationBean() {

	}

	public CalificationBean(SubjectController controller,
			Subject subjectSelected, User userSession, Evaluation evaluation,
			Evaluation evaluationToEdit, CalificationItem calificationItem) {
		try {
			this.controller = controller;
			this.subjectSelected = subjectSelected;
			this.userSession = userSession;
			this.evaluation = evaluation;
			this.calificationItem = calificationItem;
			loadEvaluationList();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void loadEvaluationList() throws Exception {
		try {
			evaluationList = controller.loadEvaluationList(
					this.subjectSelected.getIdSubject(),
					this.subjectSelected.getIdGroupSubject());

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void addEvaluationItem() {
		try {
			double Percentage = 0;
			currentPercentage = 0;

			if (evaluationList != null || !evaluationList.isEmpty()) {
				for (Evaluation e : evaluationList) {
					Percentage += e.getPercentage();
				}
			}
			double percentageTotal = 100;
			currentPercentage = percentageTotal - Percentage;

			if (currentPercentage == 0) {
				fullPercentage = 1;
			} else {
				fullPercentage = 0;
			}
			ZyosBackingBean.getRequestContext().execute(
					"dialogAddEvaluationItem.show();");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void editEvaluationItem(Evaluation evaluation) {

		try {
			evaluationToEdit = (Evaluation) evaluation.clone();
			double Percentage = 0;
			currentPercentage = 0;

			for (Evaluation e : evaluationList) {

				Percentage += e.getPercentage();
			}
			double percentageTotal = 100;
			currentPercentage = percentageTotal - Percentage;

			if (currentPercentage == 0) {
				fullPercentage = 1;

			} else {
				fullPercentage = 0;
			}

			ZyosBackingBean.getRequestContext().execute(
					"dialogEditEvaluationItem.show();");
			ZyosBackingBean.update("subjectForm:dialogEditEvaluationItem");
		} catch (CloneNotSupportedException e) {

			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void editEvaluation() throws Exception {
		try {

			if (evaluationToEdit.getName().isEmpty()
					|| evaluationToEdit.getPercentage() == 0) {
			} else {

				double validatePercentage = 0;
				currentPercentage = 0;

				for (Evaluation e : evaluationList) {

					if (!e.getIdEvaluation().equals(
							evaluationToEdit.getIdEvaluation())) {
						validatePercentage += e.getPercentage();
					}
				}
				if ((validatePercentage + evaluationToEdit.getPercentage() > 100)) {

					ZyosBackingBean
							.addWarn("Editar Corte",
									"el porcentaje que desea asignar supera el porcentaje disponible a asignar");
					return;
				}
				else {

					controller.updateEvaluationItem(evaluationToEdit,
							evaluationToEdit.getIdEvaluation(),
							subjectSelected.getIdSubject(),
							subjectSelected.getIdGroupSubject(),
							userSession.getDocumentNumber());

					loadEvaluationList();
					cleanDataAddEvaluationItem();
					ZyosBackingBean.addInfo("Editar Corte",
							"Corte Actualizado con éxito!");
					ZyosBackingBean
							.update("subjectForm:dialogEditEvaluationItem");
				}
			}

		} catch (CloneNotSupportedException e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void registerEvaluationItem() throws Exception {
		try {

			if (evaluation.getName().isEmpty()
					|| evaluation.getPercentage() == 0) {

				ZyosBackingBean.addWarn("Crear Corte",
						" No pueden existir campos vacios.");
				return;

			} else {

				if (evaluation.getPercentage() > currentPercentage) {

					ZyosBackingBean
							.addWarn("Crear Corte",
									" El porcentaje asignado supera el porcentaje disponible para asignar.");
					return;

				} else {

					controller.saveEvaluationItem(evaluation, subjectSelected,
							userSession.getDocumentNumber());
					ZyosBackingBean.addInfo("Registrar Corte",
							"Corte creado con éxito!");
					loadEvaluationList();
					cleanDataAddEvaluationItem();
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void cleanDataAddEvaluationItem() {
		try {

			evaluation = new Evaluation();
			evaluationToEdit = new Evaluation();

			SubjectBean bean = (SubjectBean) ZyosBackingBean
					.getBean("subjectBean");
			if (bean != null) {
				bean.setEvaluation(evaluation);
			}
			ZyosBackingBean.update("subjectForm:dialogAddEvaluationItem");
			ZyosBackingBean.update("subjectForm:calificationMainPanel");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void cleanDataAddCalificationItem() {
		try {

			calificationItem = new CalificationItem();

			SubjectBean bean = (SubjectBean) ZyosBackingBean
					.getBean("subjectBean");
			if (bean != null) {
				bean.setCalificationItem(calificationItem);
			}

			// ZyosBackingBean.update("subjectForm:dialogAddCalificationItem");
			// ZyosBackingBean.update("subjectForm:dataTableCalification");
			ZyosBackingBean.update("subjectForm:subjectMain",
					"subjectForm:subjectDetail");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void deleteEvaluationItem() throws Exception {
		try {
			if (evaluationToDelete == null) {

				ZyosBackingBean.addWarn("Eliminar Corte",
						" Error al intentar eliminar corte seleccionado");
			} else {

				List<Long> calificationItemIdsToDelete = new ArrayList<Long>();
				calificationItemIdsToDelete = controller
						.loadCalificationItemIdsToDelete(evaluationToDelete);

				controller.deleteCalifications(evaluationToDelete,
						userSession.getDocumentNumber(),
						calificationItemIdsToDelete);

				ZyosBackingBean.addInfo("Eliminar Corte",
						"Corte Eliminado Con Éxito!");
				loadEvaluationList();
				cleanDataAddEvaluationItem();

				SubjectBean bean = (SubjectBean) ZyosBackingBean
						.getBean("subjectBean");
				if (bean != null) {
					bean.setEvaluation(evaluation);
				}
				ZyosBackingBean.update("subjectForm:dialogAddEvaluationItem");
				ZyosBackingBean.update("subjectForm:calificationMainPanel");

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void deleteCalificationItem(User user) throws Exception {
		try {
			if (calificationItemToDelete == null) {
				ZyosBackingBean.addWarn("Eliminar Nota",
						" Error al intentar eliminar Nota seleccionada");
			} else {

				controller.deleteCalificationItem(calificationItemToDelete,
						user.getDocumentNumber());

				loadStudentCalificationItemList(evaluationSelected);
				ZyosBackingBean.addInfo("Eliminar Nota",
						"Nota Eliminada Con Éxito!");

				ZyosBackingBean.update("subjectForm:dataTableCalification");
				ZyosBackingBean
						.update("subjectForm:dataEditTableCalificationItem");

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void cleanDataEditEvaluationItem() {
		try {

			evaluation = new Evaluation();
			SubjectBean bean = (SubjectBean) ZyosBackingBean
					.getBean("subjectBean");
			if (bean != null) {
				bean.setEvaluation(evaluation);
			}
			ZyosBackingBean.update("subjectForm:dialogEditEvaluationItem");
			ZyosBackingBean.update("subjectForm:calificationMainPanel");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void cleanAllData() {

		evaluation = new Evaluation();
		evaluationToEdit = new Evaluation();

	}

	public void loadStudentCalificationItemList(Evaluation evaluationSelected) {
		try {
			if (evaluationSelected == null) {
				return;
			}	

			// studentCalificationItemList = new ArrayList<Student>();
			// calificationItemNameList = new ArrayList<CalificationItem>();
			List<Double> studentGrade = new ArrayList<Double>();

			controller = new SubjectController();
			this.evaluationSelected = evaluationSelected;

			numberCalification = controller
					.loadNumberCalificationItems(evaluationSelected);

			idsCalificationItem = controller
					.loadIdsCalificationItems(evaluationSelected);

			studentCalificationItemList = controller
					.loadStudentCalificationItemList(evaluationSelected);

			calificationItemNameList = controller
					.loadCalificationNameList(evaluationSelected);

			for (Student s : studentCalificationItemList) {
				studentGrade = new ArrayList<Double>();
				Double studentsG[] = new Double[numberCalification];
				List<Long> idCalificationList = new ArrayList<Long>();
				
				String[] grades = s.getGrade().split(",");
				
				for (int i = 0; i < grades.length; i++) {
					studentsG[i] = Double.parseDouble(grades[i].trim());
				}

				s.setStudentGrade(studentsG);

				String[] idCalifications = s.getIdCalificationItemStudent()
						.split(",");

				for (int j = 0; j < idCalifications.length; j++) {
					Long id = Long.parseLong(idCalifications[j]);

					idCalificationList.add(j, id);
				}
				s.setIdCalificationItemSudent(idCalificationList);

			}

			if (studentCalificationItemList.size() > 0
					&& calificationItemNameList.size() > 0) {
				calculateFinalCalification(studentCalificationItemList,
						calificationItemNameList);
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void calculateFinalCalification(
			List<Student> studentCalificationItemList,
			List<CalificationItem> calificationItemNameList) {
		finalCalificationList = new ArrayList<Double>();

		double note = 0;

		for (Student s : studentCalificationItemList) {
			double finalCalification = 0;
			for (int i = 0; i < s.getStudentGrade().length; i++) {
				CalificationItem c = calificationItemNameList.get(i);
				note = s.getStudentGrade()[i] * c.getPercentage()
						/ evaluationSelected.getPercentage();
				finalCalification += note;
			}

			s.setFinalCalificationStudent(finalCalification);
		}
	}

	public void addCalificationItem() {
		try {

			double percent = 0;
			currentPercentageCalification = 0;

			if (calificationItemNameList != null
					|| !calificationItemNameList.isEmpty()) {
				for (CalificationItem c : calificationItemNameList) {
					percent += c.getPercentage();
				}
			}

			double percentageTotal = evaluationSelected.getPercentage();
			currentPercentageCalification = percentageTotal - percent;

			if (currentPercentageCalification == 0) {
				fullPercentageCalification = 1;

			} else {
				fullPercentageCalification = 0;
			}

			ZyosBackingBean.getRequestContext().execute(
					"dialogAddCalificationItem.show();");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void editCalificationItem() {
		try {

			calificationItemNameList = controller
					.loadCalificationNameList(evaluationSelected);

			ZyosBackingBean.update("subjectForm:dataEditTableCalificationItem");
			ZyosBackingBean.getRequestContext().execute(
					"dialogEditCalificationItem.show();");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void registerCalificationItem(CalificationItem calification,
			String documentNumber, Evaluation evaluationSelected) {
		try {

			double percent = 0;

			List<CalificationItem> percentages = getCalificationItemNameList();

			if (percentages.size() > 0) {

				for (CalificationItem c : percentages) {
					percent += c.getPercentage();
				}
			}

			if (percent + calification.getPercentage() > evaluationSelected
					.getPercentage()) {
				ZyosBackingBean
						.addWarn("Crear Nota",
								"El porcentaje que desea asignar supera el porcentaje disponible a asignar.");
			} else {

				SubjectBean subjectBean = (SubjectBean) ZyosBackingBean
						.getBean("subjectBean");

				AssistanceBean assistance = subjectBean.getAssistanceBean();

				List<Student> studentList = assistance.getStudentList();

				controller = new SubjectController();

				controller.saveCalificationItem(evaluationSelected,
						calification, documentNumber, studentList);
				cleanDataAddCalificationItem();
				loadStudentCalificationItemList(evaluationSelected);
				ZyosBackingBean.addInfo("Crear Nota",
						"Columna de Nota creada con éxito!");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void updateCalification(User s) {
		try {

			controller = new SubjectController();
			controller.updateCalification(studentCalificationItemList,
					s.getDocumentNumber());
			loadStudentCalificationItemList(evaluationSelected);
			ZyosBackingBean.addInfo("Actualizar Notas",
					"Notas Actualizadas con éxito!");
			ZyosBackingBean.update("subjectForm:dataTableCalification");			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	public void generateAutomaticAlert(User user) {
		try {				
			Long start = System.currentTimeMillis();
			execution = new ExecutionsHistorical();
			executionController = new ExecutionsHistoricalController();
			controllerPanelControl = new ControlPanelController();
			controlPanelRisk = controllerPanelControl
					.loadCurrentPercentageRisk();
			double percentageAccumulated;
			int count = 0;
			int reportStudentNumber = 0;
			int percentageTotal = 100;
			double minGrade = 3.0;

			Double studentsG[] = new Double[numberCalification];

			for (Student s : studentCalificationItemList) {
				String[] studentGrades = s.getGrade().split(",");

				for (int i = 0; i < studentGrades.length; i++) {

					if (Double.parseDouble(studentGrades[i].trim()) < minGrade) {
						count++;
					}
				}

				percentageAccumulated = (((count * percentageTotal) / numberCalification));
				
					if (percentageAccumulated >= controlPanelRisk
							.getPercentageRiskFactor()) {
						boolean isReport = saveReportStudent(s.getIdStudent(), user);
											
							if (isReport == true) {
								reportStudentNumber++;
								isReport = false;
							}						
					}
					count = 0;
				}
				

				Long end = System.currentTimeMillis();

				Long finaltime = end - start;
				Date executeTime = parseDate(finaltime);
				String execute = ManageDate.formatDate(
						executeTime, ManageDate.HH_MM_SS);

				execution.setExecutionTime(execute);
				execution.setIdRiskFactor(IRiskFactor.BAD_CALIFICATION);
				execution.setReportStudentNumber(reportStudentNumber);
				execution.setIdExecutionHistoricalType(IExecutionHistorialType.SAC);
				execution.setInformation("Se ha generado una ejecución de análisis de notas estudiantes para generar una alerta automatica por bajo desempeño academico. ");
				executionController.saveExecutionsHistorical(
						user.getDocumentNumber(),
						execution);					
				
				ZyosBackingBean
				.addInfo("Alerta Temprana",
						"A los estudiantes que presentarón bajas calificaciones se les generó una alerta temprana por su bajo desempeño academico. ");
							
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** @autor jhernandez */
	private Date parseDate(Long milisecond) throws Exception {
		try {
			if (milisecond == null)
				return ManageDate.formatDate("00:00:00", ManageDate.HH_MM_SS);
			int seconds = milisecond.intValue() / 1000;
			int h = seconds / 3600;
			int m = (seconds % 3600) / 60;
			int s = ((seconds % 3600) % 60);

			return ManageDate.formatDate((h > 9 ? "" : "0") + h + ":"
					+ (m > 9 ? "" : "0") + m + ":" + (s > 9 ? "" : "0") + s,
					ManageDate.HH_MM_SS);
		} catch (Exception e) {
			throw e;
		}
	}

	/** @autor jhernandez */
	public boolean saveReportStudent(Long idStudentToReport, User user)
			throws Exception {
		try {

			controllerReport = new ReportStudentController();
			ReportStudent reportDuplicate = new ReportStudent();
			ReportStudent reportStudent = new ReportStudent();

			reportDuplicate = controllerReport.validateReportDuplicate(
					idStudentToReport, IRiskFactor.BAD_CALIFICATION);

			if (reportDuplicate == null) {

				studentReportController = new StudentReportController();
				Long idStudentDegree = studentReportController
						.loadIdStudentDegree(idStudentToReport);

				Long idAdviser = studentReportController
						.loadIdAdviser(idStudentDegree);

				reportStudent.setIdStudent(BigDecimal.valueOf(idStudentToReport));
				reportStudent.setIdReportType(IReportType.AUTOMATIC);
				reportStudent.setIdSolicitor(user.getId());
				reportStudent.setIdRiskFactor(IRiskFactor.BAD_CALIFICATION);

				reportStudent
						.setIdStatusReportStudent(IStatusReportStudent.REPORT);
				reportStudent.setIdZyosUserAdviserFaculty(idAdviser);
				// reportStudent.setIdStage(IStage.UDIES);
				// reportStudent.setIdAdviser(idAdviser);
				reportStudent.setFirstIntervention(Long.valueOf(0));
				String detailReport = "Este reporte es generado automáticamente al estudiante por presentar bajo rendimiento en calificaciones";
				reportStudent.setDetailReport(detailReport);

				controller.saveReportStudent(reportStudent, user.getDocumentNumber());
				controller.saveRiskFactor(reportStudent, user.getDocumentNumber());
				controller.saveReportStudentObservation(reportStudent, user.getDocumentNumber());

				System.out.println("reportado con exito");
				
				return true;
			}
			return false;
		} catch (Exception e) {
		e.printStackTrace();
		return false;
		}

	}

	/** @author mtorres */
	public void handleDataExporter(Object o) {
		try {
			Workbook wb = (Workbook) o;
			wb.setSheetName(0, "Reporte Corte");
			CellStyle cs = wb.createCellStyle();
			cs.setBorderBottom(BorderFormatting.BORDER_THIN);
			cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cs.setBorderTop(BorderFormatting.BORDER_THIN);
			cs.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cs.setBorderLeft(BorderFormatting.BORDER_THIN);
			cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cs.setBorderRight(BorderFormatting.BORDER_THIN);
			cs.setRightBorderColor(IndexedColors.BLACK.getIndex());

			CellStyle hs = wb.createCellStyle();
			hs.cloneStyleFrom(cs);
			hs.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			hs.setFillPattern(CellStyle.SOLID_FOREGROUND);

			List<String> labelTableList = new ArrayList<String>(4);
			labelTableList.add(ZyosBackingBean
					.getMessage("page.student.labelStudent"));
			labelTableList.add(ZyosBackingBean
					.getMessage("page.calificationItem.labelDocument"));
			labelTableList.add(ZyosBackingBean
					.getMessage("page.student.labelStudentFinalNote"));
			labelTableList.add(ZyosBackingBean
					.getMessage("page.student.labelStudentAbsent"));

			for (Row r : wb.getSheetAt(0)) {
				int i = 0;
				for (Cell c : r) {
					if (c.getRowIndex() == 0) {
						{
							c.setCellValue(labelTableList.get(i));
							c.setCellStyle(hs);
							i++;
						}
					} else
						c.setCellStyle(cs);
				}
			}
			wb.getSheetAt(0).autoSizeColumn(0);
			wb.getSheetAt(0).autoSizeColumn(1);
			wb.getSheetAt(0).autoSizeColumn(2);
			wb.getSheetAt(0).autoSizeColumn(3);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void updateCalificationItem() {
		try {
			double percentTmp = 0;

			for (CalificationItem c : calificationItemNameList) {

				percentTmp += c.getPercentage();

			}

			if (percentTmp > evaluationSelected.getPercentage()) {
				ZyosBackingBean
						.addWarn("Modificar Notas",
								"El porcentaje de las notas supera el porcentaje total del corte!");

				return;

			} else {

				SubjectBean subjectBean = new SubjectBean();
				controller = new SubjectController();

				controller.updateCalificationItem(calificationItemNameList,
						subjectBean.getUserSession().getDocumentNumber());

				loadStudentCalificationItemList(evaluationSelected);
				ZyosBackingBean.addInfo("Modificar Notas",
						"Notas Modificadas con éxito!");

				ZyosBackingBean.update("subjectForm:dataTableCalification");
				ZyosBackingBean.getRequestContext().execute(
						"dialogEditCalificationItem.hide();");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);

		}

	}

	public Subject getSubjectSelected() {
		return subjectSelected;
	}

	public void setSubjectSelected(Subject subjectSelected) {
		this.subjectSelected = subjectSelected;
	}

	public SubjectController getController() {
		return controller;
	}

	public void setController(SubjectController controller) {
		this.controller = controller;
	}

	public void handleReportConstruct() {

	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public List<Evaluation> getEvaluationList() {
		return evaluationList;
	}

	public void setEvaluationList(List<Evaluation> evaluationList) {
		this.evaluationList = evaluationList;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
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

	public Evaluation getEvaluationToDelete() {
		return evaluationToDelete;
	}

	public void setEvaluationToDelete(Evaluation evaluationToDelete) {
		this.evaluationToDelete = evaluationToDelete;
	}

	public double getCurrentPercentage() {
		return currentPercentage;
	}

	public void setCurrentPercentage(double currentPercentage) {
		this.currentPercentage = currentPercentage;
	}

	public int getFullPercentage() {
		return fullPercentage;
	}

	public void setFullPercentage(int fullPercentage) {
		this.fullPercentage = fullPercentage;
	}

	public Evaluation getEvaluationSelected() {
		return evaluationSelected;
	}

	public void setEvaluationSelected(Evaluation evaluationSelected) {
		this.evaluationSelected = evaluationSelected;
	}

	public CalificationItem getCalificationItem() {
		return calificationItem;
	}

	public void setCalificationItem(CalificationItem calificationItem) {
		this.calificationItem = calificationItem;
	}

	public List<Student> getStudentCalificationItemList() {
		return studentCalificationItemList;
	}

	public void setStudentCalificationItemList(
			List<Student> studentCalificationItemList) {
		this.studentCalificationItemList = studentCalificationItemList;
	}

	public int getNumberCalification() {
		return numberCalification;
	}

	public void setNumberCalification(int numberCalification) {
		this.numberCalification = numberCalification;
	}

	public CalificationItem getCalification() {
		return calification;
	}

	public void setCalification(CalificationItem calification) {
		this.calification = calification;
	}

	public List<Long> getIdsCalificationItem() {
		return idsCalificationItem;
	}

	public void setIdsCalificationItem(List<Long> idsCalificationItem) {
		this.idsCalificationItem = idsCalificationItem;
	}

	public List<CalificationItem> getCalificationItemNameList() {
		return calificationItemNameList;
	}

	public void setCalificationItemNameList(
			List<CalificationItem> calificationItemNameList) {
		this.calificationItemNameList = calificationItemNameList;
	}

	public List<Double> getFinalCalificationList() {
		return finalCalificationList;
	}

	public void setFinalCalificationList(List<Double> finalCalificationList) {
		this.finalCalificationList = finalCalificationList;
	}

	public double getCurrentPercentageCalification() {
		return currentPercentageCalification;
	}

	public void setCurrentPercentageCalification(
			double currentPercentageCalification) {
		this.currentPercentageCalification = currentPercentageCalification;
	}

	public int getFullPercentageCalification() {
		return fullPercentageCalification;
	}

	public void setFullPercentageCalification(int fullPercentageCalification) {
		this.fullPercentageCalification = fullPercentageCalification;
	}

	public CalificationItem getCalificationItemToDelete() {
		return calificationItemToDelete;
	}

	public void setCalificationItemToDelete(
			CalificationItem calificationItemToDelete) {
		this.calificationItemToDelete = calificationItemToDelete;
	}

}
