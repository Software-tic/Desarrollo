package com.zyos.alert.studentReport.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import com.zyos.alert.absent.model.Absent;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.session.common.User;

public class HistoricalBean {

	private SubjectController controller;
	private Subject subjectSelected;
	private List<Absent> studentAbsent;
	private List<Absent> filteredStudentAbsent;
	private Absent absentToDelete;
	private User userSession;
	private String observationDeleteAbsent;

	private List<Long> idStudentSubjectList;

	public HistoricalBean(SubjectController controller,
			Subject subjectSelected, List<Student> studentList, User userSession)
			throws Exception {

		this.controller = controller;
		this.subjectSelected = subjectSelected;
		this.userSession = userSession;
		idStudentSubjectList = new ArrayList<Long>();

		for (Student s : studentList) {
			idStudentSubjectList.add(s.getIdStudentSubject());
		}
		studentAbsent = controller.loadStudentAbsentList(idStudentSubjectList);

	}

	public void deleteAbsent() throws Exception {

		try {

			if (absentToDelete == null) {
				ZyosBackingBean.addWarn("Eliminar Falla",
						" Error al intentar eliminar falla seleccionada");
			} else {

				controller.deleteAbsent(absentToDelete,
						userSession.getDocumentNumber());
				ZyosBackingBean.addInfo("Eliminar Falla",
						"Falla eliminada con éxito!");
				studentAbsent.remove(absentToDelete);
				ZyosBackingBean.getRequestContext().execute(
						"delAbsentDialog.hide();"); 
				cleanDataAbsent();

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	public void goDelAbsentDialog ()
	{
		observationDeleteAbsent = null;
		ZyosBackingBean.update("subjectForm:delAbsentDialog");
		ZyosBackingBean.getRequestContext().execute(
			"delAbsentDialog.show();");		
	}	

	public void cleanDataAbsent() {
		ZyosBackingBean.update("subjectForm:dataTableHistorical");
		absentToDelete = new Absent();
		observationDeleteAbsent = null;
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

	public List<Absent> getStudentAbsent() {
		return studentAbsent;
	}

	public void setStudentAbsent(List<Absent> studentAbsent) {
		this.studentAbsent = studentAbsent;
	}

	public List<Absent> getFilteredStudentAbsent() {
		return filteredStudentAbsent;
	}

	public void setFilteredStudentAbsent(List<Absent> filteredStudentAbsent) {
		this.filteredStudentAbsent = filteredStudentAbsent;
	}

	public Absent getAbsentToDelete() {
		return absentToDelete;
	}

	public void setAbsentToDelete(Absent absentToDelete) {
		this.absentToDelete = absentToDelete;
	}

	public List<Long> getIdStudentSubjectList() {
		return idStudentSubjectList;
	}

	public void setIdStudentSubjectList(List<Long> idStudentSubjectList) {
		this.idStudentSubjectList = idStudentSubjectList;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public String getObservationDeleteAbsent() {
		return observationDeleteAbsent;
	}

	public void setObservationDeleteAbsent(String observationDeleteAbsent) {
		this.observationDeleteAbsent = observationDeleteAbsent;
	}
	
	

}
