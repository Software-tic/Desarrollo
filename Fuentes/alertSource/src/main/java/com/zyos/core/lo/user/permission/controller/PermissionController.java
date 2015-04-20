package com.zyos.core.lo.user.permission.controller;

import java.util.List;

import com.zyos.alert.studentReport.model.Stage;
import com.zyos.alert.studentReport.model.StageDAO;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.login.model.ZyosGroupTree;
import com.zyos.core.login.model.ZyosGroupTreeDAO;
import com.zyos.session.common.User;

public class PermissionController extends ZyosController {

	public List<ZyosGroupTree> loadTreeByZyosGroup(Long idEnterprise, Long level,
			Long idBranch, Long idZyosGroup) throws Exception {
		ZyosGroupTreeDAO dao = new ZyosGroupTreeDAO();
		try {
			return dao.loadTreeByZyosGroup(idEnterprise, level, idBranch, idZyosGroup);
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e; 
		} finally{
			dao.getSession().close();
			dao = null;
		}
	}

	public void updateFuncionalityList(Long idZGT, String funcionalityList, User userSession) throws Exception {
		ZyosGroupTreeDAO dao = new ZyosGroupTreeDAO();
		try {
			dao.updateFuncionalityList(idZGT, funcionalityList, userSession);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e; 
		} finally{
			dao.getSession().close();
			dao = null;
		}
	}

	public void handleZyosGroupLock(ZyosGroupTree zgt) throws Exception {
		ZyosGroupTreeDAO dao = new ZyosGroupTreeDAO();
		try {
			dao.handleZyosGroupLock(zgt);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e; 
		} finally{
			dao.getSession().close();
			dao = null;
		}
		
	}	
	
	
	public void updateStagePermission(String documentNumber, Stage stage) throws Exception {
		StageDAO dao = new StageDAO();
		try {
			stage.initializing(documentNumber, false);
			dao.updateStagePermission(stage);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e; 
		} finally{
			dao.getSession().close();
			dao = null;
		}
	}

}
