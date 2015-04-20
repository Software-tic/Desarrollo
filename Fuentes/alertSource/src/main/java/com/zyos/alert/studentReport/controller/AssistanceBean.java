package com.zyos.alert.studentReport.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.zyos.alert.absent.model.Absent;
import com.zyos.alert.absent.model.DayClass;
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
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;
import com.zyos.session.common.User;

public class AssistanceBean {

	private SubjectController controller;
	private ControlPanelController controllerControlPanel;
	private Subject subjectSelected;
	private Student studentSelected;
	private Student student;
	private Student studentCurrent;
	private ControlPanel controlPanel;
	private ControlPanel controlPanelAssistance;
	private ReportStudentController controllerReport;
	private StudentReportController studentReportController;
	private ExecutionsHistorical execution;
	private ExecutionsHistoricalController executionController;
	private User userSession;
	private DayClass dayClass;
	private List<Student> studentList;
	private List<DayClass> dayClassList;
	private List<DayClass> dayClassListTeacherTmp;
	private List<Absent> studentAbsent;
	private List<Absent> filteredStudentAbsent;
	private List<Long> idStudentSubjectList;
	private boolean validateDayClass;
	private boolean selectAllItems;
	private int hours;
	private int marginDaysAssistance;
	private Date dateSelected;
	private Date minDate;
	private Date maxDate;

	public AssistanceBean() {

	}

	public AssistanceBean(SubjectController controller,
			Subject subjectSelected, User userSession, List<DayClass> dayList,
			List<DayClass> dayClassListTeacherTmp) throws Exception {
		try {
			this.controller = controller;
			this.subjectSelected = subjectSelected;
			this.userSession = userSession;
			this.dayClassList = dayList;
			this.dayClassListTeacherTmp = dayClassListTeacherTmp;

			studentList = controller.loadStudentBySubjectList(
					this.subjectSelected.getIdSubject(),
					this.subjectSelected.getIdGroupSubject(),
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

		} catch (Exception e) {
			e.printStackTrace(); // ErrorNotificacion.handleErrorMailNotification(e,
									// this);
		}

	}

	/** @autor jhernandez */
	public static int getDayOfTheWeek(Date d) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/** @autor jhernandez */
	public void handleSelectStudentList() {
		try {
			if (selectAllItems)
				for (Student zu : studentList)
					zu.setSelected(true);
			else
				for (Student zu : studentList)
					zu.setSelected(false);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
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
					idStudentToReport, IRiskFactor.ABSENT);

			if (reportDuplicate == null) {

				studentReportController = new StudentReportController();

				Long idStudentDegree = studentReportController
						.loadIdStudentDegree(idStudentToReport);

				Long idAdviser = studentReportController
						.loadIdAdviser(idStudentDegree);

				reportStudent.setIdStudent(BigDecimal
						.valueOf(idStudentToReport));
				reportStudent.setIdReportType(IReportType.AUTOMATIC);
				reportStudent.setIdSolicitor(user.getId());
				reportStudent.setIdRiskFactor(IRiskFactor.ABSENT);

				reportStudent
						.setIdStatusReportStudent(IStatusReportStudent.REPORT);
				reportStudent.setIdZyosUserAdviserFaculty(idAdviser);
				reportStudent.setIdZyosGroup(user.getDefaultGroup());
				String detailReport = "Este reporte es generado automáticamente al "
						+ "estudiante por presentar Ausentismo por un número de fallas a"
						+ " clase considerable en alguna asignatura";
				reportStudent.setDetailReport(detailReport);

				controller.saveReportStudent(reportStudent,
						user.getDocumentNumber());
				controller.saveRiskFactor(reportStudent,
						user.getDocumentNumber());
				controller.saveReportStudentObservation(reportStudent,
						user.getDocumentNumber());

				return true;

			}
			return false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return false;
		}

	}

