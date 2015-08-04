package com.zyos.core.lo.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.zyos.alert.calification.model.Evaluation;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * ZyosUser entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.copy.lo.mu.model.ZyosUser
 * @author MyEclipse Persistence Tools
 */

public class ZyosUserDAO extends OracleBaseHibernateDAO {

	public void save(ZyosUser transientInstance) {

		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(ZyosUser persistentInstance) {

		try {
			getSession().delete(persistentInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public List<ZyosUser> loadUserListByEnterprise(Long idE, Long idZG)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select new ZyosUser( ");
			hql.append("zu.id, ");
			hql.append("zu.name, ");
			hql.append("zu.lastName, ");
			hql.append("zu.documentNumber, ");
			hql.append("zg.id, ");
			hql.append("zg.name, zu.email, zl.userLogin, zu.phone, zu.mobilePhone, zu.secondEmail, zu.idDocumentType, zu.state, zl.authMode) ");
			hql.append("from ");
			hql.append("ZyosUser zu, ");
			hql.append("ZyosUserEnterprise zue, ");
			hql.append("ZyosGroup zg, ");
			hql.append("Enterprise e, ZyosLogin zl ");
			hql.append("where ");
			hql.append("(zu.state =:state ");
			hql.append("or zu.state =:disabledState) ");
			hql.append("and zu.documentNumber is not null ");

			hql.append("and zu.idZyosGroup = zg.id   ");
			// hql.append("and zu.idLine = l.id   ");
			// hql.append("and zu.idRegion = r.id   ");

			hql.append("and zu.id = zue.idZyosUser ");
			hql.append("and zue.idEnterprise = e.id ");
			hql.append("and e.id = :idE ");
			hql.append("and zu.id = zl.idZyosUser ");
			hql.append("and e.state =:state ");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("idE", idE);

			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("disabledState", IZyosState.DISABLED);

			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public void editUser(ZyosUser data, boolean isFullEdition, String userChange) {
		StringBuilder hql = new StringBuilder();
		Query query = null;
		try {
			hql.append("update ZyosUser ");
			hql.append("set phone = :newPhone, ");
			if (isFullEdition) {
				hql.append("documentNumber = :documentNumber, ");
				hql.append("idProfession = :idPost, ");
				hql.append("idDocumentType =:idDocumentType, ");
				hql.append("name =:name, ");
				hql.append("lastName =:lastName, ");
				hql.append("idZyosGroup =:idZyosGroup, ");
				hql.append("state =:state, ");
			}
			hql.append("mobilePhone = :newMobilePhone , email = :newEmail ,");
			hql.append("secondEmail = :newSecondEmail ,");
			hql.append("dateChange = :newdateChange,");
			hql.append("userChange = :userChange ");
			hql.append("where id = :idZU");

			query = getSession().createQuery(hql.toString());
			if (isFullEdition) {
				query.setParameter("documentNumber", data.getDocumentNumber());
				query.setParameter("idPost", data.getIdProfession());
				query.setParameter("idDocumentType", data.getIdDocumentType());
				query.setParameter("name", data.getName());
				query.setParameter("lastName", data.getLastName());
				query.setParameter("state", data.getState());
				query.setParameter("idZyosGroup", data.getIdZyosGroup());
			}
			query.setParameter("idZU", data.getIdZyosUser());
			query.setParameter("newPhone", data.getPhone());
			query.setString("newMobilePhone", data.getMobilePhone());
			query.setString("newEmail", data.getEmail());
			query.setString("newSecondEmail", data.getSecondEmail());
			query.setString("newdateChange",
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
			query.setParameter("userChange", userChange);

			int rowCount = query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			query = null;
		}
	}

	public void changeStateUser(String idUserList, String documentNumber,
			Long state) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update ZyosUser ");
			hql.append("set state = :state ,");
			hql.append("userChange = :userChange ,");
			hql.append("dateChange = :newdateChange ");
			hql.append("where idZyosUser in (");
			hql.append(idUserList);
			hql.append(" )");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("state", state);
			qo.setParameter("userChange", documentNumber);
			qo.setParameter("newdateChange",
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));

			qo.executeUpdate();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public Long findLastCreateUser(Object documentNumber) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select zu.id from ");
			hql.append("ZyosUser zu ");
			hql.append("where ");
			hql.append("zu.documentNumber = :idZU and zu.state = :state");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("idZU", documentNumber);
			qo.setParameter("state", IZyosState.ACTIVE);

			qo.setMaxResults(1);
			return (Long) qo.list().get(0);

		} catch (Exception e) {
			System.out.println("User not exist");
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public ArrayList<SelectItem> findBySP(Long idSP, Long idOZ, Long idZG)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select distinct new javax.faces.model.SelectItem(zu.id, zu.name || ' ' || zu.lastName) ");
			hql.append("from ");
			if (idOZ != null) {
				hql.append(" ZyosUserOperationZone zuoz, ");
			}
			if (idSP != null) {
				hql.append(" ZyosUserServicePoint usp, ");
			}
			hql.append("ZyosUser zu, ZyosUserGroup zug ");
			hql.append("where ");
			if (idOZ != null) {
				hql.append("zuoz.idOperationZone = :idOZ and ");
				hql.append("zuoz.idZyosUser = zu.id and ");
			}
			if (idSP != null) {
				hql.append("usp.idServicePoint = :idSP and  ");
				hql.append("usp.idZyosUser = zu.id and ");
			}
			hql.append("zug.idZyosUser = zu.id and ");
			hql.append("zug.idGroup >= :idZG and ");

			hql.append("zu.state = :state ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			if (idSP != null) {
				qo.setParameter("idSP", idSP);
			}
			if (idOZ != null) {
				qo.setParameter("idOZ", idOZ);
			}
			qo.setParameter("idZG", idZG);

			hql = null;
			ArrayList<SelectItem> list = (ArrayList<SelectItem>) qo.list();
			qo = null;

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
		}
	}

	public String validateForgotPassword(String userMail, String userName,
			String randomPassword) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select zu.id, zu.name||' '||zu.lastName ");
			hql.append("from ZyosUser zu ");
			hql.append("where zu.email = :userMail and  ");
			hql.append("zu.documentNumber = :userName and ");
			hql.append("zu.state = :state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("userMail", userMail.trim());
			qo.setParameter("userName", userName.trim());

			hql = new StringBuilder();
			qo.setMaxResults(1);
			Object[] user = (Object[]) qo.uniqueResult();
			if (user != null) {
				hql.append("update ZyosLogin ");
				hql.append("set password = :password ,");
				hql.append("passwordMD5 = :passwordMD5 ,");
				hql.append("dateChange = :newdateChange ");
				// hql.append("hourChange = :newhourChange ");
				hql.append("where idZyosUser = :idZU");

				qo = getSession().createQuery(hql.toString());

				qo.setParameter("idZU", Long.valueOf(user[0].toString()));
				qo.setParameter("password", " ");
				qo.setParameter("passwordMD5", randomPassword);
				qo.setParameter("newdateChange",
						ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
				/*
				 * qo.setParameter("newhourChange",
				 * ManageDate.getCurrentHour(ManageDate.HH_MM_SS));
				 */

				int rowCount = qo.executeUpdate();
				this.getSession().beginTransaction().commit();
				if (rowCount > 0) {
					return user[1].toString();
				}
			}
			return null;
		} catch (Exception e) {
			this.getSession().close();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public ZyosUser findById(java.lang.Long id) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select new ZyosUser( ");
			hql.append("zu.secondEmail,");
			hql.append("zu.phone,zu.mobilePhone,zu.idDocumentType, ");
			hql.append("zu.idProfession,zu.dateCreation,zu.hourCreation, ");
			hql.append("zu.userCreation,zl.userLogin, ");
			hql.append("zu.state )");
			hql.append("from ZyosUser zu,ZyosLogin zl ");
			hql.append("where zu.state =:state ");
			hql.append("and zu.id = :idZU ");
			hql.append("and zl.idZyosUser = zu.id ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZU", id);
			qo.setMaxResults(1);
			return (ZyosUser) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}

	public ZyosUser validateUserDocument(String documentNumber) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select ");
			hql.append(" new ZyosUser( ");
			hql.append(" zu.id, ");
			hql.append(" zu.name, ");
			hql.append(" zu.lastName, ");
			hql.append(" zu.documentNumber, ");
			hql.append(" zg.id, ");
			hql.append(" zg.name, zu.email, zu.state) ");
			hql.append("from  ");
			hql.append("ZyosUser zu,  ");
			hql.append("ZyosGroup zg  ");
			hql.append("where  ");
			hql.append("zu.documentNumber = :documentNumber  ");
			hql.append("and zu.idZyosGroup = zg.id ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("documentNumber", documentNumber);
			qo.setMaxResults(1);
			return (ZyosUser) qo.uniqueResult();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<SelectItem> loadUserByOZ(Long idOZ) throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select distinct new javax.faces.model.SelectItem(zu.id, zu.name || ' ' || zu.lastName) ");
			hql.append("from ");
			hql.append("ZyosUser zu, ZyosUserOperationZone zuoz ");
			hql.append("where ");
			hql.append("zuoz.idOperationZone = :idOZ and ");
			hql.append("zuoz.idZyosUser = zu.id and ");
			hql.append("zu.state = :state ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idOZ", idOZ);

			hql = null;
			ArrayList<SelectItem> list = (ArrayList<SelectItem>) qo.list();
			qo = null;

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
		}
	}

	public List<SelectItem> loadUserByUnit(Long id) throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select distinct new javax.faces.model.SelectItem(zu.id, zu.name || ' ' || zu.lastName, zu.email )");
			hql.append("from ZyosUser zu ");
			hql.append("where  ");
			hql.append("zu.state = :state ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			hql = null;
			ArrayList<SelectItem> list = (ArrayList<SelectItem>) qo.list();
			qo = null;
			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<SelectItem> findByOZ(Long idOZ) throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select distinct new javax.faces.model.SelectItem(zu.id, zu.name || ' ' || zu.lastName) ");
			hql.append("from ZyosUser zu, ZyosUserOperationZone zuoz ");
			hql.append("where zuoz.idOperationZone = :idOZ and  ");
			hql.append("zuoz.idZyosUser = zu.id and ");
			hql.append("zu.state = :state ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idOZ", idOZ);

			hql = null;
			ArrayList<SelectItem> list = (ArrayList<SelectItem>) qo.list();
			qo = null;
			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
		}
	}

	public boolean loadEveryTimeMailNotification(Long idZU) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("select ");
		hql.append("n.idCutOffTime ");
		hql.append("from ");
		hql.append("Notification n ");
		hql.append("where ");
		hql.append("n.idZyosUser = :idZU ");
		hql.append("and  n.idCutOffTime = 4 ");

		Query qo = null;
		qo = getSession().createQuery(hql.toString());
		qo.setParameter("idZU", idZU);
		qo.setMaxResults(1);

		hql = null;
		List list = qo.list();
		qo = null;
		if (list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public List<ZyosUser> loadUserList(Long idEnterprise) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select zu ");
			hql.append(" from ");
			hql.append(" ZyosUser zu, ");
			hql.append(" ZyosUserEnterprise zue ");
			hql.append(" where");
			hql.append(" zue.state = :state ");
			hql.append(" and zu.state = :state ");
			hql.append(" and zu.documentNumber is not null ");
			hql.append(" and zu.id = zue.idZyosUser ");
			hql.append(" and zue.idEnterprise = :idEnterprise ");
			hql.append(" and zu.idZyosGroup <> :idZyosGroup ");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("idEnterprise", idEnterprise);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);

			List<ZyosUser> userGroups = qo.list();
			qo = null;

			return userGroups;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	/***SIAT TUNJA*/
	public List<ZyosUser> loadUserPAAIList(Long idEnterprise) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			
			hql.append("SELECT new ZyosUser( ");
			hql.append("zu.id, ");
			hql.append("zu.name, ");
			hql.append("zu.lastName, ");
			hql.append("zu.documentNumber, ");
			hql.append("zg.id, ");
			hql.append("zg.name, zu.email, zl.userLogin, zu.phone, zu.mobilePhone, zu.secondEmail, zu.idDocumentType, zu.state, zl.authMode) "
					+ "FROM ZyosUser zu, ZyosGroup zg, ZyosUserGroup zug, ZyosUserEnterprise zue, ZyosLogin zl "
					+ "WHERE zg.id IN (SELECT id FROM ZyosGroup WHERE name like :name AND state= :state) "
					+ "AND zg.id=zug.idGroup AND zu.id=zug.idZyosUser AND zu.state= :state AND zug.state= :state "
					+ "AND zu.id = zue.idZyosUser AND zu.id = zl.idZyosUser AND zue.idEnterprise = :idEnterprise AND zu.documentNumber is not null");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("idEnterprise", idEnterprise);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("name", "%Docente%PAAI%");
			
			List<ZyosUser> userGroups = qo.list();
			qo = null;

			return userGroups;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public List<SelectItem> loadZyosUserByEnterpriseList(Long idEnterprise)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select new javax.faces.model.SelectItem(zu.id,  ");
			hql.append(" zu.name ||' ' || zu.lastName, zu.email) from ");
			hql.append(" ZyosUser zu, ZyosUserEnterprise zue ");
			hql.append(" where zu.id = zue.idZyosUser and ");
			hql.append(" zu.state=:state and ");
			hql.append(" zue.idEnterprise=:idEnterprise ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idEnterprise", idEnterprise);
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * It find a user by group
	 * 
	 * @param group
	 *            This attribute must come from IZyosGroup
	 * @param idEnterprise
	 *            Id Enterprise
	 * @return Zyos user who correspond to parameters
	 * @throws Exception
	 */
	public ZyosUser queryByGroup(Long group, Long idEnterprise)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select zu from ");
			hql.append(" ZyosUser zu, ZyosUserEnterprise zue, ZyosUserGroup zug ");
			hql.append(" where zu.id = zue.idZyosUser ");
			hql.append(" and zue.idEnterprise=:idEnterprise ");
			hql.append(" and zug.idZyosUser =zu.id ");
			hql.append(" and zug.idGroup=:idZyosGroup ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idEnterprise", idEnterprise);
			qo.setParameter("idZyosGroup", group);
			qo.setMaxResults(1);
			Object o = qo.uniqueResult();
			return o != null ? (ZyosUser) o : null;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<ZyosUser> loadZyosUserList() throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("from ZyosUser zu where ");
			hql.append("zu.state = :state order by zu.idEnterprise, zu.name");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			hql = null;
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
		}
	}

	public boolean validateLoginExist(String userLogin) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select count(z) from ZyosLogin z ");
			hql.append(" where z.userLogin = :userLogin ");

			qo = getSession().createQuery(hql.toString());
			qo.setMaxResults(1);
			qo.setParameter("userLogin", userLogin);
			Long result = (Long) qo.uniqueResult();

			return (result != null && result != 0) ? true : false;
		} catch (Exception e) {
			getSession().cancelQuery();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public boolean validateEmailExist(String email) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select count(z) from ZyosUser z ");
			hql.append(" where z.email = :email ");
			hql.append(" or z.secondEmail =:email ");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("email", email);
			qo.setMaxResults(1);
			Long result = (Long) qo.uniqueResult();

			return (result != null && result != 0) ? true : false;
		} catch (Exception e) {
			getSession().cancelQuery();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public void deleteZyosUserGroup(Long id) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" delete from ZyosUserGroup z ");
			hql.append(" where z.idZyosUser = :idZU ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idZU", id);
			qo.executeUpdate();
		} catch (RuntimeException e) {
			getSession().cancelQuery();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public Long getZyosGroup(String documentNumber) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select z.idZyosGroup from ZyosUser z ");
			hql.append(" where z.documentNumber = :documentNumber ");

			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("documentNumber", documentNumber);

			Object object = (Object) qo.uniqueResult();

			return Long.valueOf(object.toString());
		} catch (Exception e) {
			getSession().cancelQuery();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public ZyosUser loadZyosUser(Long idZyosUser) throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append(" SELECT zu.idZyosUser AS idZyosUser, ");
			hql.append(" zu.name AS name, ");
			hql.append(" zu.lastName AS lastName, ");
			hql.append(" zu.email AS email ");
			hql.append(" FROM ZyosUser zu ");
			hql.append(" WHERE zu.idZyosUser = :idZyosUser ");
			hql.append(" AND zu.state = :state ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString()).setResultTransformer(
					Transformers.aliasToBean(ZyosUser.class));
			qo.setParameter("idZyosUser", idZyosUser);
			qo.setParameter("state", IZyosState.ACTIVE);

			return (ZyosUser) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ZyosUser findZyosUser(Long idZyosUser) throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append(" SELECT zu.idZyosUser AS idZyosUser, ");
			hql.append(" zu.documentNumber AS documentNumber, ");
			hql.append(" zu.name AS name, ");
			hql.append(" zu.lastName AS lastName, ");
			hql.append(" zu.phone AS phone, ");
			hql.append(" zu.mobilePhone AS mobilePhone, ");
			hql.append(" zu.email AS email, ");
			hql.append(" zu.secondEmail AS secondEmail ");
			hql.append(" FROM ZyosUser zu ");
			hql.append(" WHERE zu.idZyosUser = :idZyosUser ");
			hql.append(" AND zu.state = :state ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString()).setResultTransformer(
					Transformers.aliasToBean(ZyosUser.class));
			qo.setParameter("idZyosUser", idZyosUser);
			qo.setParameter("state", IZyosState.ACTIVE);

			return (ZyosUser) qo.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean editDataUser(ZyosUser data, boolean isFullEdition,
			String userChange) {
		StringBuilder hql = new StringBuilder();
		Query query = null;
		try {
			hql.append("update ZyosUser ");
			hql.append("set phone = :newPhone, ");
			if (isFullEdition) {
				hql.append("documentNumber = :documentNumber, ");
				hql.append("idProfession = :idPost, ");
				hql.append("idDocumentType =:idDocumentType, ");
				hql.append("name =:name, ");
				hql.append("lastName =:lastName, ");
			}
			hql.append("mobilePhone = :newMobilePhone , email = :newEmail ,");
			hql.append("secondEmail = :newSecondEmail ,");
			hql.append("dateChange = :newdateChange,");
			hql.append("userChange = :userChange ");
			hql.append("where id = :idZU");

			query = getSession().createQuery(hql.toString());
			if (isFullEdition) {
				query.setParameter("documentNumber", data.getDocumentNumber());
				query.setParameter("idPost", data.getIdProfession());
				query.setParameter("idDocumentType", data.getIdDocumentType());
				query.setParameter("name", data.getName());
				query.setParameter("lastName", data.getLastName());
			}
			query.setParameter("idZU", data.getIdZyosUser());
			query.setParameter("newPhone", data.getPhone());
			query.setString("newMobilePhone", data.getMobilePhone());
			query.setString("newEmail", data.getEmail());
			query.setString("newSecondEmail", data.getSecondEmail());
			query.setString("newdateChange",
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
			query.setParameter("userChange", userChange);

			int rowCount = query.executeUpdate();
			if (rowCount > 0) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
			query = null;
		}
	}

	public ZyosUser loadDataUser(String zyosUserSession) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select ");
			hql.append("new ZyosUser( ");
			hql.append("zu.id, dt.id, ");
			hql.append("dt.name, ");
			hql.append("zu.documentNumber, ");
			hql.append("zu.name, ");
			hql.append("zu.lastName, ");
			hql.append("zu.phone, ");
			hql.append("zu.mobilePhone, ");
			hql.append("zu.secondEmail, zu.email  ) ");
			hql.append("from ");
			hql.append("ZyosUser zu, ");
			hql.append("DocumentType dt ");
			hql.append("where ");
			hql.append("zu.state = :state ");
			hql.append("and zu.documentNumber = :documentNumber ");
			hql.append("and zu.idDocumentType = dt.id ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("documentNumber", zyosUserSession);
			qo.setMaxResults(1);
			ZyosUser list = (ZyosUser) qo.uniqueResult();
			return list;
		} catch (Exception e) {
			getSession().beginTransaction().rollback();
			throw e;
		} finally {
			qo = null;
			hql = null;
		}
	}

	/**
	 * @author jhernandez
	 * */
	public String loadAdminEmailList() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" SELECT zu.email ");
			hql.append(" FROM ZyosUser zu ");
			hql.append(" WHERE zu.id = 1 ");
			hql.append(" AND zu.state = :state ");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			return (String) qo.uniqueResult();

		} catch (Exception e) {

			throw e;
		}
	}

	/**
	 * @author mtorres
	 * */
	public void saveStudent(Student student) {
		try {
			getSession().save(student);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	/**
	 * @author mtorres
	 * */
	public boolean validateDocumentExist(String documentNumber) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT COUNT(*) FROM ZyosUser zu ");
			hql.append(" WHERE zu.documentNumber = :document ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("document", documentNumber);
			qo.setMaxResults(1);
			Long count = (Long) qo.uniqueResult();

			return count > 0;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * @author jhernandez
	 * */
	public ZyosUser validateZyosUserTeacher(String documentNumber)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select new ZyosUser (z.idZyosUser, z.documentNumber, z.name, z.email, z.idZyosGroup ) ");
			hql.append(" from ");
			hql.append(" ZyosUser z ");
			hql.append(" where ");
			hql.append(" z.documentNumber =:documentNumber and ");
			hql.append(" z.idZyosGroup =:IZyosGroup  and ");
			hql.append(" z.state =:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setMaxResults(1);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("IZyosGroup", IZyosGroup.TEACHER);
			qo.setParameter("documentNumber", documentNumber);

			return (ZyosUser) qo.uniqueResult();

		} catch (Exception e) {
			getSession().cancelQuery();
			getSession().close();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/**
	 * @author mtorres edit jhernandez
	 * */
	public ZyosUser searchUser(String codeStudent, String emailUser) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select new ZyosUser (z.idZyosUser, z.name, z.lastName, z.documentNumber, z.email, z.phone, z.mobilePhone, z.address, z.idZyosGroup, d.name ) ");
			hql.append(" From ZyosUser z, Student s, StudentDegree ds, Degree d ");

			if ((codeStudent == "" || codeStudent.isEmpty())
					&& (emailUser != "" || !emailUser.isEmpty())) {
				hql.append(" WHERE z.email=:emailUser ");
			} else {
				if ((codeStudent != "" || !codeStudent.isEmpty())
						&& (emailUser == "" || emailUser.isEmpty())) {
					hql.append(" WHERE s.code=:codeStudent ");
				} else
					hql.append(" WHERE (s.code=:codeStudent AND z.email=:emailUser ) ");
			}

			hql.append(" and ");
			hql.append(" (z.idZyosGroup =:idZyosGroup OR z.idZyosGroup =:idZyosGroupClassmate )  and ");
			hql.append(" s.idZyosUser = z.idZyosUser and ");
			hql.append(" ds.idStudent = s.idStudent and ");
			hql.append(" ds.idDegree = d.id and ");
			hql.append(" z.state =:state and ");
			hql.append(" d.state =:state and ");
			hql.append(" ds.state =:state ");

			qo = getSession().createQuery(hql.toString());
			if ((codeStudent == "" || codeStudent.isEmpty())
					&& (emailUser != "" || !emailUser.isEmpty())) {
				qo.setParameter("emailUser", emailUser);
			} else {
				if ((codeStudent != "" || !codeStudent.isEmpty())
						&& (emailUser == "" || emailUser.isEmpty())) {
					qo.setParameter("codeStudent", codeStudent);
				} else {
					qo.setParameter("codeStudent", codeStudent);
					qo.setParameter("emailUser", emailUser);
				}

			}
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);
			qo.setParameter("idZyosGroupClassmate", IZyosGroup.CLASS_MATE);
			qo.setMaxResults(1);

			return (ZyosUser) qo.uniqueResult();

		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * @author jhernandez
	 * */
	public ZyosUser validateZyosUser(String documentNumber) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select new ZyosUser (z.idZyosUser, z.documentNumber, z.name, z.email, z.idZyosGroup ) ");
			hql.append(" from ");
			hql.append(" ZyosUser z, ZyosGroup zg ");
			hql.append(" where ");
			hql.append(" z.documentNumber =:documentNumber and ");
			hql.append(" z.idZyosGroup =:IZyosGroup and ");
			hql.append(" z.state =:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setMaxResults(1);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("IZyosGroup", IZyosGroup.STUDENT);
			qo.setParameter("documentNumber", documentNumber);

			return (ZyosUser) qo.uniqueResult();

		} catch (Exception e) {
			getSession().cancelQuery();
			getSession().close();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/**
	 * @author jhernandez
	 * */
	public String validateDuplicateUser(String email) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select z.userLogin ");
			hql.append(" from ");
			hql.append(" ZyosLogin z ");
			hql.append(" where ");
			hql.append(" z.userLogin =:email and ");
			hql.append(" z.state =:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setMaxResults(1);
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("email", email);

			return (String) qo.uniqueResult();

		} catch (Exception e) {
			getSession().cancelQuery();
			getSession().close();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/**
	 * @author oortiz
	 * @throws Exception
	 */
	public List<ZyosUser> loadResponsibleListByRole(Long idStage)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" select new ZyosUser(z.idZyosUser, z.name, z.lastName) ");
			hql.append(" from ");
			hql.append(" ZyosUser z ");
			hql.append(" where ");
			if (idStage != null)
				hql.append(" z.idZyosGroup = :idStage and ");
			hql.append(" z.state =:state ");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			if (idStage != null)
				qo.setParameter("idStage", idStage);

			return qo.list();

		} catch (Exception e) {
			getSession().cancelQuery();
			getSession().close();
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/**
	 * @author jhernandez
	 * @throws Exception
	 */
	public void updateZyosGroup(ZyosUser zyosUser) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE ZyosUser Set idZyosGroup=:idZyosGroup");
			hql.append(" Where idZyosUser=:idZyosUser ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idZyosUser", zyosUser.getIdZyosUser());
			qo.setParameter("idZyosGroup", IZyosGroup.CLASS_MATE);

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/**
	 * @author jhernandez
	 * @throws Exception
	 */
	public void updateZyosGroupTeacher(ZyosUser zyosUser) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE ZyosUser Set idZyosGroup=:idZyosGroup");
			hql.append(" Where idZyosUser=:idZyosUser ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idZyosUser", zyosUser.getIdZyosUser());
			qo.setParameter("idZyosGroup", IZyosGroup.TEACHER);

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	/**
	 * @author jhernandez
	 * @throws Exception
	 */
	public void updateStudentData(Student student) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {

			sql.append(" UPDATE ZyosUser Set name=:name, address=:address, secondEmail=:secondEmail, phone=:phone, mobilePhone=:mobilePhone, ");
			sql.append(" dateChange=:dateChange, userChange=:userChange ");
			sql.append(" From Student, StudentDegree, Degree ");
			sql.append(" Where ");
			sql.append(" ZyosUser.idZyosUser = Student.idZyosUser AND ");
			sql.append(" Student.idStudent =:idStudent AND ");
			sql.append(" StudentDegree.idStudent = Student.idStudent AND ");
			sql.append(" StudentDegree.idDegree = Degree.id AND ");
			sql.append(" ZyosUser.state = :state AND ");
			sql.append(" Student.state = :state AND ");
			sql.append(" StudentDegree.state = :state AND ");
			sql.append(" Degree.state = :state ");

			qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("idStudent", student.getIdStudent());
			qo.setParameter("name", student.getName());
			qo.setParameter("address", student.getAddress());
			qo.setParameter("secondEmail", student.getSecondEmail());
			qo.setParameter("phone", student.getPhone());
			qo.setParameter("mobilePhone", student.getMobilePhone());
			qo.setParameter("dateChange", student.getDateChange());
			qo.setParameter("userChange", student.getUserChange());
			qo.setParameter("state", IZyosState.ACTIVE);

			qo.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}

	public List<Subject> lodInfoToShow(Long idStudent) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select su from Subject su, StudentSubject ss, Student s ");
			hql.append("where s.id = :idStudent and ");
			hql.append("s.id = ss.idStudent and ");
			hql.append("ss.idSubject = su.id and "); 
			hql.append("su.state =:state " );
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idStudent", idStudent);
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List<ZyosUser> loadTeacherBySchoolTunja(Long idSchool) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW ZyosUser ( zu.idZyosUser, zu.documentNumber, zu.name, zu.lastName, zu.email ) "
					+ " FROM Teacher t, ZyosUser zu "
					+ " WHERE t.idZyosUser = zu.idZyosUser AND t.idSchool = :idSchool "
					+ " AND t.state = :state AND zu.state = :state ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idSchool", idSchool);
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List<ZyosUser> loadDecDivisionBySchoolTunja(Long idFaculty) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW ZyosUser ( zu.idZyosUser, zu.documentNumber, zu.name, zu.lastName, zu.email ) "
					+ " FROM FacultyCoordinator fc, ZyosUser zu "
					+ " WHERE fc.idZyosUser = zu.idZyosUser "
					+ " AND fc.idFaculty = :idFaculty "
					+ " AND fc.state = :state "
					+ " AND zu.state = :state ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idFaculty", idFaculty);
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public List<ZyosUser> loadDecFacultyBySchoolTunja(Long idSchool) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" SELECT NEW ZyosUser ( zu.idZyosUser, zu.documentNumber, zu.name, zu.lastName, zu.email ) "
					+ " FROM ZyosUser zu, SchoolCoordinador sc "
					+ " WHERE zu.idZyosUser = sc.idZyosuser "
					+ " AND zu.state = :state "
					+ " AND sc.state = :state "
					+ " AND sc.idSchool = :idSchool ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idSchool", idSchool);
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public int deleteDecDivisionBySchoolTunja(Long idDecDivision) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" UPDATE FacultyCoordinator SET state=:state WHERE idZyosUser = :idDecanoD ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idDecanoD", idDecDivision);
			qo.setParameter("state", IZyosState.INACTIVE);
			return qo.executeUpdate();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public int deleteDecFacultyBySchoolTunja(Long idDecFaculty) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("  UPDATE SchoolCoordinador SET state=:state WHERE idZyosUser = :idDecanoF ");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("idDecanoF", idDecFaculty);
			qo.setParameter("state", IZyosState.INACTIVE);
			return qo.executeUpdate();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
}