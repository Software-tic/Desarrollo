package com.zyos.core.lo.user.controller;

import java.util.List;

import org.hibernate.Transaction;

import com.zyos.alert.studentReport.model.Stage;
import com.zyos.alert.studentReport.model.StageDAO;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosGroupDAO;
import com.zyos.core.login.model.ZyosGroupTree;

public class ZyosGroupController extends ZyosController {

	public List<ZyosGroup> loadZyosGroupList() throws Exception {
		ZyosGroupDAO dao = new ZyosGroupDAO();
		try {
			return dao.findAll();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void addZyosGroup(ZyosGroup c, Long idEnterprise) throws Exception {
		ZyosGroupDAO dao = new ZyosGroupDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			dao.save(c);
			dao.getSession().flush();

			if (c != null && c.getId() != null) {
				// create default tree structure
				List<ZyosGroupTree> zgtl = dao
						.loadAndBuildTreeEnterpriseStructure(idEnterprise);

				int count = 1;

				for (ZyosGroupTree zgt : zgtl) {
					zgt.initializing(c.getUserCreation(), true);
					zgt.setIdZyosGroup(c.getId());

					dao.save(zgt);

					if (count++ % 10 == 0) {
						dao.getSession().flush();
					}
				}
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
			tx = null;
		}
	}

	public void editZyosGroup(ZyosGroup c) throws Exception {
		ZyosGroupDAO dao = new ZyosGroupDAO();
		try {
			dao.getSession().saveOrUpdate(c);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void deleteZyosGroup(String user, ZyosGroup... ca) throws Exception {
		ZyosGroupDAO dao = new ZyosGroupDAO();
		StageDAO daos = new StageDAO();
		try {
			for (ZyosGroup c : ca) {
				c.initializing(user, false);
				c.setState(IZyosState.INACTIVE);
				dao.getSession().saveOrUpdate(c);			
				
			}
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
		/**@author jhernandez*/
	public void addStage(String document, Stage stage ) throws Exception {
		StageDAO dao = new StageDAO();
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			stage.initializing(document, true);
			dao.save(stage);
			tx.commit();
			
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			tx = null;
			dao = null;
		}
	}	
			
		/**@author jhernandez*/
		public void editStage(String document, Stage stage) throws Exception {
			StageDAO dao = new StageDAO();
			Transaction tx = null;
			try {
				tx = dao.getSession().beginTransaction();
				stage.initializing(document, false);
				dao.editStage(stage);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw e;
			} finally {
				dao.getSession().close();
				tx = null;
				dao = null;
			}
		}
		
		/**@author jhernandez*/
		public void deleteStage(String document, List<Long> idStageList) throws Exception {
			StageDAO dao = new StageDAO();
			Transaction tx = null;
			try {
				
				tx = dao.getSession().beginTransaction();										
				dao.deleteStage(document, idStageList);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw e;
			} finally {
				dao.getSession().close();
				tx = null;
				dao = null;
			}
		}
	
	
	

}
