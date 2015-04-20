package com.zyos.core.common.controller;

import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.calification.model.CalificationitemDAO;
import com.zyos.alert.calification.model.Evaluation;
import com.zyos.alert.studentReport.model.DegreeDAO;
import com.zyos.alert.studentReport.model.RiskFactorDAO;
import com.zyos.core.common.model.AParameter;
import com.zyos.core.common.model.ZyosParameterDAO;

public class AParameterController extends ZyosController {

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
	
	
	
	
}
