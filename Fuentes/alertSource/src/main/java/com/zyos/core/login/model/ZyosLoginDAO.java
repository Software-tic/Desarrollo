package com.zyos.core.login.model;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.common.util.auth.StartTlsSimple;
import com.zyos.core.common.util.security.RSA;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.session.common.User;

/**
 * A data access object (DAO) providing persistence and search support for ZyosLogin entities.
 * Transaction control of the save(), update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how to configure it for
 * the desired type of transaction control.
 * 
 * @see com.zyos.core.login.model.ZyosLogin
 * @author MyEclipse Persistence Tools
 */

public class ZyosLoginDAO extends OracleBaseHibernateDAO {

	public void save(ZyosLogin transientInstance) {

		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(ZyosLogin persistentInstance) {
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public boolean validateUserLogin(String userLogin) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select id from ZyosLogin where userLogin = :userLogin");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("userLogin", userLogin);
			qo.setMaxResults(1);

			return qo.uniqueResult() != null ? true : false;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public com.zyos.session.common.User validateUser(String user, String _password, String idS, Long idZyosGroup) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select authMode from ZyosLogin where userLogin =:user and state =:state");
			qo = getSession().createQuery(hql.toString());

			qo.setParameter("user", user);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setMaxResults(1);

			RSA rsa = new RSA();
			String dpass = RSA.decrypt(_password);

			Object o = qo.uniqueResult();
			boolean auth_ldap_enable = false;
			if (o != null) {
				auth_ldap_enable = o.toString().equals("ldap") ? true : false;
			}

			System.out.println("Validate if is ldap auth enable: " + auth_ldap_enable);
			if (auth_ldap_enable) {
				StartTlsSimple s = new StartTlsSimple();
				System.out.println("Validating LDAP connection for " + user);
				if (!s.validateUser(user, dpass)) {
					System.out.println("Fail LDAP auth for " + user);
					return null;
				}
				System.out.println("LDAP auth was successfull for " + user);
			}

			hql = new StringBuilder();

			hql.append("select new com.zyos.session.common.User( ");
			hql.append("zu.idZyosUser,   zu.documentNumber,   zu.name,   zu.lastName,   zl.firstLogin,   zu.email,   zg.id,   zg.name,   zl.passwordMD5,   e.id,   zl.password, e.name) ");
			hql.append("from ");
			hql.append("ZyosUser zu,   ZyosLogin zl, ");
			hql.append("Enterprise e,  ZyosUserGroup zug, ZyosGroup zg ");
			hql.append("where ");
			hql.append("zl.userLogin = :login ");

			if (!auth_ldap_enable)
				hql.append("and zl.password = :MD5 ");

			hql.append("and zl.idZyosUser = zu.id ");
			hql.append("and zu.state =:state and zl.state =:state and e.state =:state and zg.state =:state ");
			hql.append("and zu.idEnterprise = e.id ");
			hql.append("and zug.idZyosUser = zu.id ");
			hql.append("and zug.idGroup = zg.id ");
			hql.append("and zg.id = ");
			hql.append(idZyosGroup == null ? " zu.idZyosGroup " : idZyosGroup);
			hql.append(" group by ");
			hql.append(" zu.idZyosUser,   zu.documentNumber,   zu.name,   zu.lastName,   zl.firstLogin,   zu.email,   zg.id,   zg.name,   zl.passwordMD5,   e.id,   zl.password, e.name ");
			hql.append(" order by ");
			hql.append(" e.name ");

			qo = getSession().createQuery(hql.toString());

			if (!auth_ldap_enable)
				qo.setParameter("MD5", dpass);

			qo.setParameter("login", user);
			qo.setParameter("state", IZyosState.ACTIVE);

			List<User> userList = qo.list();
			qo = null;

			User u = null;
			if (!userList.isEmpty()) {
				u = userList.get(0);
				if (userList.size() > 1) {
					u.setSelectEnterprise(true);
					for (int i = 1; i < userList.size(); i++) {
						User temp = userList.get(i);
						u.getListEnterprise().add(temp.getListEnterprise().get(0));
					}
				}

				hql = new StringBuilder();
				hql.append("select distinct  ");
				hql.append("zug.idGroup ");
				hql.append("from ");
				hql.append("ZyosUserGroup zug ");
				hql.append("where ");
				hql.append("zug.idZyosUser =:idZU and zug.state=:state order by 1 ");

				qo = null;
				qo = getSession().createQuery(hql.toString());

				qo.setParameter("idZU", u.getId());
				qo.setParameter("state", IZyosState.ACTIVE);

				u.setListGroup(qo.list());
				u.setIdSession(idS);
				u.setDateLogin(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
				u.setHourLogin(ManageDate.getCurrentHour(ManageDate.HH_MM_SS));
				HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				String ip = httpServletRequest.getRemoteAddr();
				System.out.println(ip);

				hql = new StringBuilder();
				hql.append("select distinct  ");
				hql.append("ze.idEnterprise ");
				hql.append("from ");
				hql.append("ZyosUserEnterprise ze ");
				hql.append("where ");
				hql.append("ze.idZyosUser =:idZU and ze.state=:state order by 1 ");

				qo = null;
				qo = getSession().createQuery(hql.toString());

				qo.setParameter("idZU", u.getId());
				qo.setParameter("state", IZyosState.ACTIVE);

				u.setListEnterprise(qo.list());

				return u;

				// }
			}
			return null;
		} catch (Exception e) {
			System.out.println("Invalid user in DAO");
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public boolean validateOldPassword(String oldPassword, String newPassword, Long idZU, String userChange) {
		StringBuilder hql = new StringBuilder();
		try {

			hql.append("update ");
			hql.append("ZyosLogin  ");
			hql.append("set ");
			hql.append("passwordMD5 = :md5 , ");
			hql.append("password = :newPassword, dateChange = :dateChange, ");
			hql.append("userChange = :userChange  ");
			hql.append("where ");
			hql.append("idZyosUser = :idZU ");
			hql.append("and   passwordMD5 = :oldPassword ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("md5", RSA.encrypt(newPassword));
			qo.setParameter("newPassword", newPassword);
			qo.setParameter("dateChange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
			qo.setParameter("userChange", userChange);
			qo.setParameter("idZU", idZU);
			qo.setParameter("oldPassword", RSA.encrypt(oldPassword));

			int result = qo.executeUpdate();
			this.getSession().beginTransaction().commit();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			this.getSession().close();
			return false;
		} finally {
			this.getSession().close();
		}
	}

	public boolean updateAceptLicense(Long id, String documentNumber) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update ZyosLogin ");
			hql.append("set firstLogin = :firstLogin ,");
			hql.append("userChange = :userChange ,");
			hql.append("dateChange = :newdateChange ");
			hql.append("where idZyosUser = :idZU");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("firstLogin", 0);
			qo.setParameter("idZU", id);
			qo.setParameter("userChange", documentNumber);
			qo.setParameter("newdateChange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));

			int rowCount = qo.executeUpdate();
			this.getSession().beginTransaction().commit();
			if (rowCount > 0) {
				return true;
			}
			return false;

		} catch (Exception e) {
			return false;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public void updatePassword(Object idZU, Object password, String passwordMD5, String documentNumber, String userLogin) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update ZyosLogin set ");
			if (!password.toString().isEmpty()) {
				hql.append("password=:password,passwordMD5=:passwordMD5,");
			}

			hql.append("userLogin = :userLogin, ");
			hql.append("userChange = :userChange ,");
			hql.append("dateChange = :newdateChange ,");
			hql.append("hourChange = :newhourChange ");
			hql.append("where idZyosUser = :idZU");

			qo = getSession().createQuery(hql.toString());
			if (!password.toString().isEmpty()) {
				qo.setParameter("password", password);
				qo.setParameter("passwordMD5", passwordMD5);
			}
			qo.setParameter("idZU", idZU);
			qo.setParameter("userLogin", userLogin);
			qo.setParameter("userChange", documentNumber);
			qo.setParameter("newdateChange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
			qo.setParameter("newhourChange", ManageDate.getCurrentHour(ManageDate.HH_MM_SS));

			qo.executeUpdate();
			this.getSession().beginTransaction().commit();

		} catch (Exception e) {
			System.out.println("ERROR: %% ZyosLoginDAO %% updatePassword " + e);
		} finally {
			hql = null;
			qo = null;
		}
	}

	/** @author jhernandez 16/07/2014 12:35:58 */
	public void updateZyosLogin(ZyosLogin zyosLogin, boolean updatePass) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update ZyosLogin set ");
			if (updatePass) {
				hql.append(" password=:password, passwordMD5=:passwordMD5, ");
			}

			hql.append("userLogin = :userLogin, ");
			hql.append("userChange = :userChange , ");
			hql.append("dateChange = :dateChange, ");
			hql.append(" authMode =:authMode ");
			hql.append(" where idZyosUser =:idZU ");

			qo = getSession().createQuery(hql.toString());
			if (updatePass) {
				qo.setParameter("password", zyosLogin.getPassword());
				qo.setParameter("passwordMD5", zyosLogin.getPasswordMD5());
			}
			qo.setParameter("idZU", zyosLogin.getIdZyosUser());
			qo.setParameter("userLogin", zyosLogin.getUserLogin());
			qo.setParameter("userChange", zyosLogin.getUserChange());
			qo.setParameter("dateChange", zyosLogin.getDateChange());
			qo.setParameter("authMode", zyosLogin.getAuthMode());

			qo.executeUpdate();
			this.getSession().beginTransaction().commit();

		} catch (Exception e) {
			this.getSession().close();
		} finally {
			hql = null;
			qo = null;
		}
	}

}
