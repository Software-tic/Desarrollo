package com.zyos.alert.query.controller;

import java.util.List;

import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ObservationDAO;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.model.ZyosParameterDAO;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;

public class QueryController extends ZyosController {

	public List<ZyosUser> getStudentList() throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadUserList(1l);
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<Subject> lodInfoToShow(ZyosUser zu) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.lodInfoToShow(zu.getIdStudent());
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<Observation> lodInfoObservationToShow(ZyosUser zu) throws Exception {
		ObservationDAO dao = new ObservationDAO();
		try {
			return dao.loadObservationByTeacher(zu.getIdZyosUser());
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<Faculty> getFacultiesList(){
		ZyosParameterDAO dao = new ZyosParameterDAO();
		try {
			return dao.loadFacultyList();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<ZyosUser> getUserPAAIList() throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadUserPAAIList(1l,"PAAI");
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

}
