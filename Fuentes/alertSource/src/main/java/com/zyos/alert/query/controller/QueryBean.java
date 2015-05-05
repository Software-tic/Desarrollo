package com.zyos.alert.query.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean
@ViewScoped
@URLMapping(id = "queryBean", pattern = "/portal/consulta", viewId = "/pages/query/query.jspx")
public class QueryBean extends ZyosBackingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean showUserList = true;
	private boolean showSubjectByUserList;
	
	private String FacultyNameList, DocentePAAINameList;

	private List<ZyosUser> userList;
	private List<Subject> subjectListByStudent;
	private List<Faculty> FacultiesList;

	private QueryController controller = new QueryController();

	public QueryBean() throws Exception {
		userList = controller.getUserPAAIList();
	}

	public void goShowInfo(ZyosUser zu) {
		System.out.println(zu.getDocumentNumber());
		try {
			subjectListByStudent = null;
			subjectListByStudent = controller.lodInfoToShow(zu);
			
			//execute("showInfoWV.show();");
			
			addWarn("sdadasdas", "sdasdasd");
			
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

	public List<Subject> getSubjectListByStudent() {
		return subjectListByStudent;
	}

	public void setSubjectListByStudent(List<Subject> subjectListByStudent) {
		this.subjectListByStudent = subjectListByStudent;
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

}
