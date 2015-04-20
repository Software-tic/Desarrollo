package com.zyos.alert.inscriptionStudent.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.absent.model.DayClass;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.alert.studentReport.controller.ReportStudentController;
import com.zyos.alert.studentReport.controller.StudentReportController;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean
@ViewScoped
@URLMapping(id = "inscriptionStudentBean", pattern = "/portal/registroEstudiante", viewId = "/pages/student/student.jspx")
public class InscriptionStudentBean extends ZyosBackingBean {
	// Primitives
	private boolean showRegisterStudent = true;
	private boolean showObservation;
	private boolean existUser;
	private boolean documentExist;
	private String observation;
	private String documentUser;
	private String emailUser;
	private int idCategory;

	private Student student;
	private Student currentStudent;
	private ZyosUser zyosUser;
	private ReportStudent reportStudent;

	private InscriptionStudentController controller;
	private StudentReportController controllerStudentReport;
	private ReportStudentController controllerReport;

	private List<RiskFactor> riskFactorListByCategory;

	public InscriptionStudentBean() throws Exception {
		try {
			this.zyosUser = new ZyosUser();
			this.student = new Student();
			this.reportStudent = new ReportStudent();
			this.controller = new InscriptionStudentController();
			this.controllerStudentReport = new StudentReportController();
			this.existUser = false;
			this.showObservation = false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez **/
	public void searchUser() {
		try {
			if (!this.documentUser.equals("") || !this.emailUser.equals("")) {

				this.zyosUser = this.controller.searchUser(this.documentUser, this.emailUser);
				if (this.zyosUser != null) {
					addInfo("Solicitud de acompañamiento a estudiante", "Estudiante cargado con exito!");
					this.existUser = true;
					getRequestContext().update("registerStudentForm:registerStudentPanel");
				} else {
					addWarn("Solicitud de acompañamiento a estudiante", "No se encontraron resultados de búsqueda.");
					this.existUser = false;
					this.zyosUser = new ZyosUser();
					getRequestContext().update("registerStudentForm:registerStudentPanel");
				}
			} else {
				addWarn("Buscar Estudiante", "Ingrese código o correo eléctronico por favor.");
				return;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * @author jhernandez
	 * **/
	public void inscribeStudent() {
		try {

			if (this.zyosUser.getDocumentNumber() != null && this.zyosUser.getName() != null) {
				if (reportStudent.getIdRiskFactor() == 0) {
					addWarn("Solicitud de acompañamiento a estudiante", "Debe seleccionar por lo menos un factor de riesgo");
					return;
				} else {
					controllerReport = new ReportStudentController();
					student = new Student();
					ReportStudent reportDuplicate = new ReportStudent();
					// reportStudent = (ReportStudent)
					// controllerReport.loadReportStudent();
					student = controllerStudentReport.loadStudentCurrent(zyosUser.getIdZyosUser());
					reportDuplicate = controllerReport.validateReportDuplicate(student.getIdStudent(), reportStudent.getIdRiskFactor());
					if (reportDuplicate == null) {
						Long idAdviser = controllerStudentReport.loadIdAdviser(student.getIdDegree());
						reportStudent.setIdStudent(BigDecimal.valueOf(student.getIdStudent()));
						reportStudent.setDetailReport(this.observation);
						reportStudent.setIdReportType(IReportType.MANUAL);
						reportStudent.setIdStatusReportStudent(IStatusReportStudent.REPORT);
						// Id de quien reporta.
						reportStudent.setIdZyosGroup(getUserSession().getDefaultGroup());
						reportStudent.setIdSolicitor(getUserSession().getId());
						// reportStudent.setIdStage(IStage.UDIES);
						// Id Adviser del ZyosUser que tenga la misma carrera
						// del estudiante.
						// reportStudent.setIdAdviser(idAdviser);
						reportStudent.setIdZyosUserAdviserFaculty(idAdviser);
						// reportStudent.setFirstIntervention(Long.valueOf(0));

						Long idReport = controller.saveReportStudent(reportStudent, getUserSession().getDocumentNumber());
						controller.saveRiskFactorReportStudent(idReport, reportStudent, getUserSession().getDocumentNumber());
						controllerStudentReport.saveReportStudentObservation(reportStudent, getUserSession().getDocumentNumber());
						clearDataStudent();
						getRequestContext().update("registerStudentForm:registerStudentPanel");
						addInfo("Solicitud de acompañamiento a estudiante", "El estudiante fue registrado exitosamente");
					} else {
						if (getUserSession().getDefaultGroup().equals(IZyosGroup.TEACHER)) {
							ZyosBackingBean.addInfo("Registrar Estudiante",
								"El estudiante ya ha sido reportado anteriormente por este factor de riesgo!, Gracias por tu preocupación!");
						} else {
							ZyosBackingBean.addInfo("- Registrar Estudiante", "El estudiante fué reportado éxitosamente.");
						}
						clearDataStudent();
						getRequestContext().update("registerStudentForm:registerStudentPanel");
					}
				}
			} else {
				addWarn("Solicitud de acompañamiento a estudiante",
					"Verifique que el código ó correo eléctronico es correcto y que ha realizado la búsqueda del estudiante y vuelva a intentar.");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez **/
	public void clearDataStudent() {
		this.reportStudent = new ReportStudent();
		this.student = new Student();
		this.zyosUser = new ZyosUser();
		this.observation = "";
		this.documentUser = "";
		this.emailUser = "";
		this.existUser = false;
		this.showObservation = false;
		this.idCategory = 0;
		riskFactorListByCategory = null;
	}

	/**
	 * @author mtorres
	 * */
	public void handleValidateDocumentExist() {
		try {
			if (!this.zyosUser.getDocumentNumber().trim().equals("")) {
				this.documentExist = this.controller.validateDocumentExist(this.zyosUser.getDocumentNumber());
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	private void loadTitleRiskList() throws Exception{
		try {
			List<String> titleList = new ArrayList<String>(riskFactorListByCategory.size());
			for (RiskFactor rf : riskFactorListByCategory) {
				titleList.add(rf.getDescription() + "¥");
			}
			ZyosBackingBean.getRequestContext().execute("addToolTip('" + titleList + "');");
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
			ZyosBackingBean.update("registerStudentForm");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** jhernandez */
	public void showObservation() {
		try {
			showObservation = true;
			loadTitleRiskList();
			ZyosBackingBean.update("registerStudentForm");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/*------getters && setters----------*/

	public boolean isShowRegisterStudent() {
		return showRegisterStudent;
	}

	public void setShowRegisterStudent(boolean showRegisterStudent) {
		this.showRegisterStudent = showRegisterStudent;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public ZyosUser getZyosUser() {
		return zyosUser;
	}

	public void setZyosUser(ZyosUser zyosUser) {
		this.zyosUser = zyosUser;
	}

	public boolean isDocumentExist() {
		return documentExist;
	}

	public void setDocumentExist(boolean documentExist) {
		this.documentExist = documentExist;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getDocumentUser() {
		return documentUser;
	}

	public void setDocumentUser(String documentUser) {
		this.documentUser = documentUser;
	}

	public boolean isExistUser() {
		return existUser;
	}

	public void setExistUser(boolean existUser) {
		this.existUser = existUser;
	}

	public Student getCurrentStudent() {
		return currentStudent;
	}

	public void setCurrentStudent(Student currentStudent) {
		this.currentStudent = currentStudent;
	}

	public ReportStudent getReportStudent() {
		return reportStudent;
	}

	public void setReportStudent(ReportStudent reportStudent) {
		this.reportStudent = reportStudent;
	}

	public InscriptionStudentController getController() {
		return controller;
	}

	public void setController(InscriptionStudentController controller) {
		this.controller = controller;
	}

	public StudentReportController getControllerStudentReport() {
		return controllerStudentReport;
	}

	public void setControllerStudentReport(StudentReportController controllerStudentReport) {
		this.controllerStudentReport = controllerStudentReport;
	}

	public ReportStudentController getControllerReport() {
		return controllerReport;
	}

	public void setControllerReport(ReportStudentController controllerReport) {
		this.controllerReport = controllerReport;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public List<RiskFactor> getRiskFactorListByCategory() {
		return riskFactorListByCategory;
	}

	public void setRiskFactorListByCategory(List<RiskFactor> riskFactorListByCategory) {
		this.riskFactorListByCategory = riskFactorListByCategory;
	}

	public boolean isShowObservation() {
		return showObservation;
	}

	public void setShowObservation(boolean showObservation) {
		this.showObservation = showObservation;
	}



}
