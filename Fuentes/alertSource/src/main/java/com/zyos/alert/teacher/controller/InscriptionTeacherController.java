package com.zyos.alert.teacher.controller;

import org.hibernate.Transaction;

import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;
import com.zyos.core.lo.user.model.ZyosUserGroup;
import com.zyos.core.lo.user.model.ZyosUserGroupDAO;
import com.zyos.core.login.model.ZyosLogin;
import com.zyos.core.login.model.ZyosLoginDAO;


public class InscriptionTeacherController extends ZyosController{

	private static final long serialVersionUID = 1L;

	public void saveTeacherZyosLogin(ZyosLogin zyosLogin, String documentNumber, ZyosUser zyosUser) throws Exception {
	
		ZyosLoginDAO dao = new ZyosLoginDAO();
		ZyosUserDAO daos = new ZyosUserDAO();
		ZyosUserGroupDAO daoss = new ZyosUserGroupDAO();
		
		Transaction tx = null;
		try {
			tx = dao.getSession().beginTransaction();
			
			ZyosUserGroup zyosUserGoup = new ZyosUserGroup();
			
			zyosUserGoup.setIdGroup(IZyosGroup.TEACHER);
			zyosUserGoup.setIdZyosUser(zyosUser.getIdZyosUser());

			zyosUserGoup.initializing(documentNumber, true);
			zyosLogin.initializing(documentNumber, true);			
			
					
			daoss.save(zyosUserGoup);
			dao.save(zyosLogin);
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
	
	   public ZyosUser validateZyosUser(String document) throws Exception{
			ZyosUserDAO dao = new ZyosUserDAO();
			try {
				 return dao.validateZyosUserTeacher(document);
			} catch (Exception e) {
			    dao.getSession().beginTransaction().rollback();
				throw e;
			} finally {
				dao.getSession().close();
				dao = null;
			}
		}  
	   
	   
	   public String validateDuplicateUser(String email) throws Exception{
			ZyosUserDAO dao = new ZyosUserDAO();
			try {
				 return dao.validateDuplicateUser(email);
			} catch (Exception e) {
			    dao.getSession().beginTransaction().rollback();
				throw e;
			} finally {
				dao.getSession().close();
				dao = null;
			}
		}  



	

}
