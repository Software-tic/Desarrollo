package com.zyos.core.lo.user.model;

import java.util.List;



import org.hibernate.Query;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * ZyosUserGroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.lo.user.model.ZyosUserGroup
 * @author MyEclipse Persistence Tools
 */

public class ZyosUserGroupDAO extends OracleBaseHibernateDAO {

	public void save(ZyosUserGroup transientInstance) {
		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(ZyosUserGroup persistentInstance) {
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {

			throw re;
		}
	}

	public List<Object> loadZyosGroupListByUser(Long idUser) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select idGroup from ");
			hql.append("ZyosUserGroup zug ");
			hql.append("where ");
			hql.append("zug.idZyosUser = :idZU   ");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("idZU", idUser);

			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}

	}
}