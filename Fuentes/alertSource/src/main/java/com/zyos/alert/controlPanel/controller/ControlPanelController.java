package com.zyos.alert.controlPanel.controller;

import org.hibernate.Transaction;

import com.zyos.alert.controlPanel.model.ControlPanel;
import com.zyos.alert.controlPanel.model.ControlPanelDAO;
import com.zyos.core.common.controller.ZyosController;

public class ControlPanelController extends ZyosController{
	
	
	/** @autor jhernandez */
	public void saveOrUpdateMarginHour (ControlPanel controlPanel, String documentNumber) {
		
		ControlPanelDAO dao = new ControlPanelDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			
			controlPanel.initializing(documentNumber, true);			
			controlPanel.setPercentageRiskFactor(0D);
			controlPanel.setPercentageAssistance(0D);
			
			dao.getSession().saveOrUpdate(controlPanel);

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @autor jhernandez */
	public void saveOrUpdatePercentage (ControlPanel controlPanelRisk, String documentNumber) {
		
		ControlPanelDAO dao = new ControlPanelDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			
			controlPanelRisk.initializing(documentNumber, true);			
			controlPanelRisk.setMarginHour(0);
			controlPanelRisk.setPercentageAssistance(0D);
			
			dao.getSession().saveOrUpdate(controlPanelRisk);	

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @autor jhernandez */
	public void saveOrUpdatePercentageAssistance (ControlPanel controlPanelAssistance, String documentNumber) {
		
		ControlPanelDAO dao = new ControlPanelDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {

			
			controlPanelAssistance.initializing(documentNumber, true);			
			controlPanelAssistance.setMarginHour(0);
			controlPanelAssistance.setPercentageRiskFactor(0D);
			
			dao.getSession().saveOrUpdate(controlPanelAssistance);	

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @autor jhernandez */
	public ControlPanel loadCurrentMarginDays() throws Exception {
		ControlPanelDAO dao = new ControlPanelDAO();
		try {
			return dao.loadCurrentMarginDays();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @autor jhernandez */
	public ControlPanel loadCurrentPercentageRisk() throws Exception {
		ControlPanelDAO dao = new ControlPanelDAO();
		try {
			return dao.loadCurrentPercentageRisk();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** @autor jhernandez */
	public ControlPanel loadCurrentPercentageAssistance() throws Exception {
		ControlPanelDAO dao = new ControlPanelDAO();
		try {
			return dao.loadCurrentPercentageAssistance();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** SIAT-TUNJA */
	public ControlPanel loadCurrentBaseGradeTunja() throws Exception {
		ControlPanelDAO dao = new ControlPanelDAO();
		try {
			return dao.loadCurrentBaseGradeTunja();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** SIAT-TUNJA */
	public ControlPanel loadCurrentBaseGoodGradesTunja() throws Exception {
		ControlPanelDAO dao = new ControlPanelDAO();
		try {
			return dao.loadCurrentBaseGoodGradesTunja();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
}
