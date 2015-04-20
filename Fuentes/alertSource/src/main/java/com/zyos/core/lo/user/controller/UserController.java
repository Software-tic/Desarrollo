package com.zyos.core.lo.user.controller;

import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Transaction;

import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.util.security.RSA;
import com.zyos.core.lo.enterprise.model.ZyosUserEnterprise;
import com.zyos.core.lo.enterprise.model.ZyosUserEnterpriseDAO;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;
import com.zyos.core.lo.user.model.ZyosUserGroup;
import com.zyos.core.lo.user.model.ZyosUserGroupDAO;
import com.zyos.core.login.model.ZyosLogin;
import com.zyos.core.login.model.ZyosLoginDAO;
import com.zyos.session.common.User;

public class UserController extends ZyosController {

	public ZyosUser validateUserDocument(String documentNumber) {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.validateUserDocument(documentNumber);
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public boolean validateUserLogin(String userLogin) {
		ZyosLoginDAO dao = new ZyosLoginDAO();
		try {
			return dao.validateUserLogin(userLogin);
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * Method that save a new user
	 * 
	 * @param zyosUser
	 * @param zyosGroupList
	 * @return
	 */
	public void saveUser(ZyosUser zyosUser, List<Object> zyosGroupList) {
		ZyosUserDAO dao = new ZyosUserDAO();
		Transaction tx = dao.getSession().beginTransaction();
		try {
			zyosUser.setIdZyosGroup(Long.valueOf(zyosGroupList.get(0).toString()));
			dao.save(zyosUser);

			zyosUser.getZyosLogin().setIdZyosUser(zyosUser.getIdZyosUser());
			zyosUser.getZyosLogin().setPasswordMD5(RSA.encrypt((zyosUser.getZyosLogin().getPassword().toString().trim())));
			zyosUser.getZyosLogin().setDeadLine("2012/12/12");
			zyosUser.getZyosLogin().setFirstLogin(0);
			zyosUser.getZyosLogin().initializing(zyosUser.getUserCreation(), true);

			dao.getSession().save(zyosUser.getZyosLogin());

			for (Object zg : zyosGroupList) {
				dao.getSession().save(new ZyosUserGroup(zyosUser.getIdZyosUser(), Long.valueOf(zg.toString()), zyosUser.getUserCreation(), true));
			}
			dao.getSession().save(new ZyosUserEnterprise(zyosUser.getIdZyosUser(), zyosUser.getIdEnterprise(), zyosUser.getUserCreation(), true));

			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<Object> loadZyosGroupListByUser(Long idUser) {
		ZyosUserGroupDAO dao = new ZyosUserGroupDAO();
		try {
			return dao.loadZyosGroupListByUser(idUser);
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * Method that edit a user
	 * 
	 */
	public void editUser(ZyosUser zu, List<Object> zyosGroupList, String documentNumber) {
		ZyosUserDAO dao = new ZyosUserDAO();
		ZyosLoginDAO dao2 = new ZyosLoginDAO();
		try {

			for (Object o : zyosGroupList)
				zu.setIdZyosGroup(Long.valueOf(o.toString()));

			dao.editUser(zu, true, zu.getUserChange());
			dao.deleteZyosUserGroup(zu.getIdZyosUser());


			for (Object o : zyosGroupList)
				dao.getSession().save(new ZyosUserGroup(zu.getIdZyosUser(), Long.valueOf(o.toString()), zu.getDateChange(), true));

			boolean pass = false;
			if (zu.getZyosLogin().getPassword() != null && !zu.getZyosLogin().getPassword().isEmpty()
				&& zu.getZyosLogin().getAuthMode().equals("application")) {
				zu.getZyosLogin().setPasswordMD5(RSA.encrypt(zu.getZyosLogin().getPassword().toString().trim()));
				pass = true;
			}

			zu.getZyosLogin().setIdZyosUser(zu.getIdZyosUser());
			zu.getZyosLogin().initializing(documentNumber, false);

			dao2.updateZyosLogin(zu.getZyosLogin(), pass);

			dao.getSession().beginTransaction().commit();
			dao2.getSession().beginTransaction().commit();


		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void deleteUser(ZyosUser[] selectedZyosUserList, String documentNumber) {
		ZyosUserDAO dao = null;
		try {
			dao = new ZyosUserDAO();
			StringBuilder uil = new StringBuilder();
			for (ZyosUser zyosUser : selectedZyosUserList) {
				uil.append(zyosUser.getIdZyosUser());
				uil.append(",");
			}
			dao.changeStateUser(uil.toString() + "0", documentNumber, IZyosState.INACTIVE);

			dao.getSession().beginTransaction().commit();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	private Long idZyosUser = null;
	private User userSession = null;
	private ZyosUser zyosUser = null;
	private boolean isNew = false;
	private List<String> addedSP;
	private List<String> deletedSP;

	public List<SelectItem> queryUserByEnterprise(Long idSP, Long idOZ, Long idZG) {
		ZyosUserDAO zyosUserDAO = new ZyosUserDAO();
		try {
			return zyosUserDAO.findBySP(idSP, idOZ, idZG);
		} catch (Exception e) {

			return null;
		} finally {
			zyosUserDAO.getSession().close();
			zyosUserDAO = null;
		}
	}

	public List<SelectItem> loadEnterpriseByUser(Long idZU) {
		ZyosUserEnterpriseDAO dao = new ZyosUserEnterpriseDAO();
		try {
			return dao.getListEnterpriseByUser(idZU);
		} catch (Exception e) {
			return null;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<ZyosUser> loadUserListByEnterprise(Long idE, Long idZG) throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadUserListByEnterprise(idE, idZG);
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public ZyosUser saveUserAdmin(boolean isNew, String nameUser, String lastNameUser, String emailUser, String documentNumber, String phone,
		Long idDocumentType, String password, String passwordMD5, String userLogin) {
		ZyosUserDAO userDAO = new ZyosUserDAO();
		ZyosLoginDAO zyosLoginDAO = new ZyosLoginDAO();
		ZyosUser zyosUser = null;
		ZyosLogin zyosLogin = null;
		this.isNew = isNew;
		try {
			zyosUser = new ZyosUser();
			zyosLogin = new ZyosLogin();

			Object o = userDAO.validateUserDocument(documentNumber);
			if (isNew) {
				if (o != null) {
					userDAO.getSession().close();
					return null;
				}
				zyosUser.initializing(documentNumber, isNew);
				zyosUser.setDocumentNumber(documentNumber);
				zyosUser.setName(nameUser.toString().trim());
				zyosUser.setLastName(lastNameUser.trim());
				zyosUser.setPhone(phone.toString());
				zyosUser.setEmail(emailUser.trim());
				zyosUser.setIdProfession(Long.valueOf(1));
				zyosUser.setIdDocumentType(Long.valueOf(1));
				zyosUser.setIdZyosGroup(IZyosGroup.ADMINISTRATOR);
				zyosUser.setIdDocumentType(idDocumentType);
				userDAO.save(zyosUser);
				userDAO.getSession().beginTransaction().commit();
				userDAO.getSession().close();

				zyosLogin.initializing(documentNumber, isNew);
				zyosLogin.setIdZyosUser(zyosUser.getIdZyosUser());
				zyosLogin.setPassword(password);
				zyosLogin.setPasswordMD5(passwordMD5);
				zyosLogin.setUserLogin(userLogin);
				zyosLogin.setFirstLogin(1);
				zyosLoginDAO.save(zyosLogin);
				zyosLoginDAO.getSession().beginTransaction().commit();
				zyosLoginDAO.getSession().close();
			}
			return zyosUser;
		} catch (Exception e) {

			System.out.println("ERROR UserController - saveUser " + e);
			return null;
		} finally {
			userDAO.getSession().close();
			zyosLoginDAO.getSession().close();
			userDAO = null;
			zyosLoginDAO = null;
			zyosUser = null;
			zyosLogin = null;
		}
	}

	public void addZyosUserEnterprise(Long idZyosUser, Long idEnterprise, String user) {
		ZyosUserEnterpriseDAO zyosUserEnterpriseDAO = new ZyosUserEnterpriseDAO();
		ZyosUserEnterprise zyosUserEnterprise = new ZyosUserEnterprise();
		try {
			zyosUserEnterprise.initializing(user, true);
			zyosUserEnterprise.setIdEnterprise(idEnterprise);
			zyosUserEnterprise.setIdZyosUser(idZyosUser);
			Transaction tx = zyosUserEnterpriseDAO.getSession().beginTransaction();
			zyosUserEnterpriseDAO.save(zyosUserEnterprise);
			tx.commit();
		} catch (Exception e) {

		} finally {
			zyosUserEnterpriseDAO.getSession().close();
			zyosUserEnterpriseDAO = null;
			zyosUserEnterprise = null;
		}
	}

	public void addZyosUserGroup(Long idZyosUser, Long idZyosGroup, String user) {
		ZyosUserGroupDAO zyosUserGroupDAO = new ZyosUserGroupDAO();
		ZyosUserGroup zyosUserGroup = new ZyosUserGroup();
		try {
			zyosUserGroup.initializing(user, true);
			zyosUserGroup.setIdGroup(idZyosGroup);
			zyosUserGroup.setIdZyosUser(idZyosUser);
			Transaction tx = zyosUserGroupDAO.getSession().beginTransaction();
			zyosUserGroupDAO.save(zyosUserGroup);
			tx.commit();
		} catch (Exception e) {

		} finally {
			zyosUserGroupDAO.getSession().close();
			zyosUserGroupDAO = null;
			zyosUserGroup = null;
		}

	}

	public boolean editUserData(ZyosUser data, String documentNumberTemp, String userChange) {
		ZyosUserDAO userDAO = new ZyosUserDAO();
		Transaction tx = null;
		try {
			boolean success = userDAO.editDataUser(data, false, userChange);
			tx = userDAO.getSession().beginTransaction();
			tx.commit();
			return success;
		} catch (Exception e) {

			return false;
		} finally {
			userDAO.getSession().close();
			tx = null;
			userDAO = null;
		}
	}

	public ZyosUser loadDataUser(String zyosUserSession) {
		ZyosUserDAO userDAO = new ZyosUserDAO();
		try {
			return userDAO.loadDataUser(zyosUserSession);
		} catch (Exception e) {
			return null;
		} finally {
			userDAO.getSession().close();
			userDAO = null;
		}
	}

}
