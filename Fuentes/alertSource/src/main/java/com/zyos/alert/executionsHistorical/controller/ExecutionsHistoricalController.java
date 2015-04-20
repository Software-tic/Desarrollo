package com.zyos.alert.executionsHistorical.controller;

import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistoricalDAO;
import com.zyos.core.common.controller.ZyosController;

public class ExecutionsHistoricalController extends ZyosController{
	
	
	/** @autor jhernandez */
	public void saveExecutionsHistorical(String documentNumber, ExecutionsHistorical execution) {
		ExecutionsHistoricalDAO dao = new ExecutionsHistoricalDAO();		

		Transaction tx = dao.getSession().beginTransaction();
		try {

			execution.initializing(documentNumber, true);
			dao.save(execution);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	
	/** @author jhernandez*/
	public List<ExecutionsHistorical> loadAutomaticAlertList() throws Exception {
		ExecutionsHistoricalDAO dao = new ExecutionsHistoricalDAO();
		try {
			return dao.loadAutomaticAlertList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	} 
	
	/** @author jhernandez*/
	public List<ExecutionsHistorical> loadExecutionCalificatioMoodleList() throws Exception {
		ExecutionsHistoricalDAO dao = new ExecutionsHistoricalDAO();
		try {
			return dao.loadExecutionCalificatioMoodleList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	

}
