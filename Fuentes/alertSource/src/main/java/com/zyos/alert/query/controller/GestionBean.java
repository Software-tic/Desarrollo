package com.zyos.alert.query.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.query.model.School;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.user.controller.UserBean;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean
@ViewScoped
@URLMapping(id = "gestionBean", pattern = "/portal/Facultades", viewId = "/pages/facultyGestion/index.jspx")
public class GestionBean extends ZyosBackingBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean showDivisionList,showFacultyList,showUserList,showAdd;
	private boolean DecanoD,DecanoF,Teacher;
	private boolean facultyAdd,SchoolAdd;
	private String headerText,headerTextDialog,headerAdd;
	private String messageDel;
	private List<School> schoolList;
	private List<Faculty> facultyList;
	private List<ZyosUser> zyosUserList;
	private List<ZyosUser> zyosUserListFind;
	private ZyosUser ZUSelectToAdd;
	
	private School schoolSelectD;
	private School schoolSelect;
	private Faculty facultySelect;
	private ZyosUser zyosUserSelect;
	
	private GestionController controller = new GestionController();
	
	public GestionBean() throws Exception {
		clearViews();
		showDivisionList = true;
		this.facultyList = controller.loadDivisionTunja();
	}
	
	public void clearViews(){
		showDivisionList = false;
		showFacultyList = false;
		showUserList = false;
		showAdd = false;
	}
	
	public void clearParams(){
		DecanoD = false;
		DecanoF = false;
		Teacher = false;
	}
	
	public void clearParams2(){
		facultyAdd = false;
		SchoolAdd = false;
	}
	
	public void goFacultys() throws Exception{
		schoolList = controller.loadFacultyTunja(facultySelect.getIdFaculty());
		clearViews();
		showFacultyList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goDecanoDivision() throws Exception{
		clearParams();
		DecanoD=true;
		this.headerText= "Decano de División";
		zyosUserList = controller.loadDecDivisionBySchoolTunja(facultySelect.getIdFaculty());
		clearViews();
		showUserList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goDecanoFacultad() throws Exception{
		clearParams();
		DecanoF=true;
		this.headerText= "Decano de Facultad";
		zyosUserList = controller.loadDecFacultyBySchoolTunja(schoolSelectD.getIdschool());
		clearViews();
		showUserList = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goTeachers() throws Exception{
		clearParams();
		Teacher=true;
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
		Faculty aux = controller.loadFacultyById(facultySelect.getIdFaculty());
		if (aux != null){
			if (!controller.deleteDivision(facultySelect.getIdFaculty())){
				addInfo( "Eliminar División","No se ha podido eliminar la división");
			} else {
				
				addInfo( "Eliminar División","Se eliminó correctamente la división");
			}
		} else {
			addInfo( "Eliminar División","No se ha podido eliminar la división tiene facultades asignadas");
		}
		ZyosBackingBean.update("gestionForm");
		//ZyosBackingBean.update("gestionForm:facultyTable");
	}
	
	public void goDeleteFaculty() throws Exception{
		List<ZyosUser> aux = null;
		aux = controller.loadTeacherListBySchoolTunja(schoolSelect.getIdschool());
		if (aux != null){
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
		Boolean delete = false;
		 if(Teacher) {
				delete = controller.deleteTeacher(zyosUserSelect.getIdZyosUser());
				goTeachers();
		}
		else if(DecanoF){
			delete = controller.deleteDecanoF(zyosUserSelect.getIdZyosUser());
			goDecanoFacultad();
		}
		else if(DecanoD) {
			delete = controller.deleteDecanoD(zyosUserSelect.getIdZyosUser());
			goDecanoDivision();
		}
		if (!delete){
			addInfo( "Eliminar Persona","No se ha podido eliminar al usuario");
		} else {
			addInfo( "Eliminar Persona","Se eliminó correctamente al usuario");
			
		}
	}
	
	@SuppressWarnings("static-access")
	public void goAddPerson() throws Exception{
		 if(Teacher) {
			 headerTextDialog = "Agregar Profesor";
		}
		else if(DecanoF) {
			headerTextDialog = "Agregar Decano de Facultad";
		}
		else if(DecanoD) {
			headerTextDialog = "Agregar Decano de División";
		}
		zyosUserListFind = controller.LoadZyosUserTunja();
		ZyosBackingBean.getRequestContext().getCurrentInstance().update("gestionForm:userTableAdd");
		ZyosBackingBean.getRequestContext().getCurrentInstance().update("gestionForm:globalFilter");
		ZyosBackingBean.update("gestionForm:dlg2");
		ZyosBackingBean.getRequestContext().execute("dlg2WV.show();");
	}
	
	public void AddPerson() throws Exception{
		Boolean Create = false;
		if(Teacher) {
			 Create = controller.createTeacher(ZUSelectToAdd.getIdZyosUser(),schoolSelect.getIdschool(),getUserSession().getDocumentNumber());
				goTeachers();
		}
		else if(DecanoF) {
			Create = controller.createDecanoF(ZUSelectToAdd.getIdZyosUser(),schoolSelectD.getIdschool(),getUserSession().getDocumentNumber());
			goDecanoFacultad();
		}
		else if(DecanoD) {
			Create = controller.createDecanoD(ZUSelectToAdd.getIdZyosUser(),facultySelect.getIdFaculty(),getUserSession().getDocumentNumber());
			goDecanoDivision();
		}
		if (!Create) {
			addInfo( "Crear Persona","No se ha podido crear al usuario" );
		} else {
			addInfo( "Crear Persona","Se creó correctamente al usuario" );
		}
		ZyosBackingBean.getRequestContext().execute("dlg2WV.hide();");
		ZyosBackingBean.getRequestContext().getCurrentInstance().reset("gestionForm:dlg2");
		//ZyosBackingBean.getRequestContext().getCurrentInstance().update("gestionForm:userTableAdd");
		//ZyosBackingBean.getRequestContext().getCurrentInstance().update("gestionForm:globalFilter");
		ZyosBackingBean.update("gestionForm");
		
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
	
	public void goAddFaculty(){
		facultySelect = new Faculty();
		clearParams2();
		facultyAdd = true;
		headerAdd = "División";
		clearViews();
		showAdd = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void goAddSchool(){
		schoolSelect = new School();
		clearParams2();
		SchoolAdd = true;
		headerAdd = "Facultad";
		clearViews();
		showAdd = true;
		ZyosBackingBean.update("gestionForm");
	}
	
	public void AddParameter() throws Exception{
		Boolean result=false;
		if (facultyAdd) {
			result=controller.saveFaculty(facultySelect,getUserSession().getDocumentNumber());
		} else if (SchoolAdd) {
			result=controller.saveSchool(schoolSelect,getUserSession().getDocumentNumber());
		}
		
		if (result)
			addInfo( "Crear "+headerAdd,"Se creó correctamente la "+headerAdd);
		else 
			addInfo( "Crear "+headerAdd,"No se creó la "+headerAdd);
		clearViews();
		showDivisionList=true;
		ZyosBackingBean.update("gestionForm");
		//ZyosBackingBean.update("gestionForm:facultyTable");
	}
	
	public void goDetailUser() throws Exception{
		zyosUserSelect = controller.loadZyosUser(zyosUserSelect.getIdZyosUser());
		ZyosBackingBean.update("gestionForm:PoPupInfoUser");
		ZyosBackingBean.getRequestContext().execute("PoPupInfoUserWV.show();");
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

	public School getSchoolSelectD() {
		return schoolSelectD;
	}

	public void setSchoolSelectD(School schoolSelectD) {
		this.schoolSelectD = schoolSelectD;
	}

	public String getHeaderTextDialog() {
		return headerTextDialog;
	}

	public void setHeaderTextDialog(String headerTextDialog) {
		this.headerTextDialog = headerTextDialog;
	}

	public List<ZyosUser> getZyosUserListFind() {
		return zyosUserListFind;
	}

	public void setZyosUserListFind(List<ZyosUser> zyosUserListFind) {
		this.zyosUserListFind = zyosUserListFind;
	}

	public ZyosUser getZUSelectToAdd() {
		return ZUSelectToAdd;
	}

	public void setZUSelectToAdd(ZyosUser zUSelectToAdd) {
		ZUSelectToAdd = zUSelectToAdd;
	}

	public boolean isTeacher() {
		return Teacher;
	}

	public void setTeacher(boolean teacher) {
		Teacher = teacher;
	}

	public boolean isDecanoF() {
		return DecanoF;
	}

	public void setDecanoF(boolean decanoF) {
		DecanoF = decanoF;
	}

	public boolean isDecanoD() {
		return DecanoD;
	}

	public void setDecanoD(boolean decanoD) {
		DecanoD = decanoD;
	}
	
	public boolean isFacultyAdd() {
		return facultyAdd;
	}

	public void setFacultyAdd(boolean facultyAdd) {
		this.facultyAdd = facultyAdd;
	}

	public boolean isShowAdd() {
		return showAdd;
	}

	public void setShowAdd(boolean showAdd) {
		this.showAdd = showAdd;
	}

	public String getHeaderAdd() {
		return headerAdd;
	}

	public void setHeaderAdd(String headerAdd) {
		this.headerAdd = headerAdd;
	}

	public boolean isSchoolAdd() {
		return SchoolAdd;
	}

	public void setSchoolAdd(boolean schoolAdd) {
		SchoolAdd = schoolAdd;
	}
	
}
