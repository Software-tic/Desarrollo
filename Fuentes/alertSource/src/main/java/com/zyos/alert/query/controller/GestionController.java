package com.zyos.alert.query.controller;

import java.util.List;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.faculty.model.FacultyDAO;
import com.zyos.alert.facultyCoordinator.model.FacultyCoordinator;
import com.zyos.alert.facultyCoordinator.model.FacultyCoordinatorDAO;
import com.zyos.alert.query.model.School;
import com.zyos.alert.query.model.SchoolCoordinador;
import com.zyos.alert.query.model.SchoolCoordinadorDAO;
import com.zyos.alert.query.model.SchoolDAO;
import com.zyos.alert.query.model.Teacher;
import com.zyos.alert.query.model.TeacherDAO;
import com.zyos.alert.sac.model.DecanoFacultadDAO;
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
			return dao.findAllDivision();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public Faculty loadFacultyById(Long idFaculty) throws Exception{
		FacultyDAO dao = new FacultyDAO();
		try {
			return dao.findFacultyById(idFaculty);
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
	
	
	public List<ZyosUser> LoadZyosUserTunja() throws Exception{
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadUserList(1L);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public ZyosUser loadZyosUser(Long idZyosUser) throws Exception{
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.findZyosUser(idZyosUser);
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
			int a = dao.deleteDivision(idFaculty);
			if (a>0){
				retorno = true;
				dao.getSession().beginTransaction().commit();
			}
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
			int a = dao.deleteFaculty(idSchool);
			if (a>0){
				retorno = true;
				dao.getSession().beginTransaction().commit();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean deleteTeacher(Long idPersona) throws Exception{
		Boolean retorno=false;
		TeacherDAO dao = new TeacherDAO();
		try {
			int a=dao.deletePersona(idPersona);
			if (a>0){
				retorno = true;
				dao.getSession().beginTransaction().commit();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean deleteDecanoF(Long idPersona) throws Exception{
		Boolean retorno=false;
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			int a=dao.deleteDecFacultyBySchoolTunja(idPersona);
			if (a>0){
				retorno = true;
				dao.getSession().beginTransaction().commit();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean deleteDecanoD(Long idPersona) throws Exception{
		Boolean retorno=false;
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			int a=dao.deleteDecDivisionBySchoolTunja(idPersona);
			if (a>0){
				retorno = true;
				dao.getSession().beginTransaction().commit();
			}
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
			int a=dao.updateDivision(facultySelect);
			if (a>0){
				retorno = true;
				dao.getSession().beginTransaction().commit();
			}
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
			int a=dao.updateFaculty(schoolSelect);
			if (a>0){
				retorno = true;
				dao.getSession().beginTransaction().commit();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean createTeacher(Long idZyosUser,Long School, String User) throws Exception{
		Boolean retorno=false;
		TeacherDAO dao = new TeacherDAO();
		try {
			Teacher t = dao.FindTeacher(idZyosUser, School);
			if (t!= null){
				t.initializing(User, false);
				dao.getSession().update(t);
			} else {
				Long id = dao.searchIdTeacher();
				t = new Teacher((id+1), idZyosUser, School);
				t.initializing(User, true);
				dao.getSession().save(t);
			}
			dao.getSession().beginTransaction().commit();
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean createDecanoF(Long idZyosUser,Long School, String User) throws Exception{
		Boolean retorno=false;
		SchoolCoordinadorDAO dao = new SchoolCoordinadorDAO();
		try {
			SchoolCoordinador t = null;
			if ((t = dao.FindDFacultad(idZyosUser, School))!= null){
				t.initializing(User, false);
				dao.getSession().update(t);
			} else {
				Long id = dao.searchIdDFacultad();
				t = new SchoolCoordinador((id+1), idZyosUser, School);
				t.initializing(User, true);
				dao.getSession().save(t);
			}
			dao.getSession().beginTransaction().commit();
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean createDecanoD(Long idZyosUser,Long Faculty, String User) throws Exception{
		Boolean retorno=false;
		FacultyCoordinatorDAO dao = new FacultyCoordinatorDAO();
		try {
			FacultyCoordinator t = null;
			if ((t = dao.findDDivision(idZyosUser, Faculty))!= null){
				t.initializing(User, false);
				dao.getSession().update(t);
			} else {
				Long id = dao.searchIdDDivision();
				t = new FacultyCoordinator((id+1), idZyosUser, Faculty);
				t.initializing(User, true);
				dao.getSession().save(t);
			}
			dao.getSession().beginTransaction().commit();
			retorno = true;
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean saveSchool(School schoolSelect, String User) throws Exception{
		Boolean retorno=false;
		SchoolDAO dao = new SchoolDAO();
		try {
			if ((dao.findByNameFaculty(schoolSelect))== null){
				Long id = dao.findMaxIDFaculty();
				schoolSelect.setIdschool( (id+1) );
				schoolSelect.initializing(User, true);
				dao.getSession().save(schoolSelect);
				retorno = true;
			}
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
	
	public Boolean saveFaculty(Faculty facultySelect, String User) throws Exception{
		Boolean retorno=false;
		FacultyDAO dao = new FacultyDAO();
		try {
			if ((dao.findByNameDivision(facultySelect))== null){
				Long id = dao.findMaxIdDivision();
				facultySelect.setIdFaculty((id+1));
				facultySelect.initializing(User, true);
				dao.getSession().save(facultySelect);
				retorno = true;
			}
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return retorno;
	}
}
