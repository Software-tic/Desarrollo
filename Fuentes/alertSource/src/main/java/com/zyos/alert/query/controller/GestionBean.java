package com.zyos.alert.query.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.query.model.School;
import com.zyos.alert.query.model.Teacher;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean
@ViewScoped
@URLMapping(id = "gestionBean", pattern = "/portal/Facultades", viewId = "/pages/facultyGestion/index.jspx")
public class GestionBean extends ZyosBackingBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean showDivisionList = true,showFacultyList,showUserList;
	private String headerText;
	private String messageDel;
	private List<School> schoolList;
	private List<Faculty> facultyList;
	private List<ZyosUser> zyosUserList;
	
	private School schoolSelect;
	private Faculty facultySelect;
	private ZyosUser zyosUserSelect;
	
	private GestionController controller = new GestionController();
	
	public GestionBean() throws Exception {
		this.facultyList = controller.loadDivisionTunja();
	}
	
	public void clearViews(){
		showDivisionList = false;
		showFacultyList = false;
		showUserList = false;
	}
	
	public void goFacultys() throws Exception{
		schoolList = controller.loadFacultyTunja(facultySelect.getIdFaculty());
		clearViews();
		showFacultyList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goDecanoDivision() throws Exception{
		this.headerText= "Decano de División";
		zyosUserList = controller.loadDecDivisionBySchoolTunja(facultySelect.getIdFaculty());
		clearViews();
		showUserList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goDecanoFacultad() throws Exception{
		this.headerText= "Decano de Facultad";
		zyosUserList = controller.loadDecFacultyBySchoolTunja(schoolSelect.getIdschool());
		clearViews();
		showUserList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goTeachers() throws Exception{
		this.headerText= "Profesores de Facultad";
		zyosUserList = controller.loadTeacherListBySchoolTunja(schoolSelect.getIdschool());
		clearViews();
		showUserList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goBack(){
		clearViews();
		showDivisionList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goDeleteDivision() throws Exception{
		List<School> aux = null;
		aux = controller.loadFacultyTunja(facultySelect.getIdFaculty());
		if (aux == null){
			if (!controller.deleteDivision(facultySelect.getIdFaculty())){
				addInfo( "Eliminar División","No se ha podido eliminar la división");
			} else {
				ZyosBackingBean.update("gestionForm");
				addInfo( "Eliminar División","Se eliminó correctamente la división");
			}
		} else {
			addInfo( "Eliminar División","No se ha podido eliminar la división tiene facultades asignadas");
		}
	}
	
	public void goDeleteFaculty() throws Exception{
		List<ZyosUser> aux = null;
		aux = controller.loadTeacherListBySchoolTunja(schoolSelect.getIdschool());
		if (aux == null){
			if (!controller.deleteFaculty(schoolSelect.getIdschool())){
				addInfo( "Eliminar Facultad","No se ha podido eliminar la facultad");
			} else {
				ZyosBackingBean.update("gestionForm");
				addInfo( "Eliminar Facultad","Se eliminó correctamente la facultad");
			}
		} else {
			addInfo( "Eliminar Facultad","No se ha podido eliminar la facultad tiene docentes asignados");
		}
	}
	
	public void goDeletePersona() throws Exception{
		if (!controller.deletePersona(zyosUserSelect.getIdZyosUser())){
			addInfo( "Eliminar Persona","No se ha podido eliminar al usuario");
		} else {
			ZyosBackingBean.update("gestionForm");
			addInfo( "Eliminar Persona","Se eliminó correctamente al usuario");
		}
	}
	
	public void goEditDiv(){
		ZyosBackingBean.update("gestionForm:PoPupInfo");
		ZyosBackingBean.getRequestContext().execute("PoPupInfoWV.show();");
	}
	
	public void goEditDivision() throws Exception{
		ZyosBackingBean.getRequestContext().execute("PoPupInfoWV.hide();");
		if (controller.editDivision(facultySelect))
			addInfo( "Editar División","Se editó correctamente la división");
		else 
			addInfo( "Editar División","No se realizó la edición de la división");
	}
	
	public void goEditFa(){
		ZyosBackingBean.update("gestionForm:PoPupInfo2");
		ZyosBackingBean.getRequestContext().execute("PoPupInfo2WV.show();");
	}
	
	public void goEditFaculty(){
		
	}
	
	public boolean isShowDivisionList() {
		return showDivisionList;
	}

	public void setShowDivisionList(boolean showDivisionList) {
		this.showDivisionList = showDivisionList;
	}

	public boolean isShowFacultyList() {
		return showFacultyList;
	}

	public void setShowFacultyList(boolean showFacultyList) {
		this.showFacultyList = showFacultyList;
	}

	public boolean isShowUserList() {
		return showUserList;
	}

	public void setShowUserList(boolean showUserList) {
		this.showUserList = showUserList;
	}

	public List<School> getSchoolList() {
		return schoolList;
	}

	public void setSchoolList(List<School> schoolList) {
		this.schoolList = schoolList;
	}

	public List<Faculty> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(List<Faculty> facultyList) {
		this.facultyList = facultyList;
	}

	public List<ZyosUser> getZyosUserList() {
		return zyosUserList;
	}

	public void setZyosUserList(List<ZyosUser> zyosUserList) {
		this.zyosUserList = zyosUserList;
	}

	public School getSchoolSelect() {
		return schoolSelect;
	}

	public void setSchoolSelect(School schoolSelect) {
		this.schoolSelect = schoolSelect;
	}

	public Faculty getFacultySelect() {
		return facultySelect;
	}

	public void setFacultySelect(Faculty facultySelect) {
		this.facultySelect = facultySelect;
	}

	public ZyosUser getZyosUserSelect() {
		return zyosUserSelect;
	}

	public void setZyosUserSelect(ZyosUser zyosUserSelect) {
		this.zyosUserSelect = zyosUserSelect;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getMessageDel() {
		return messageDel;
	}

	public void setMessageDel(String messageDel) {
		this.messageDel = messageDel;
	}
	
}
