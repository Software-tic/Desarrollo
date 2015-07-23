package com.zyos.alert.query.controller;

import java.util.List;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.faculty.model.FacultyDAO;
import com.zyos.alert.query.model.School;
import com.zyos.alert.query.model.SchoolDAO;
import com.zyos.alert.query.model.TeacherDAO;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;


public class GestionController extends ZyosController {

	/**
	 * SIAT TUNJA
	 */
	private static final long serialVersionUID = 1L;
	
	
	public List<Faculty> loadDivisionTunja() throws Exception{
		FacultyDAO dao = new FacultyDAO();
		try {
			return dao.findAll();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<School> loadFacultyTunja(Long idDivision) throws Exception{
		SchoolDAO dao = new SchoolDAO();
		try {
			return dao.loadFacultyByDivision(idDivision);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<ZyosUser> loadTeacherListBySchoolTunja(Long idSchool) throws Exception{
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadTeacherBySchoolTunja(idSchool);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<ZyosUser> loadDecDivisionBySchoolTunja(Long idDivision) throws Exception{
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadDecDivisionBySchoolTunja(idDivision);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<ZyosUser> loadDecFacultyBySchoolTunja(Long idSchool) throws Exception{
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadDecFacultyBySchoolTunja(idSchool);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public Boolean deleteDivision(Long idFaculty) throws Exception{
		Boolean retorno=false;
		FacultyDAO dao = new FacultyDAO();
		try {
			dao.deleteDivision(idFaculty);
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean deleteFaculty(Long idSchool) throws Exception{
		Boolean retorno=false;
		SchoolDAO dao = new SchoolDAO();
		try {
			dao.deleteFaculty(idSchool);
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean deletePersona(Long idPersona) throws Exception{
		Boolean retorno=false;
		TeacherDAO dao = new TeacherDAO();
		try {
			dao.deletePersona(idPersona);
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean editDivision(Faculty facultySelect) throws Exception{
		Boolean retorno=false;
		FacultyDAO dao = new FacultyDAO();
		try {
			dao.updateDivision(facultySelect);
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean editFaculty(School schoolSelect) throws Exception{
		Boolean retorno=false;
		SchoolDAO dao = new SchoolDAO();
		try {
			dao.updateFaculty(schoolSelect);
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
}
