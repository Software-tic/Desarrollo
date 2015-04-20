package com.zyos.alert.studentReport.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IStage;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.session.common.User;

@ManagedBean
@ViewScoped
@URLMapping(id = "autoReportBean", pattern = "/portal/autoReportarse", viewId = "/pages/report/autoReport.jspx")
public class AutoReportBean extends ZyosBackingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StudentReportController controller;
	private ReportStudentController controllerReport;
	private Student student;
	private ReportStudent reportStudent;
	private User userSession;
	private boolean showObservation;
	private int idCategory;
	private List <RiskFactor> riskFactorListByCategory;

	public AutoReportBean() throws Exception {

		if (getValidateUserRole()) {

			student = new Student();
			reportStudent = new ReportStudent();
			controller = new StudentReportController();
			controllerReport = new ReportStudentController();
			showObservation = false;
			student = controller.loadStudentCurrent(userSession.getId());
		}
	}

	public boolean getValidateUserRole() throws Exception {
		try {
			if (getUserSession().getDefaultGroup().equals(IZyosGroup.STUDENT) || getUserSession().getDefaultGroup().equals(IZyosGroup.CLASS_MATE)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Expired session - redirect login");
			return false;
		}
	}

	public void saveAutoReportStudent() throws Exception {
		try {

			if (reportStudent.getIdRiskFactor() == 0) {
				ZyosBackingBean.addWarn("Solicitud de acompañamiento a estudiante", "Debe seleccionar por lo menos un factor de riesgo.");
				return;
			} else {

				
				ReportStudent reportDuplicate = new ReportStudent();
				reportDuplicate = controllerReport.validateReportDuplicate(student.getIdStudent(), reportStudent.getIdRiskFactor());
				if (reportDuplicate == null) {

					Long idAdviser = controller.loadIdAdviser(student.getIdDegree());
					reportStudent.setIdStudent(BigDecimal.valueOf(student.getIdStudent()));
					reportStudent.setIdReportType(IReportType.MANUAL);
					reportStudent.setIdSolicitor(userSession.getId());
					reportStudent.setIdZyosGroup(userSession.getDefaultGroup());
					reportStudent.setIdStatusReportStudent(IStatusReportStudent.REPORT);				
					reportStudent.setIdZyosUserAdviserFaculty(idAdviser);	
					controller.saveReportStudent(reportStudent, userSession.getDocumentNumber());
					controller.saveRiskFactor(reportStudent, userSession.getDocumentNumber());
					controller.saveReportStudentObservation(reportStudent, userSession.getDocumentNumber());

					ZyosBackingBean.addInfo("Solicitud de acompañamiento a estudiante", "El estudiante fue registrado con éxito!");
					clearDataForm();
				} else {
					ZyosBackingBean.addInfo("- Solicitud de acompañamiento a estudiante", "El estudiante fué registrado éxitosamente.");
					clearDataForm();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clearDataForm() {
		try {
			reportStudent = new ReportStudent();
			idCategory = 0;
			riskFactorListByCategory = null;
			showObservation = false;
			update("autoReporForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**@author MTorres 19/06/2014 9:34:47*/
	private void loadTitleRiskList() throws Exception{
		try {
			List<String> titleList = new ArrayList<String>(riskFactorListByCategory.size());
			for(RiskFactor rf:riskFactorListByCategory){
				titleList.add(rf.getDescription()+ "¥");
			}
			ZyosBackingBean.getRequestContext().execute("addAutoRepToolTip('"+titleList+"');");
		} catch (Exception e) {
			throw e;
		}
	}

	/** jhernandez*/
	public void loadRiskFactorListByCategory() {
		try {
			showObservation = false;
			riskFactorListByCategory = controller.loadRiskFactorListByCategory(idCategory);
			loadTitleRiskList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** jhernandez*/
	public void showObservation() {
		try {
			showObservation = true;
			loadTitleRiskList();
			ZyosBackingBean.update("autoReporForm");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public ReportStudent getReportStudent() {
		return reportStudent;
	}

	public void setReportStudent(ReportStudent reportStudent) {
		this.reportStudent = reportStudent;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public StudentReportController getController() {
		return controller;
	}

	public void setController(StudentReportController controller) {
		this.controller = controller;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public boolean isShowObservation() {
		return showObservation;
	}

	public void setShowObservation(boolean showObservation) {
		this.showObservation = showObservation;
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

}
