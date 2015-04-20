package com.zyos.alert.familyStudent.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.familyStudent.model.FamilyStudent;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.alert.studentReport.controller.ReportStudentController;
import com.zyos.alert.studentReport.controller.StudentReportController;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorCategory;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.session.common.User;

@ManagedBean
@ViewScoped
@URLMapping(id = "familyStudentBean", pattern = "/portal/reporteFamiliar", viewId = "/pages/inscriptionReportFamily/inscriptionReportFamily.jspx")
public class FamilyStudentBean {

	private FamilyStudentController controllerFamilyStudent;
	private StudentReportController controller;
	private FamilyStudent familyStudent;
	private Student student;
	private Student validateStudent;
	private boolean showReportData;
	private boolean showObservation;
	private int idCategory;

	private ReportStudent reportStudent;
	private User userSession;
	private ArrayList<SelectItem> relationshipList;
	private List<RiskFactorCategory> riskFactorCategoryList;
	private List<RiskFactor> riskFactorListByCategory;

	public FamilyStudentBean() throws Exception {

		familyStudent = new FamilyStudent();
		reportStudent = new ReportStudent();
		student = new Student();
		validateStudent = new Student();
		controllerFamilyStudent = new FamilyStudentController();
		controller = new StudentReportController();
		showReportData = false;
		showObservation = false;
		idCategory = 0;

		relationshipList = controllerFamilyStudent.loadRelationshipTypeList();
		riskFactorCategoryList = controllerFamilyStudent.loadRiskFactorCategoryList();
	}