	/** @author jhernandez 20/08/2014 14:01:31 */
	public Date lessDaysToDate(Date fecha, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/** @author jhernandez 20/08/2014 14:01:31 */
	public void goRegisterAssistance() {
		try {

			controllerControlPanel = new ControlPanelController();
			String currentDate = ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD);
			Date CurrentDate = ManageDate.stringToDate(currentDate);
			controlPanel = controllerControlPanel.loadCurrentMarginDays();
			marginDaysAssistance = controlPanel.getMarginHour();

			Date dateMargin = ManageDate.plusDate(dateSelected,
					controlPanel.getMarginHour(), Calendar.DAY_OF_MONTH);

			minDate = lessDaysToDate(CurrentDate, -marginDaysAssistance);
			maxDate = CurrentDate;
			dateSelected = null;

			ZyosBackingBean.getRequestContext().update(
					"subjectForm:registerAssistanceDialog");
			ZyosBackingBean.getRequestContext().execute(
					"registerAssistanceWV.show();");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @author jhernandez 20/08/2014 14:01:31 */
	public void registerAssistance() {
		try {
			controllerControlPanel = new ControlPanelController();
			hours = 0;

			if (dateSelected != null) {

				int dayClassCurrent = getDayOfTheWeek(dateSelected);

				if (getUserSession().getDefaultGroup().equals(
						IZyosGroup.STUDENT)
						|| getUserSession().getDefaultGroup().equals(
								IZyosGroup.CLASS_MATE)) {

					if (dayClassList != null && dayClassList.size() > 0) {

						for (DayClass d : dayClassList) {

							if (d.getIdDayCalendar().equals(
									Long.valueOf(dayClassCurrent))) {
								String hourStart = d.getHourStart();
								String hourEnd = d.getHourEnd();
								String[] h1 = hourStart.split(":");
								String[] h2 = hourEnd.split(":");
								hours = (Integer.parseInt(h2[0]) - Integer
										.parseInt(h1[0]));
								break;
							}
						}
					}
				} else {
					if (dayClassListTeacherTmp != null
							&& dayClassListTeacherTmp.size() > 0) {

						for (DayClass d : dayClassListTeacherTmp) {

							if (d.getIdDayCalendar().equals(
									Long.valueOf(dayClassCurrent))) {
								String hourStart = d.getHourStart();
								String hourEnd = d.getHourEnd();
								String[] h1 = hourStart.split(":");
								String[] h2 = hourEnd.split(":");
								hours = (Integer.parseInt(h2[0]) - Integer
										.parseInt(h1[0]));
								break;
							}
						}

					} else {
						ZyosBackingBean
								.addWarn(
										"Registrar Asistencia",
										"No es posible registrar asistencia por inconsistencia en los horarios. Consulte al administrador");
						return;
					}
				}

				if (hours != 0) {
					for (int i = 0; i < hours; i++) {
						controller.registerAssistance(
								subjectSelected.getIdSubject(), studentList,
								userSession.getId(),
								userSession.getDocumentNumber());
					}
					clearStudentSelected();
					reportStudentAssitance();
					ZyosBackingBean.addInfo("Registro Asistencia",
							"Registro de Asistencia Éxitoso!");
				} else {
					ZyosBackingBean
							.addWarn(
									"Registrar Asistencia",
									"La fecha seleccionada no es un dia de clase de esta asignatura para registrar asistencia.");
				}

			} else {
				ZyosBackingBean
						.addWarn("Registrar Asistencia",
								"Seleccione una fecha para registrar asistencia por favor");
				return;
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			ZyosBackingBean
					.addWarn(
							"Registrar Asistencia",
							"Se presento un error al intentar registrar la asistencia, intente de nuevo o contacte al administrador");
		}

	}

	public void reportStudentAssitance() {
		try {

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {

						studentList = controller.loadStudentBySubjectList(
								subjectSelected.getIdSubject(), subjectSelected
										.getIdGroupSubject(), ManageDate
										.getCurrentDate(ManageDate.YYYY_MM_DD));

						Long start = System.currentTimeMillis();

						controllerControlPanel = new ControlPanelController();
						controlPanelAssistance = new ControlPanel();
						int reportStudentNumber = 0;
						execution = new ExecutionsHistorical();
						executionController = new ExecutionsHistoricalController();

						double percentageTotal = 100;
						controlPanelAssistance = controllerControlPanel
								.loadCurrentPercentageAssistance();
						double percentageAccumulated;

						for (Student s : studentList) {

							percentageAccumulated = ((s.getHours() * controlPanelAssistance
									.getPercentageAssistance()) / percentageTotal);

							if (s.getStudentAbsent() >= percentageAccumulated) {
								boolean isReport = saveReportStudent(
										s.getIdStudent(), userSession);
								if (isReport == true) {
									reportStudentNumber++;
									isReport = false;
								}
							}
						}

						Long end = System.currentTimeMillis();
						Long finaltime = end - start;

						Date executeTime = parseDate(finaltime);
						String execute = ManageDate.formatDate(executeTime,
								ManageDate.HH_MM_SS);

						execution.setExecutionTime(execute);
						execution.setIdRiskFactor(IRiskFactor.ABSENT);
						execution.setReportStudentNumber(reportStudentNumber);
						execution
								.setIdExecutionHistoricalType(IExecutionHistorialType.SAC);

						executionController.saveExecutionsHistorical(
								userSession.getDocumentNumber(), execution);

						System.out.println("tiempo de ejecucion: " + execute);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	/** @autor jhernandez */
	public void clearStudentSelected() {
		try {
			if (studentList == null || studentList.isEmpty()) {

				return;
			}

			for (Student s : studentList) {
				s.setSelected(false);
			}
			selectAllItems = false;

			ZyosBackingBean.update("subjectForm:subjectMain",
					"subjectForm:subjectDetail");
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

	public Student getStudentSelected() {
		return studentSelected;
	}

	public void setStudentSelected(Student studentSelected) {
		this.studentSelected = studentSelected;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
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

	public boolean isValidateDayClass() {
		return validateDayClass;
	}

	public void setValidateDayClass(boolean validateDayClass) {
		this.validateDayClass = validateDayClass;
	}

	public List<DayClass> getDayClassList() {
		return dayClassList;
	}

	public void setDayClassList(List<DayClass> dayClassList) {
		this.dayClassList = dayClassList;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public boolean isSelectAllItems() {
		return selectAllItems;
	}

	public void setSelectAllItems(boolean selectAllItems) {
		this.selectAllItems = selectAllItems;
	}

	public List<Absent> getStudentAbsent() {
		return studentAbsent;
	}

	public void setStudentAbsent(List<Absent> studentAbsent) {
		this.studentAbsent = studentAbsent;
	}

	public List<DayClass> getDayClassListTeacherTmp() {
		return dayClassListTeacherTmp;
	}

	public void setDayClassListTeacherTmp(List<DayClass> dayClassListTeacherTmp) {
		this.dayClassListTeacherTmp = dayClassListTeacherTmp;
	}

	public List<Absent> getFilteredStudentAbsent() {
		return filteredStudentAbsent;
	}

	public void setFilteredStudentAbsent(List<Absent> filteredStudentAbsent) {
		this.filteredStudentAbsent = filteredStudentAbsent;
	}

	public ControlPanelController getControllerControlPanel() {
		return controllerControlPanel;
	}

	public void setControllerControlPanel(
			ControlPanelController controllerControlPanel) {
		this.controllerControlPanel = controllerControlPanel;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public int getMarginDaysAssistance() {
		return marginDaysAssistance;
	}

	public void setMarginDaysAssistance(int marginDaysAssistance) {
		this.marginDaysAssistance = marginDaysAssistance;
	}

	public Date getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Date dateSelected) {
		this.dateSelected = dateSelected;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

}
