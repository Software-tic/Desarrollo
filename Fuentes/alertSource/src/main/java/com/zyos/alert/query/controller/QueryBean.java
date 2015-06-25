package com.zyos.alert.query.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean
@ViewScoped
@URLMapping(id = "queryBean", pattern = "/portal/consulta", viewId = "/pages/query/query.jspx")
public class QueryBean extends ZyosBackingBean {

	/**
	 * SIAT TUNJA
	 */
	private static final long serialVersionUID = 1L;
	private boolean showUserList = true;
	private boolean showSubjectByUserList;
	
	private String FacultyNameList, DocentePAAINameList;

	private List<ZyosUser> userList;
	private List<Observation> ObservationList;
	private List<Faculty> FacultiesList;
	
	private ZyosUser selectedZyosUser;
	private String Fecha="";
	private QueryController controller = new QueryController();

	public QueryBean() throws Exception {
		userList = controller.getUserPAAIList();
	}

	public void goShowInfo(ZyosUser zu) {
		try {
			Fecha = ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD);	
			setObservationList(null);
			setObservationList(controller.lodInfoObservationToShow(zu));		
			setShowSubjectByUserList(true);
			setPanelView("showSubject", "titulo", "QueryBean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ZyosUser> onChangeFaculty(){
		
		return userList;
		
	}
	
	public void goBack(){
		setShowUserList(true);
		setPanelView("showSubject", "titulo", "QueryBean");
	}

	public boolean isShowUserList() {
		return showUserList;
	}

	public void setShowUserList(boolean showUserList) {
		this.showUserList = showUserList;
		this.showSubjectByUserList = !showUserList;
	}

	public List<ZyosUser> getUserList() {
		return userList;
	}

	public void setUserList(List<ZyosUser> userList) {
		this.userList = userList;
	}

	public QueryController getController() {
		return controller;
	}

	public void setController(QueryController controller) {
		this.controller = controller;
	}

	public boolean isShowSubjectByUserList() {
		return showSubjectByUserList;
	}

	public void setShowSubjectByUserList(boolean showSubjectByUserList) {
		this.showSubjectByUserList = showSubjectByUserList;
		this.showUserList = !showSubjectByUserList;
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

	public List<Observation> getObservationList() {
		return ObservationList;
	}

	public void setObservationList(List<Observation> observationList) {
		ObservationList = observationList;
	}

	public ZyosUser getSelectedZyosUser() {
		return selectedZyosUser;
	}

	public void setSelectedZyosUser(ZyosUser selectedZyosUser) {
		this.selectedZyosUser = selectedZyosUser;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

}
