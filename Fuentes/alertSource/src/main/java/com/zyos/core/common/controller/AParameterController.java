package com.zyos.core.common.controller;

import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.facultyDegree.model.FacultyDegree;
import com.zyos.alert.studentReport.model.DegreeDAO;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.model.AParameter;
import com.zyos.core.common.model.ZyosParameterDAO;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;

public class AParameterController extends ZyosController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * It loads one type of enterprise parameters.
	 * 
	 * @param idEnterprise
	 *            Id Enterprise to search the parameters
	 * @param nameClass
	 *            Class name for identify the parameters, It must be an
	 *            AParameter
	 * @return
	 */
	public List<AParameter> loadParameterList(Long idEnterprise,
			String nameClass, int globalParameter) {
		ZyosParameterDAO dao = new ZyosParameterDAO();
		try {
			return dao.loadParameterList(idEnterprise, nameClass,
					globalParameter);
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void saveParameter(AParameter parameter) {
		ZyosParameterDAO dao = new ZyosParameterDAO();
		try {
			dao.getSession().save(parameter);
			dao.getSession().beginTransaction().commit();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void saveOrUpdateParameter(AParameter parameter) {
		ZyosParameterDAO dao = new ZyosParameterDAO();
		try {
			dao.getSession().saveOrUpdate(parameter);
			dao.getSession().beginTransaction().commit();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	
	
	/** @autor jhernandez */
	public List<String> validateDegreeUsed(List<Long> idDegree)
			throws Exception {
		DegreeDAO dao = new DegreeDAO ();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			return dao.validateDegreeUsed(idDegree);

		} catch (Exception e) {

			tx.rollback();
			ErrorNotificacion.handleErrorMailNotification(e, this);
			throw e;

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	//-------------degree-------------------------------------
	public List<FacultyDegree> loadParameterList() {
		ZyosParameterDAO dao = new ZyosParameterDAO();
		try {
			return dao.loadParameterListdegree();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<Faculty> loadFacultyByDegree(Long IdDegree){
		ZyosParameterDAO dao = new ZyosParameterDAO();
		try {
			return dao.loadFacultyByDegree(IdDegree);
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
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
	
	public void deleteParameter(FacultyDegree[] selectedFacultyDegreeList, String documentNumber){
		ZyosParameterDAO dao = null;
		try {
			dao = new ZyosParameterDAO();
			StringBuilder uil = new StringBuilder();
			StringBuilder uil2 = new StringBuilder();
			for (FacultyDegree o : selectedFacultyDegreeList) {
				uil.append(o.getIdDegree() + ",");
				uil2.append(o.getIdFacultyDegree() + ",");
			}
			dao.changeState(uil.toString() + "0", uil2.toString() + "0", documentNumber, IZyosState.INACTIVE);

			dao.getSession().beginTransaction().commit();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
}
