package com.zyos.alert.studentReport.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IStage;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.session.common.User;

public class ReportBean {

	private ReportStudent reportStudent;

	private SubjectController controller;
	private StudentReportController studentReportController;
	private ReportStudentController controllerReport;
	private Subject subjectSelected;
	private SubjectBean subjectBean;

	public ReportBean() throws Exception {

	}

	public ReportBean(SubjectController controller, Subject subjectSelected)
			throws Exception {
		this.controller = controller;
		this.subjectSelected = subjectSelected;
	}

	public void saveReportStudent(ReportStudent reportStudent,
			Student studentSelected, User user, SubjectController controller)
			throws Exception {

		try {

			controllerReport = new ReportStudentController();
			ReportStudent reportDuplicate = new ReportStudent();

			reportDuplicate = controllerReport.validateReportDuplicate(
					studentSelected.getIdStudent(),
					reportStudent.getIdRiskFactor());

			if (reportDuplicate == null) {

				studentReportController = new StudentReportController();
				
				Long idStudentDegree = studentReportController
						.loadIdStudentDegree(studentSelected.getIdStudent());

				Long idAdviser = studentReportController
						.loadIdAdviser(idStudentDegree);				

				reportStudent.setIdStudent(BigDecimal.valueOf(studentSelected.getIdStudent()));
				reportStudent.setIdReportType(IReportType.MANUAL);
				reportStudent.setIdSolicitor(user.getId());
				reportStudent.setIdZyosGroup(user.getDefaultGroup());

				reportStudent.setIdStatusReportStudent(IStatusReportStudent.REPORT);
				reportStudent.setIdZyosUserAdviserFaculty(idAdviser);
		
				
				controller.saveReportStudent(reportStudent,
						user.getDocumentNumber());
				controller.saveRiskFactor(reportStudent,
						user.getDocumentNumber());
				controller.saveReportStudentObservation(reportStudent,
						user.getDocumentNumber());

				ZyosBackingBean.addInfo("Solicitud de acompañamiento a estudiante",
						"El estudiante fue registrado con éxito!");				

				clearDataForm();

			} else {
				
				if(user.getDefaultGroup().equals(IZyosGroup.TEACHER))
				{
					ZyosBackingBean.addInfo(
							"Solicitud de acompañamiento a estudiante",
							"El estudiante ya ha sido registrado anteriormente por este factor de riesgo!, Gracias por tu preocupación!");
				}else
				{
					ZyosBackingBean.addInfo(
							"- Solicitud de acompañamiento a estudiante",
							"El estudiante fué reportado éxitosamente.");
				}
				
				clearDataForm();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clearDataForm() throws Exception {
		try {

			reportStudent = new ReportStudent();
			
			SubjectBean bean = (SubjectBean) ZyosBackingBean
					.getBean("subjectBean");
			if (bean != null) {
				bean.setReportStudent(reportStudent);
				bean.setIdCategory(0);
				bean.setShowObservation(false);
				bean.setRiskFactorListByCategory(null);
				
			}
			ZyosBackingBean.update("subjectForm:reportStudentPanel");
		} catch (Exception e) {
			e.printStackTrace();
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

	public ReportStudent getReportStudent() {
		return reportStudent;
	}

	public void setReportStudent(ReportStudent reportStudent) {
		this.reportStudent = reportStudent;
	}

}
