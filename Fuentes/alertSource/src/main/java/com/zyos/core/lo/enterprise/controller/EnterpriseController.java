package com.zyos.core.lo.enterprise.controller;

import java.util.List;

import com.zyos.core.lo.enterprise.model.Enterprise;
import com.zyos.core.lo.enterprise.model.EnterpriseDAO;
import com.zyos.session.common.User;

public class EnterpriseController {

	public List<Enterprise> loadEnterpriseList() throws Exception {
		EnterpriseDAO dao = new EnterpriseDAO();
		try {
			return dao.loadEnterpriseList();
		} catch (Exception e) {
			dao.getSession().cancelQuery();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public Enterprise loadDataEnterprise(Long idEnterprise) throws Exception {
		EnterpriseDAO dao = new EnterpriseDAO();

		try {
			return dao.loadDataEnterprise(idEnterprise);

		} catch (Exception e) {
			dao.getSession().cancelQuery();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void updateEnterprise(Enterprise enterprise, User userSession)
			throws Exception {
		EnterpriseDAO dao = new EnterpriseDAO();
		try {
			enterprise.initializing(userSession.getDocumentNumber(), true);
			dao.updateEnterprise(enterprise);
			dao.getSession().beginTransaction().commit();

		} catch (Exception e) {
			dao.getSession().cancelQuery();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}

	}

	public void saveEnterprise(Enterprise enterprise, User userSession)
			throws Exception {
		EnterpriseDAO dao = new EnterpriseDAO();
		try {

			enterprise.initializing(userSession.getDocumentNumber(), true);
			dao.save(enterprise);
			dao.getSession().beginTransaction().commit();
			
		}catch (Exception e) {
			dao.getSession().cancelQuery();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}

	}

	public Enterprise validateNIT(String idEnterprise) throws Exception{
		EnterpriseDAO dao = new EnterpriseDAO();
		try{
			return dao.validateNIT(idEnterprise);
		}catch (Exception e) {
			dao.getSession().cancelQuery();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
		
	}

	public void deleteEnterprise(List<Enterprise> enterpriseListSelected,
			User userSession)throws Exception {
		
			EnterpriseDAO dao = new EnterpriseDAO();
			try {
				
				for (Enterprise enterprise : enterpriseListSelected) {
					enterprise.initializing(userSession.getDocumentNumber(), true);
					dao.deleteEnterprise(enterprise);
				}
				
				dao.getSession().beginTransaction().commit();

			} catch (Exception e) {
				dao.getSession().cancelQuery();
				throw e;
			} finally {
				dao.getSession().close();
				dao = null;
			}
		
	}


}