	/** @autor jhernandez **/
	public void searchUser() {
		try {

			if ((!student.getDocumentNumber().equals("") && !student.getEmail().equals(""))
				|| ((!student.getDocumentNumber().equals("") && !student.getCode().equals("")))
				|| (!student.getCode().equals("") && !student.getEmail().equals(""))
				|| (!student.getDocumentNumber().equals("") && !student.getEmail().equals("") && !student.getCode().equals(""))) {

				ZyosBackingBean.addWarn("Solicitud de acompañamiento a estudiante", "Debe ingresar solo un criterio de busqueda.");
				return;

			}

			if (!student.getDocumentNumber().equals("") || !student.getEmail().equals("") || !student.getCode().equals("")) {

				validateStudent = controller.validateStudent(student.getDocumentNumber(), student.getEmail(), student.getCode());

				if (validateStudent != null) {

					ZyosBackingBean.addInfo("Solicitud de acompañamiento a estudiante", "Estudiante cargado con éxito!");

					showReportData = true;
					ZyosBackingBean.getRequestContext().update("inscriptionRegisterFamilyForm:registerReportFamilyPanel");

				} else {
					showReportData = false;
					showObservation = false;
					riskFactorListByCategory = null;
					idCategory = 0;
					ZyosBackingBean.getRequestContext().update("inscriptionRegisterFamilyForm");
					ZyosBackingBean.addWarn("Solicitud de acompañamiento a estudiante",
						"El estudiante al que desea realizarle la solicitud de acompañamiento no se encuentra registrado.");

				}

			} else {
				ZyosBackingBean.addWarn("Buscar Estudiante", "Debe diligenciar al menos uno de los campos de busqueda.");
				showObservation = false;
				showReportData = false;
				riskFactorListByCategory = null;
				idCategory = 0;

				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 * @autor jhernandez
	 **/
	public void saveFamilyStudentReport() throws Exception {

		try {
			if (validateStudent.getDocumentNumber() != null) {

				if (reportStudent.getIdRiskFactor() == 0) {
					ZyosBackingBean.addWarn("Solicitud de acompañamiento a estudiante", "Debe seleccionar por lo menos un factor de riesgo.");
					return;
				} else {

					if (familyStudent.getPhone() != 0 || familyStudent.getMobilePhone() != 0) {

						ReportStudent reportDuplicate = new ReportStudent();
						StudentReportController controllerStudentReport = new StudentReportController();
						ReportStudentController controllerReportStudent = new ReportStudentController();
						Student studentCurrent = new Student();

						studentCurrent = controllerStudentReport.loadStudentCurrent(validateStudent.getIdZyosUser());

						reportDuplicate =
							controllerReportStudent.validateReportDuplicate(validateStudent.getIdStudent(), reportStudent.getIdRiskFactor());

						if (reportDuplicate == null) {

							Long idAdviser = controller.loadIdAdviser(validateStudent.getIdDegree());

							reportStudent.setIdStudent(BigDecimal.valueOf(validateStudent.getIdStudent()));
							reportStudent.setIdReportType(IReportType.MANUAL);
							// reportStudent.setIdSolicitor(userSession.getId());
							// null
							// because is externalUser
							reportStudent.setIdStatusReportStudent(IStatusReportStudent.REGISTER);
							reportStudent.setIdZyosUserAdviserFaculty(idAdviser);
							reportStudent.setIdZyosGroup(null);
							familyStudent.setIdStudent(validateStudent.getIdStudent());
							familyStudent.setIdRiskFactor(reportStudent.getIdRiskFactor());

							controllerFamilyStudent.saveFamilyStudent(familyStudent, familyStudent.getDocument());

							controller.saveReportStudent(reportStudent, familyStudent.getDocument());
							controller.saveRiskFactor(reportStudent, familyStudent.getDocument());
							controller.saveReportStudentObservation(reportStudent, familyStudent.getDocument());

							ZyosBackingBean.addInfo("Solicitud de acompañamiento a estudiante", "El estudiante fue registrado con éxito!");
							clearDataForm();
						} else {
							ZyosBackingBean.addInfo("Solicitud de acompañamiento a estudiante", "El estudiante fue registrado con éxito!");
							clearDataForm();

						}

					} else {
						ZyosBackingBean.addWarn("Solicitud de acompañamiento a estudiante",
							"Debe ingresar un Teléfono Fijo o Celular para contactarlo posteriormente.");
						familyStudent.setPhone(null);
						familyStudent.setMobilePhone(null);
						return;

					}
				}
			} else {
				ZyosBackingBean.addWarn("Solicitud de acompañamiento a estudiante",
					"Debe cargar un estudiante para solicitar acompañamiento al estudiante");
				if (familyStudent.getPhone() == 0) {
					familyStudent.setPhone(null);
				}
				if (familyStudent.getMobilePhone() == 0) {
					familyStudent.setMobilePhone(null);
				}
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	/** @autor jhernandez **/
	public void clearDataForm() {
		reportStudent = new ReportStudent();
		familyStudent = new FamilyStudent();
		student = new Student();
		validateStudent = new Student();
		showReportData = false;
		showObservation = false;
		riskFactorListByCategory = null;
		idCategory = 0;
		ZyosBackingBean.update("inscriptionRegisterFamilyForm");

	}

	public void loadRiskFactorListByCategory() {
		try {

			showObservation = false;
			familyStudent = new FamilyStudent();
			reportStudent.setDetailReport(null);
			riskFactorListByCategory = controller.loadRiskFactorListByCategory(idCategory);
			loadTitleRiskList();
			ZyosBackingBean.update("inscriptionRegisterFamilyForm");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @author MTorres 19/06/2014 9:31:05 */
	private void loadTitleRiskList() throws Exception {
		try {
			List<String> titleList = new ArrayList<String>(riskFactorListByCategory.size());
			for (RiskFactor rf : riskFactorListByCategory) {
				titleList.add(rf.getDescription() + "¥");
			}
			ZyosBackingBean.getRequestContext().execute("addFamilyToolTip('" + titleList + "');");
		} catch (Exception e) {
			throw e;
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

	public FamilyStudentController getControllerFamilyStudent() {
		return controllerFamilyStudent;
	}

	public void setControllerFamilyStudent(FamilyStudentController controllerFamilyStudent) {
		this.controllerFamilyStudent = controllerFamilyStudent;
	}

	public StudentReportController getController() {
		return controller;
	}

	public void setController(StudentReportController controller) {
		this.controller = controller;
	}

	public ReportStudent getReportStudent() {
		return reportStudent;
	}

	public void setReportStudent(ReportStudent reportStudent) {
		this.reportStudent = reportStudent;
	}

	public void setRelationshipList(ArrayList<SelectItem> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public ArrayList<SelectItem> getRelationshipList() {
		return relationshipList;
	}

	public void setrelationshipList(ArrayList<SelectItem> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public FamilyStudent getFamilyStudent() {
		return familyStudent;
	}

	public void setFamilyStudent(FamilyStudent familyStudent) {
		this.familyStudent = familyStudent;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public List<RiskFactor> getRiskFactorList() {

		return ZyosBackingBean.getRiskFactorList();

	}

	public Student getValidateStudent() {
		return validateStudent;
	}

	public void setValidateStudent(Student validateStudent) {
		this.validateStudent = validateStudent;
	}

	public boolean isShowReportData() {
		return showReportData;
	}

	public void setShowReportData(boolean showReportData) {
		this.showReportData = showReportData;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public boolean isShowObservation() {
		return showObservation;
	}

	public void setShowObservation(boolean showObservation) {
		this.showObservation = showObservation;
	}

	public List<RiskFactorCategory> getRiskFactorCategoryList() {
		return riskFactorCategoryList;
	}

	public void setRiskFactorCategoryList(List<RiskFactorCategory> riskFactorCategoryList) {
		this.riskFactorCategoryList = riskFactorCategoryList;
	}

	public List<RiskFactor> getRiskFactorListByCategory() {
		return riskFactorListByCategory;
	}

	public void setRiskFactorListByCategory(List<RiskFactor> riskFactorListByCategory) {
		this.riskFactorListByCategory = riskFactorListByCategory;
	}



}
